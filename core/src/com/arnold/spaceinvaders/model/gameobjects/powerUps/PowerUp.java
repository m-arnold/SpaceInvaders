package com.arnold.spaceinvaders.model.gameobjects.powerUps;

import com.arnold.spaceinvaders.model.Entity;
import com.arnold.spaceinvaders.utils.EntityManager;

import java.util.Random;

/**
 * Abstract PowerUp defining movement behaviour for all power ups.
 *
 * author: Marc Arnold
 */
public abstract class PowerUp extends Entity {

    @Override
    public void update() {
        posY = posY - 5;
        updateBoundingBox();
    }

    @Override
    public void destroy() {
        entityManager.removeEntity(this);
    }

    /**
     * Static method used to spawn random powerups at a given location (x,y).
     * PowerUps are spawned with a 20% chance on call.
     *
     * @param posX
     * @param posY
     */
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
