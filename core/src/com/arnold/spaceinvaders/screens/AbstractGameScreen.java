package com.arnold.spaceinvaders.screens;

import com.arnold.spaceinvaders.utils.AssetManager;
import com.arnold.spaceinvaders.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Abstract class for GameScreens. Provides methods which all game screens need.
 *
 * author: Marc Arnold
 */
public abstract class AbstractGameScreen implements Screen {

    protected AssetManager assetManager = AssetManager.getAssetManager();

    private Texture background1;
    private float background1Pos;
    private Texture background2;
    private float background2Pos;

    protected static Sound music;

    /**
     * Initializes the moving background
     */
    protected void initMovingBackgroung() {
        background1 = assetManager.textures.get("Background");
        background1Pos = 0;
        background2 = assetManager.textures.get("Background");
        background2Pos = background1.getHeight();
    }

    /**
     * Renders the moving background
     */
    protected void renderMovingBackground(){
        SpriteBatch spriteBatch = Utils.spriteBatch;
        spriteBatch.begin();
        spriteBatch.draw(background1,0,background1Pos);
        spriteBatch.draw(background2,0,background2Pos);
        spriteBatch.end();
        background1Pos = background1Pos -1f;
        background2Pos = background2Pos -1f;

        if(background1Pos <= 0 - (background1.getHeight())){
            background1Pos = background1.getHeight();
        }
        if(background2Pos <=  0 - (background2.getHeight())){
            background2Pos = background1.getHeight();
        }
    }

    /**
     * Clears the canvas
     */
    protected void clear(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Plays music
     *
     * @param sound which will be played
     */
    protected void playMusic(Sound sound) {
        if(music == null) {
            music = sound;
            music.loop();
        }
    }

    /**
     * Stops music
     */
    protected  void stopMusic() {
        music.stop();
        music = null;
    }

    @Override
    public abstract void show();

    @Override
    public abstract void render(float v);

    @Override
    public void resize(int i, int i1) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}
