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
        // Collision with other Entity (but not with PowerUps)
        if(collided != null) {
            handleCollision(collided);
        }

        // Update Position of projectile and the corresponding bounding box
        posY = posY + speedY;
        posX = posX + speedX;
        updateBoundingBox();
    }

    private void handleCollision(Entity collided) {
        // Projectiles should ignore PowerUps and other Projectile
        if(collided instanceof PowerUp || collided instanceof Projectile) {
            return;
        }
        if(fromPlayer && !(collided instanceof Player)) {
            // Destroy entity which was hit
            collided.destroy();
            // Destroy projectile
            this.destroy();
            // Spawn explosion
            animationManager.addAnimation(new Explosion(collided.posX, collided.posY));
            // Play explosion sound
            assetManager.sounds.get("Explosion").play();
            // Add player score
            ((Player) entityManager.getEntityById("Player")).increaseScore(collided);
            // Spawn PowerUp
            PowerUp.spawnPowerUp(collided.posX, collided.posY);
        }
    }

    @Override
    public void destroy() {
        entityManager.removeEntity(this);
    }
}
