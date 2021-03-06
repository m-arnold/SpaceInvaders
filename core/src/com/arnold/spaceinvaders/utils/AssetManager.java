package com.arnold.spaceinvaders.utils;

import com.arnold.spaceinvaders.model.animations.Explosion;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containing the AssetManager.
 *
 * author: Marc Arnold
 */
public class AssetManager {

    public static AssetManager assetManager;

    public Map<String, Sound> sounds;
    public Map<String, Texture> textures;
    public Map<String, BitmapFont> fonts;

    public FreeTypeFontGenerator fontGenerator;

    private AssetManager() {
        loadTextures();
        loadSounds();
        loadAnimations();
        loadFonts();
    }

    public static AssetManager getAssetManager() {
        if(assetManager == null){
            assetManager = new AssetManager();
        }
        return assetManager;
    }

    /**
     * Loads sounds used within the game.
     */
    private void loadSounds() {
        sounds = new HashMap<>();
        sounds.put("Laser", Gdx.audio.newSound(Gdx.files.internal("sounds/laser.ogg")));
        sounds.put("PowerUp", Gdx.audio.newSound(Gdx.files.internal("sounds/powerUp.ogg")));
        sounds.put("Explosion", Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav")));
        sounds.put("MenuMusic", Gdx.audio.newSound(Gdx.files.internal("sounds/menuMusic.wav")));
        sounds.put("GameMusic", Gdx.audio.newSound(Gdx.files.internal("sounds/gameMusic.wav")));
    }

    /**
     * Loads fonts used within the game.
     */
    private void loadFonts() {
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenvector_future.ttf"));

        fonts = new HashMap<>();

        // Generate the different fonts used within the game
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = Color.RED;
        fonts.put("WaveMessageFont", fontGenerator.generateFont(parameter));

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        fonts.put("PlayerScoreFont", fontGenerator.generateFont(parameter));

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.BLACK;
        fonts.put("HighScoreFont", fontGenerator.generateFont(parameter));
    }

    /**
     * Loads textures used within the game.
     */
    private void loadTextures() {
        textures = new HashMap<>();
        //Load Brackground Texture
        textures.put("Background", new Texture("textures/Background.png"));
        // Load Enemy Texture
        textures.put("Enemy1", new Texture("textures/enemyRed2.png"));
        textures.put("Enemy2", new Texture("textures/enemyBlack1.png"));
        textures.put("Boss", new Texture("textures/boss.png"));
        // Load Player Texture
        textures.put("Player", new Texture("textures/playerShip1_blue.png"));
        // Load Projectiles Textures
        textures.put("PlayerFire", new Texture("textures/laserRed12.png"));
        textures.put("EnemyFire", new Texture("textures/laserBlue09.png"));
        textures.put("BossFire", new Texture("textures/laserRed09.png"));
        // Load Shield Textures
        textures.put("Shield", new Texture("textures/shield.png"));
        // Load PowerUp Textures
        textures.put("ShieldPowerUp", new Texture("textures/shieldPowerUp.png"));
        textures.put("FirePowerUp", new Texture("textures/firePowerUp.png"));
        textures.put("LifePowerUp", new Texture("textures/lifePowerUp.png"));
        // Load Asteroids Textures
        textures.put("Asteroid", new Texture("textures/meteorBrown_big1.png"));
        textures.put("AsteroidMedium1", new Texture("textures/meteorBrown_med1.png"));
        textures.put("AsteroidMedium2", new Texture("textures/meteorBrown_med2.png"));
        textures.put("AsteroidSmall1", new Texture("textures/meteorBrown_small1.png"));
        textures.put("AsteroidSmall2", new Texture("textures/meteorBrown_small2.png"));
        // Load Player Live Texture
        textures.put("Lives", new Texture("textures/playerLife1_blue.png"));
        // Load Button Textures
        textures.put("ExitButton", new Texture("textures/exitbutton.png"));
        textures.put("StartButton", new Texture("textures/startbutton.png"));
        textures.put("HighScoreButton", new Texture("textures/highscorebutton.png"));
        textures.put("BackButton", new Texture("textures/backbutton.png"));
        // Load Menu Textures
        textures.put("MainMenu", new Texture("textures/MainMenu.png"));
        textures.put("HighscoreMenu", new Texture("textures/HighscoreBackground.png"));
    }

    /**
     * Loads animations used within the game.
     */
    private void loadAnimations(){
        Explosion.preloadAnimation();
    }
}
