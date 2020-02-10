package com.arnold.spaceinvaders.model.gameobjects;

import com.badlogic.gdx.math.Rectangle;

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
