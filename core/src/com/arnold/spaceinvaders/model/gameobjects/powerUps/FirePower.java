package com.arnold.spaceinvaders.model.gameobjects.powerUps;

import com.badlogic.gdx.math.Rectangle;

public class FirePower extends PowerUp {

    public FirePower(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        texture = assetManager.textures.get("FirePowerUp");
        boundingBox = new Rectangle(posX,posY, texture.getWidth(), texture.getHeight());
    }
}
