package com.arnold.spaceinvaders.spawners;

import com.arnold.spaceinvaders.model.gameobjects.Asteroid;
import com.arnold.spaceinvaders.model.gameobjects.Boss;
import com.arnold.spaceinvaders.model.gameobjects.FiringEnemy;
import com.arnold.spaceinvaders.model.gameobjects.NonFiringEnemy;
import com.arnold.spaceinvaders.utils.AssetManager;
import com.arnold.spaceinvaders.utils.EntityManager;
import com.arnold.spaceinvaders.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

/**
 * Class containing the enemy spawner.
 *
 * author: Marc Arnold
 */
public class EnemySpawner {

    public static EnemySpawner enemySpawner;
    public EntityManager entityManager;
    public AssetManager assetManager;

    private int spawnCounter;
    private BitmapFont font;

    private WaveMode waveMode;
    private boolean waveRunning;
    private long endTimeLastWave;

    private Boss boss;
    private boolean bossSpawned;

    private int waveCounter;

    private EnemySpawner(){
        entityManager = EntityManager.getEntityManager();
        assetManager = AssetManager.getAssetManager();
        endTimeLastWave = TimeUtils.millis();
        waveRunning = false;
        waveMode = null;
        waveCounter = 0;
        bossSpawned = false;

        font = assetManager.fonts.get("WaveMessageFont");
    }

    public static EnemySpawner getEnemySpawner(){
        if(enemySpawner == null){
            enemySpawner = new EnemySpawner();
        }
        return enemySpawner;
    }

    /**
     * Called from the gameplay screen. Checks whether a wave is running, if not
     * it starts a new wave.
     */
    public void spawn() {
        // Wave Running
        if(waveRunning){
           spawn(waveMode);
        }
        // No Wave Running
        else{
            setNextWaveMode();
            renderWaveMessage();
            // If 5 Seconds past since last wave, start new one
            if(TimeUtils.timeSinceMillis(endTimeLastWave) > 5000){
                waveRunning = true;
            }
        }
    }

    /**
     * Called if the game is over, resets the enemy spawner
     */
    public void reset() {
        enemySpawner = new EnemySpawner();
    }

    /**
     * Sets the mode of the next wave
     */
    public void setNextWaveMode() {
        // Determine next WaveMode
        if(waveMode == null){
            if (waveCounter % 3 == 0) {
                waveMode = WaveMode.boss;
            } else {
                waveMode = WaveMode.getRandomWaveMode();
            }
        }
    }

    /**
     * Calls more concrete spawn method depending on the given wave mode
     *
     * @param mode of the wave currently running
     */
    private void spawn(WaveMode mode){
        switch(mode){
            case enemies:
                spawnEnemies();
                break;
            case asteroids:
                spawnAsteroids();
                break;
            case boss:
                spawnBoss();
                break;
        }
    }

    /**
     * Spawns a boss wave
     */
    public void spawnBoss() {
        if (!bossSpawned) {
            boss = new Boss();
            entityManager.addEntity(boss);
            bossSpawned = true;
        } else {
            // Wave finished
            if (entityManager.getEntityById("Boss") == null) {
                finishWave();
            }
        }
    }

    /**
     * Spawns an enemies wave
     */
    public void spawnEnemies(){
        //Determine Random x-Position for spawning Enemies
        float min = 250;
        float max = 750;
        float random = (float) (min + Math.random() * (max - min));
        // Spawn FiringEnemy
        if(spawnCounter % 300 == 0){
            entityManager.addEntity(new FiringEnemy(random, Gdx.graphics.getHeight()));
        }
        // Spawn NonFiringEnemy
        else if(spawnCounter % 150 == 0){
            entityManager.addEntity(new NonFiringEnemy(random,Gdx.graphics.getHeight()));
        }

        // Wave finished
        if(spawnCounter == 1500){
            finishWave();
        }
        spawnCounter++;
    }

    /**
     * Spawns an asteroid wave
     */
    public void spawnAsteroids(){
        float min = 50;
        float max = 950;
        float random = (float) (min + Math.random() * (max - min));
        if(spawnCounter % 100 == 0){
            entityManager.addEntity(new Asteroid(random));
        }

        if(spawnCounter == 1500){
            finishWave();
        }
        spawnCounter++;
    }

    /**
     * Called to prepare the next wave
     */
    private void finishWave(){
        spawnCounter = 0;
        bossSpawned = false;
        endTimeLastWave = TimeUtils.millis();
        waveRunning = false;
        waveMode = null;
        waveCounter++;
    }

    /**
     * Renders wave message to the screen
     */
    public void renderWaveMessage(){
        SpriteBatch batch = Utils.spriteBatch;

        String message = "";
        switch(waveMode){
            case enemies:
                message = "Enemies ahead!";
                break;
            case asteroids:
                message = "Asteroids ahead!";
                break;
            case boss:
                message = "Boss ahead!";
                break;
        }
        batch.begin();
        font.draw(batch,message, (Gdx.graphics.getWidth() / 2) - 165,(Gdx.graphics.getHeight() / 2) + 150);
        batch.end();
    }

    private enum WaveMode{
        asteroids,
        enemies,
        boss;
        private static WaveMode getRandomWaveMode(){
            Random random = new Random();
            return random.nextBoolean() ? enemies : asteroids;
        }
    }
}
