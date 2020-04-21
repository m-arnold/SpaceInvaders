package com.arnold.spaceinvaders.model.gameobjects.powerUps;

import com.arnold.spaceinvaders.model.Entity;
import com.arnold.spaceinvaders.utils.EntityManager;

import java.util.Random;

public abstract class PowerUp extends Entity {

    @Override
    public void update() {
        posY = posY - 5;
        updateBoundingBox();
    }

    public static void spawnPowerUp(float posX, float posY) {
        Random r = new Random();
        EntityManager em = EntityManager.getEntityManager();

        // Chance to spawn powerUp is 20%
        if (r.nextInt(5) == 0) {
            switch (r.nextInt(3)) {
                case 0:
                    em.addEntity(new Shield(posX, posY));
                    break;
                case 1:
                    em.addEntity(new FirePower(posX, posY));
                    break;
                case 2:
                    em.addEntity(new BonusLife(posX, posY));
            }
        }
    }
}
