package com.arnold.spaceinvaders.model.gameobjects;

import com.arnold.spaceinvaders.model.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Boss extends Entity {

    private float speedX = 3;
    private float speedY = 3;

    private boolean moveFoward;
    private boolean moveSideWays;
    private boolean moveBackToMid;

    private float distanceVertical;

    private Texture bossFire;
    private int life;

    public Boss() {
        texture = assetManager.textures.get("Boss");
        this.posX = (Gdx.graphics.getWidth() / 2) - (texture.getWidth() / 2);
        this.posY = 1100;

        life = 200;

        moveFoward = true;
        moveSideWays = false;
        moveBackToMid = false;

        distanceVertical = 0;

        bossFire = assetManager.textures.get("BossFire");

        boundingBox = new Rectangle(posX, posY, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void update() {

        // Move the boss forward
        if (moveFoward) {
            if (posY > 800) {
                posY = posY - speedY;
            } else {
                moveSideWays = true;
                moveFoward = false;
            }
        }

        // If the boss moved forward far enough, then move it sideways
        if (moveSideWays) {
            posX = posX + speedX;
            distanceVertical = distanceVertical + Math.abs(speedX);

            if (distanceVertical >= 300) {
                speedX = (-1) * speedX;
                distanceVertical = 0;
                moveBackToMid = true;
                moveSideWays = false;
                fire();
            }
        }

        if (moveBackToMid) {
            if(Math.abs(getCenterOfTexture().x - 500) < 5) {
                moveSideWays = true;
                moveBackToMid = false;
                fire();
            } else {
                posX = posX + speedX;
            }
        }

        updateBoundingBox();
    }

    //TODO: Don't do life logic in here
    @Override
    public void destroy() {

        if(life > 1) {
            life--;
        } else{
            entityManager.removeEntity(this);
        }
    }

    private void fire() {
        for(int i = 0; i <=7; i++) {
            entityManager.addEntity(
                    new Projectile(getCenterOfTexture().x,
                            getCenterOfTexture().y - 55,
                            -4.5f,
                            -4.5f + i*1.5f,
                            false,
                            bossFire));
        }
    }

}
