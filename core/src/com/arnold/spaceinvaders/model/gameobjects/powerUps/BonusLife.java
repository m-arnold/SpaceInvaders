package com.arnold.spaceinvaders.model.gameobjects.powerUps;

import com.badlogic.gdx.math.Rectangle;

/**
 * Class containing the BonusLife powerup.
 *
 * author: Marc Arnold
 */
public class BonusLife extends PowerUp {

    public BonusLife(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        texture = assetManager.textures.get("LifePowerUp");
        boundingBox = new Rectangle(posX, posY, texture.getWidth(), texture.getHeight());
    }
}
