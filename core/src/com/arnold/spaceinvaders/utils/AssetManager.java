package com.arnold.spaceinvaders.utils;

import com.arnold.spaceinvaders.model.animations.Explosion;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.HashMap;
import java.util.Map;

public class AssetManager {

    public static AssetManager assetManager;

    public Map<String, Sound> sounds;
    public Map<String, Texture> textures;
    public Map<String, Animation> animations;

    private AssetManager(){
        loadTextures();
        loadSounds();
        loadAnimations();
    }

    public static AssetManager getAssetManager(){
        if(assetManager == null){
            assetManager = new AssetManager();
        }
        return assetManager;
    }

    private void loadSounds(){
    }

    private void loadTextures(){
        textures = new HashMap<>();
        //Load Brackground Texture
        textures.put("Background", new Texture("textures/Background.png"));
        // Load Enemy Texture
        textures.put("Enemy1", new Texture("textures/enemyRed2.png"));
        textures.put("Enemy2", new Texture("textures/enemyBlack1.png"));
        // Load Player Texture
        textures.put("Player", new Texture("textures/playerShip1_blue.png"));
        // Load Projectiles Textures
        textures.put("PlayerFire", new Texture("textures/laserRed12.png"));
        textures.put("EnemyFire", new Texture("textures/laserBlue09.png"));
        // Load Asteroids Textures
        textures.put("Asteroid", new Texture("textures/meteorBrown_big1.png"));
        // Load Player Live Texture
        textures.put("Lives", new Texture("textures/playerLife1_blue.png"));

        // Load Explosion Texture
        // TODO: Test why this is not working as intented
        //textures.put("Explosion4", new Texture("animations/explosion/explosion_4.png"));
    }

    /**
     * In here
     */
    private void loadAnimations(){
        Explosion.preloadAnimation();
    }
}
