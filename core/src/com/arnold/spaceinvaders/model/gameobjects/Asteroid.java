package com.arnold.spaceinvaders.model.gameobjects;


import com.arnold.spaceinvaders.model.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

/**
 * Class containing the asteroid entity.
 *
 * author: Marc Arnold
 */
public class Asteroid extends Entity {

    float speedY;

    public Asteroid(float posX){
        this.posX = posX;
        this.posY = Gdx.graphics.getHeight();
        speedY = 3;
        texture = assetManager.textures.get("Asteroid");
        boundingBox = new Rectangle(posX,posY,texture.getWidth(), texture.getHeight());
    }

    @Override
    public void update() {
        posY = posY - speedY;
        updateBoundingBox();
    }

    @Override
    public void destroy() {
        entityManager.removeEntity(this);
        // Spawn smaller Asteroids
        entityManager.addEntity(new SmallAsteroid(posX, posY));
        entityManager.addEntity(new SmallAsteroid(posX, posY));
        entityManager.addEntity(new SmallAsteroid(posX, posY));
        entityManager.addEntity(new SmallAsteroid(posX, posY));
    }
}

