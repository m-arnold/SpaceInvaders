package com.arnold.spaceinvaders.model.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class FiringEnemy extends Enemy {

    public FiringEnemy(float posX, float posY){
        super(posX, posY);
        id = "Enemy";
        texture = assetManager.textures.get("Enemy1");
        boundingBox = new Rectangle(posX, posY, texture.getWidth(), texture.getHeight());
    }

    @Override
    protected void fire() {
        Texture t = assetManager.textures.get("EnemyFire");
        entityManager.addEntity(new Projectile(posX, posY, -10, 0,false, t));
        entityManager.addEntity(new Projectile(posX, posY, 10, 0,false, t));
        entityManager.addEntity(new Projectile(posX, posY, 0, -10,false, t));
        entityManager.addEntity(new Projectile(posX, posY, 0, 10,false, t));
    }
}
