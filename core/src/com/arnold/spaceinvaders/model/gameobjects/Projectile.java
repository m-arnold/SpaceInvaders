package com.arnold.spaceinvaders.model.gameobjects;

import com.arnold.spaceinvaders.utils.AnimationManager;
import com.arnold.spaceinvaders.model.Entity;
import com.arnold.spaceinvaders.model.animations.Explosion;
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
            entityManager.removeEntity(this);
            return;
        }

        // Check whether projectile collided
        Entity collided = collidedWith();
        // Collision with Enemy or Player
        if(collided != null &&
          ((fromPlayer && collided instanceof Enemy)
          ||(fromPlayer && collided instanceof Asteroid))){

            entityManager.removeEntity(collided);
            entityManager.removeEntity(this);
            animationManager.addAnimation(new Explosion(collided.posX, collided.posY));
        }

        // Update Position of projectile and the corresponding bounding box
        posY = posY + speedY;
        posX = posX + speedX;
        boundingBox.y = posY;
        boundingBox.x = posX;
    }
}
