package com.arnold.spaceinvaders.model.gameobjects;

import com.arnold.spaceinvaders.model.Entity;

import java.util.Random;

public abstract class Enemy extends Entity {

    protected float speedY;
    protected float speedX;
    protected float distanceVertical;

    public Enemy(float posX, float posY){
        this.posX = posX;
        this.posY = posY;
        Random random = new Random();
        int randomStartDirection = random.nextBoolean() ? 1 : -1;
        speedY = 2.5f;
        speedX = randomStartDirection * 2.5f;
        distanceVertical = 0;
    }

    @Override
    public void update() {
        posY = posY - speedY;
        posX = posX + speedX;
        updateBoundingBox();
        distanceVertical = distanceVertical + Math.abs(speedX);
        if(distanceVertical > 200){
            speedX = (-1) * speedX;
            distanceVertical = 0;
            fire();
        }
    }

    protected abstract void fire();
}
