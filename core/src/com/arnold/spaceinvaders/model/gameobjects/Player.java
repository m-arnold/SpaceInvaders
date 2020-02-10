package com.arnold.spaceinvaders.model.gameobjects;

import com.arnold.spaceinvaders.model.Entity;
import com.arnold.spaceinvaders.model.animations.Explosion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Entity {

    private float speedY = 4;
    private float speedX = 4;

    private int lives;

    private boolean invincible;
    private long respawnTime;

    private long lastBlinkTime;
    private boolean blink;

    public Player(){
        id = "Player";
        lives = 3;
        texture = assetManager.textures.get("Player");
        blink = false;
        setInitialPosition();
        boundingBox = new Rectangle(posX,posY, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void update() {
        // Check whether invibiblity needs to be changed.
        // Player is only invincible for 4 seconds after respawning
        if(invincible && TimeUtils.timeSinceMillis(respawnTime) > 4000){
            invincible = false;
        }

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

        // Check whether the player collided with something
        Entity collided = collidedWith();
        if(!invincible && collided !=  null && (!(collided instanceof Projectile) ||
          (!((Projectile)collided).fromPlayer))){
            destroy();
        }

        // Fire
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            fire();
        }

        // Update boundingBox
        updateBoundingBox();
    }

    /**
     * Called when the player is destroyed. Adds explosion at the
     * players location, reduces lives, checks whether the game is over
     */
    private void destroy(){
        animationManager.addAnimation(new Explosion(this.posX,this.posY));
        if(lives > 0){
            lives--;
            respawn();
        } else{
            entityManager.removeEntity(this);
            System.out.println("Game Over");
        }
    }

    /**
     * Respawns the player
     */
    private void respawn(){
        invincible = true;
        respawnTime = TimeUtils.millis();
        setInitialPosition();
    }

    /**
     * Called when SPACE is pressed. It fires one or multiple projectiles
     */
    private void fire(){
        Texture t = assetManager.textures.get("PlayerFire");
        Vector2 vector = getCenterOfTexture();
        entityManager.addEntity(new Projectile(vector.x, vector.y, 10, 0 ,true ,t));
        // Powerup?
        if(false){
            entityManager.addEntity(new Projectile(vector.x+50, vector.y, 10, 3f,true, t));
            entityManager.addEntity(new Projectile(vector.x-50, vector.y, 10, -3f,true, t));
        }
    }

    /**
     *  Sets the initial coordinates for the player ship
     */
    private void setInitialPosition(){
        posX = (Gdx.graphics.getWidth() / 2) - (texture.getWidth() / 2) - 20;
        posY = 40;
    }

    /**
     * Renders the live textures in the top left corner depending on
     * how much lives are left
     */
    private void renderPlayerLives(){
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
     * Renders a blinking player
     */
    private void renderBlinkingPlayer(){
        // Check if player should be rendered
        if(blink){
            super.render();
        }
        if(TimeUtils.timeSinceMillis(lastBlinkTime) > 200){
            lastBlinkTime = TimeUtils.millis();
            blink = !blink;
        }
    }

    @Override
    public void render() {
        // If player is invincible, the render blinking player
        if (invincible){
            renderBlinkingPlayer();
        } else{
            // Else render player normally
            super.render();
        }
        renderPlayerLives();
    }
}
