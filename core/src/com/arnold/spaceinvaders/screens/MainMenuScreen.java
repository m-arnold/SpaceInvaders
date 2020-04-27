package com.arnold.spaceinvaders.screens;

import com.arnold.spaceinvaders.SpaceInvaders;
import com.arnold.spaceinvaders.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenuScreen extends AbstractGameScreen {

    private SpaceInvaders game;
    private Stage stage;

    private Texture mainMenuText;
    private ImageButton startGameButton;
    private ImageButton highScoreButton;
    private ImageButton exitButton;

    public MainMenuScreen(final SpaceInvaders game){
        this.game = game;
    }

    @Override
    public void show() {
        mainMenuText = assetManager.textures.get("MainMenu");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        initMovingBackgroung();
        playMusic(assetManager.sounds.get("MenuMusic"));

        startGameButton = new ImageButton(new TextureRegionDrawable(assetManager.textures.get("StartButton")));
        startGameButton.setX((Gdx.graphics.getWidth() / 2) - (startGameButton.getWidth() / 2));
        startGameButton.setY(500);
        startGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stopMusic();
                game.setScreen(new GameplayScreen(game));
            }
        });
        stage.addActor(startGameButton);

        highScoreButton = new ImageButton(new TextureRegionDrawable(assetManager.textures.get("HighScoreButton")));
        highScoreButton.setX((Gdx.graphics.getWidth() / 2) - (highScoreButton.getWidth() / 2));
        highScoreButton.setY(350);
        highScoreButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HighscoreScreen(game));
            }
        });
        stage.addActor(highScoreButton);

        exitButton = new ImageButton(new TextureRegionDrawable(assetManager.textures.get("ExitButton")));
        exitButton.setX((Gdx.graphics.getWidth() / 2) - (exitButton.getWidth() / 2));
        exitButton.setY(200);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {
        renderMovingBackground();
        Utils.spriteBatch.begin();
        Utils.spriteBatch.draw(mainMenuText,0,0);
        startGameButton.draw(Utils.spriteBatch,1);
        highScoreButton.draw(Utils.spriteBatch,1);
        exitButton.draw(Utils.spriteBatch,1);
        Utils.spriteBatch.end();
        stage.act();
    }
}
