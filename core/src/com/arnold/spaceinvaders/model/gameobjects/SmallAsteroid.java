package com.arnold.spaceinvaders.model.gameobjects;

import com.arnold.spaceinvaders.model.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class SmallAsteroid extends Entity {

    private float speedX;
    private float speedY;

    public SmallAsteroid(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.speedX = randomSpeed();
        this.speedY = randomSpeed();
        texture = getRandomTextures();
        boundingBox = new Rectangle(posX, posY, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void update() {
        posX = posX + speedX;
        posY = posY + speedY;
        updateBoundingBox();
    }

    @Override
    public void destroy() {
        entityManager.removeEntity(this);
    }

    private float randomSpeed() {
        Random random = new Random();
        return (random.nextBoolean() ? (-1) * (random.nextInt(6) + 1) : random.nextInt(6) + 1);
    }

    private Texture getRandomTextures() {
        Random random = new Random();
        switch (random.nextInt(4)) {
            case 0:
                return assetManager.textures.get("AsteroidMedium1");
            case 1:
                return assetManager.textures.get("AsteroidMedium2");
            case 2:
                return assetManager.textures.get("AsteroidSmall1");
            case 3:
                return assetManager.textures.get("AsteroidSmall2");
        }
        return null;
    }
}
