package com.arnold.spaceinvaders.model.gameobjects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Class containing the non firing enemy entity.
 *
 * author: Marc Arnold
 */
public class NonFiringEnemy extends Enemy {

    public NonFiringEnemy(float posX, float posY){
        super(posX, posY);
        id = "Enemy";
        distanceVertical = 0;
        texture = assetManager.textures.get("Enemy2");
        boundingBox = new Rectangle(posX, posY, texture.getWidth(), texture.getHeight());
    }

    @Override
    protected void fire() {}
}
