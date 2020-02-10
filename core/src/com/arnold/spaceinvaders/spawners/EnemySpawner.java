package com.arnold.spaceinvaders.spawners;

import com.arnold.spaceinvaders.utils.EntityManager;
import com.arnold.spaceinvaders.model.gameobjects.Asteroid;
import com.arnold.spaceinvaders.model.gameobjects.FiringEnemy;
import com.arnold.spaceinvaders.model.gameobjects.NonFiringEnemy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

public class EnemySpawner {

    public static EnemySpawner enemySpawner;
    public EntityManager entityManager;

    private int spawnCounter;
    private BitmapFont font;

    private WaveMode waveMode;
    private boolean waveRunning;
    private long endTimeLastWave;

    private EnemySpawner(){
        entityManager = EntityManager.getEntityManager();
        endTimeLastWave = TimeUtils.millis();
        waveRunning = false;
        waveMode = null;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenvector_future.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = Color.RED;
        font = generator.generateFont(parameter);
    }

    public static EnemySpawner getEnemySpawner(){
        if(enemySpawner == null){
            enemySpawner = new EnemySpawner();
        }
        return enemySpawner;
    }

    public void spawn(){
        // Wave Running
        if(waveRunning){
           spawn(waveMode);
        }
        // No Wave Running
        else{
            // Determine next WaveMode
            if(waveMode == null){
                waveMode = WaveMode.getRandomWaveMode();
            }
            renderWaveMessage();
            // If 5 Seconds past since last wave, start new one
            if(TimeUtils.timeSinceMillis(endTimeLastWave) > 5000){
                waveRunning = true;
            }
        }
    }

    private void spawn(WaveMode mode){
        switch(mode){
            case enemies:
                spawnEnemies();
                break;
            case asteroids:
                spawnAsteroids();
                break;
        }
    }

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

    private void finishWave(){
        spawnCounter = 0;
        endTimeLastWave = TimeUtils.millis();
        waveRunning = false;
        waveMode = null;
    }

    public void renderWaveMessage(){
        SpriteBatch batch = new SpriteBatch();

        String message = "";
        switch(waveMode){
            case enemies:
                message = "Enemies ahead!";
                break;
            case asteroids:
                message = "Asteroids ahead!";
                break;
        }
        batch.begin();
        font.draw(batch,message, (Gdx.graphics.getWidth() / 2) - 165,(Gdx.graphics.getHeight() / 2) + 150);
        batch.end();
    }

    private enum WaveMode{
        asteroids,
        enemies;
        private static WaveMode getRandomWaveMode(){
            Random random = new Random();
            return random.nextBoolean() ? enemies : asteroids;
        }
    }
}
