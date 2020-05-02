package com.arnold.spaceinvaders.model.gameobjects;

import com.arnold.spaceinvaders.model.Entity;
import com.arnold.spaceinvaders.model.animations.Explosion;
import com.arnold.spaceinvaders.model.gameobjects.powerUps.PowerUp;
import com.arnold.spaceinvaders.utils.AnimationManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Projectile extends Entity {

    private float speedY;
    private float speedX;
    public boolean fromPlayer;
    AnimationManager animationManager = AnimationManager.getAnimationManager();

    public Projectile(float startX, float startY, float speedY, float speedX, boolean fromPlayer, Texture texture){
        id = "Projectile";
        posX = startX;
        posY = startY;
        this.fromPlayer = fromPlayer;
        this.speedX = speedX;
        this.speedY = speedY;
        this.texture = texture;
        boundingBox = new Rectangle(posX, posY, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void update() {

        // Check whether projectile left screen
        if(leftScreen()){
            this.destroy();
            return;
        }

        // Check whether projectile collided
        Entity collided = collidedWith();
        // Collision with other Entity
        if(collided != null) {
            handleCollision(collided);
        }

        // Update Position of projectile and the corresponding bounding box
        posY = posY + speedY;
        posX = posX + speedX;
        updateBoundingBox();
    }

    private void handleCollision(Entity collided) {
        // Collision of projectiles with the player is handled within in the player class
        if (!fromPlayer || collided instanceof Player) {
            return;
        }
        // Projectiles should ignore PowerUps and other Projectile
        if (collided instanceof PowerUp || collided instanceof Projectile) {
            return;
        }
        // Collision with boss enemy
        if (collided instanceof Boss) {
            ((Boss) collided).decreaseLife();
        }
        // Collision with normal enemy or asteroid
        else {
            // Destroy entity which was hit
            collided.destroy();
            // Spawn explosion
            animationManager.addAnimation(new Explosion(collided.posX, collided.posY));
            // Play explosion sound
            assetManager.sounds.get("Explosion").play();
            // Add player score
            ((Player) entityManager.getEntityById("Player")).increaseScore(collided);
            // Spawn PowerUp
            PowerUp.spawnPowerUp(collided.posX, collided.posY);
        }
        // Destroy projectile
        this.destroy();
    }

    @Override
    public void destroy() {
        entityManager.removeEntity(this);
    }
}
