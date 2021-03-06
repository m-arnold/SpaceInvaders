package com.arnold.spaceinvaders.model.gameobjects.powerUps;

import com.badlogic.gdx.math.Rectangle;

/**
 * Class containing the Shield powerup.
 *
 * author: Marc Arnold
 */
public class Shield extends PowerUp {

    public Shield(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        texture = assetManager.textures.get("ShieldPowerUp");
        boundingBox = new Rectangle(posX,posY, texture.getWidth(), texture.getHeight());
    }
}
