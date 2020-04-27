package com.arnold.spaceinvaders.model.gameobjects;

import com.arnold.spaceinvaders.model.Entity;
import com.arnold.spaceinvaders.model.animations.Explosion;
import com.arnold.spaceinvaders.model.gameobjects.powerUps.BonusLife;
import com.arnold.spaceinvaders.model.gameobjects.powerUps.FirePower;
import com.arnold.spaceinvaders.model.gameobjects.powerUps.PowerUp;
import com.arnold.spaceinvaders.model.gameobjects.powerUps.Shield;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Entity {

    private float speedY = 4;
    private float speedX = 4;

    private int lives;
    private int score;

    private boolean shieldPowerUp;
    private boolean firePowerUp;

    private boolean invincible;
    private long invincibleSince;

    private long lastBlinkTime;
    private boolean blink;

    private BitmapFont font;

    public Player() {
        id = "Player";
        lives = 3;
        texture = assetManager.textures.get("Player");
        blink = false;
        setInitialPosition();
        boundingBox = new Rectangle(posX,posY, texture.getWidth(), texture.getHeight());
        font = assetManager.fonts.get("PlayerScoreFont");
    }

    @Override
    public void update() {
        // Check whether invibiblity needs to be changed.
        // Player is only invincible for 4 seconds after respawning
        if(invincible && TimeUtils.timeSinceMillis(invincibleSince) > 4000){
            invincible = false;
        }
        // Moving, Firing, etc.
        handleInput();
        // Check whether the player collided with something and
        // needs to be destroyed
        handleCollision();
        // Update boundingBox
        updateBoundingBox();
    }

    /**
     * Depending on the key input, this method update the player state accordingly. This could be
     * moving the player (ship) in a certain direction or firing projectiles.
     */
    public void handleInput() {
        // Move Up, Down, Left and Right depending on input key
        // Take into account that the ship is not allowed to leave the screen
        if(Gdx.input.isKeyPressed(Input.Keys.W) && posY + texture.getHeight() < Gdx.graphics.getHeight()){
            posY = posY + speedY;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) && posY > 0){
            posY = posY - speedY;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && posX +texture.getWidth() < Gdx.graphics.getWidth()){
            posX = posX + speedX;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && posX > 0 ){
            posX = posX - speedX;
        }
        // Fire
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            fire();
        }
    }

    /**
     * This method checks whether the player is colliding with something and if so, whether
     * the player needs to be destroyed.
     */
    public void handleCollision() {
        // Check whether the player collided with something
        Entity collided = collidedWith();

        // Player collided with something
        if (collided != null) {
            if (collided instanceof Projectile) {
                handleCollisionWithProjectile((Projectile) collided);
            } else if (collided instanceof PowerUp) {
                handleCollisionWithPowerUp((PowerUp) collided);
            }
            // Collision with asteroid or enemy
            else {
                handleCollisionWithEntity();
            }
        }
    }

    // TODO: Refactor documentation
    /**
     * Called when the player has collided with an projectile. If the player
     * is not invincible and has not collided with his an projectile, then
     * he is destroyed.
     *
     * @param projectile the player collided with
     */
    private void handleCollisionWithProjectile(Projectile projectile) {
        // Player is not affected by his own projectiles
        if (projectile.fromPlayer) {
            return;
        }
        // If the player is hit and has a shield, remove it and make him
        // invincible
        if (shieldPowerUp) {
            removeShieldPowerUp();
        }
        if(!invincible) {
            destroy();
        }
    }

    /**
     * Called when the player has collided with an powerUp. Depending
     * on the powerUp, the player receives different bonuses
     *
     * @param powerUp the player collided with
     */
    private void handleCollisionWithPowerUp(PowerUp powerUp) {
        if (powerUp instanceof Shield) {
            shieldPowerUp = true;
        } else if (powerUp instanceof FirePower) {
            firePowerUp = true;
        } else if (powerUp instanceof BonusLife) {
            // Maximum are 3 lives
            if (lives < 3) {
                lives++;
            }
        }
        assetManager.sounds.get("PowerUp").play();
        entityManager.removeEntity(powerUp);
    }

    /**
     * Called when the player has collided with an asteroid or an enemy. Checks whether
     * the player has the shield powerUp. If the player does not have the shield
     * powerUp, then he will be destroyed
     *
     */
    private void handleCollisionWithEntity() {
        if (shieldPowerUp) {
            removeShieldPowerUp();
        }
        if (!invincible) {
            destroy();
        }
    }

    /**
     *  Removes the shield powerUp and makes the player invincible
     */
    private void removeShieldPowerUp() {
        shieldPowerUp = false;
        invincible = true;
        invincibleSince = TimeUtils.millis();
    }

    /**
     * Called when the player is destroyed. Adds explosion at the
     * players location, reduces lives, checks whether the game is over
     */
    public void destroy() {
        animationManager.addAnimation(new Explosion(this.posX,this.posY));
        assetManager.sounds.get("Explosion").play();
        lives--;
        if(lives > 0) {
            respawn();
        } else{
            entityManager.removeEntity(this);
        }
    }

    /**
     * Respawns the player
     */
    private void respawn() {
        invincible = true;
        invincibleSince = TimeUtils.millis();
        setInitialPosition();
    }

    /**
     * Called when SPACE is pressed. It fires one or multiple projectiles
     */
    private void fire() {
        Texture t = assetManager.textures.get("PlayerFire");
        Vector2 vector = getCenterOfTexture();
        entityManager.addEntity(new Projectile(vector.x, vector.y, 10, 0 ,true ,t));
        if(firePowerUp){
            entityManager.addEntity(new Projectile(vector.x+50, vector.y, 10, 3f,true, t));
            entityManager.addEntity(new Projectile(vector.x-50, vector.y, 10, -3f,true, t));
        }
        assetManager.sounds.get("Laser").play();
    }

    /**
     *  Sets the initial coordinates for the player ship
     */
    private void setInitialPosition() {
        posX = (Gdx.graphics.getWidth() / 2) - (texture.getWidth() / 2) - 20;
        posY = 40;
    }

    /**
     * Called when a projectile fired by the player destroyed another
     * entity. Updates the player score according to the destroyed entity.
     *
     * @param entity destroyed by the player
     */
    public void increaseScore(Entity entity) {
        if(entity instanceof  FiringEnemy){
            score += 20;
        } else if (entity instanceof NonFiringEnemy) {
            score += 10;
        } else if (entity instanceof Asteroid) {
            score += 5;
        } else if (entity instanceof SmallAsteroid) {
            score += 15;
        }
    }

    /**
     * Renders the live textures in the top left corner depending on
     * how much lives are left
     */
    private void renderPlayerLives() {
        Texture tx = assetManager.textures.get("Lives");
        spriteBatch.begin();
        if(lives > 0){
            spriteBatch.draw(tx, 10,950);
        }
        if(lives > 1){
            spriteBatch.draw(tx, 10 + tx.getWidth() + 10,950);
        }
        if(lives > 2){
            spriteBatch.draw(tx, 10 + tx.getWidth() + 10 + tx.getWidth() + 10,950);
        }
        spriteBatch.end();
    }

    /**
     * Renders the player score in the top right corner
     */
    private void renderPlayerScore() {
        spriteBatch.begin();
        String msg = "Score: " + score;
        font.draw(spriteBatch, msg, 800,975);
        spriteBatch.end();
    }

    /**
     * Renders the shield gained by the shield power up
     */
    private void renderPlayerShield() {
        spriteBatch.begin();
        spriteBatch.draw(assetManager.textures.get("Shield"),boundingBox.x - 22, boundingBox.y);
        spriteBatch.end();
    }

    /**
     * Renders a blinking player
     */
    private void renderBlinkingPlayer() {
        // Check if player should be rendered
        if(blink){
            super.render();
        }
        if(TimeUtils.timeSinceMillis(lastBlinkTime) > 200){
            lastBlinkTime = TimeUtils.millis();
            blink = !blink;
        }
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void render() {
        // If player is invincible, render blinking player
        if (invincible){
            renderBlinkingPlayer();
        } else{
            // Else render player normally
            super.render();
        }

        // If the player collected the shield power up, render
        // the shield
        if (shieldPowerUp) {
            renderPlayerShield();
        }

        // Render score and live display
        renderPlayerLives();
        renderPlayerScore();
    }
}
