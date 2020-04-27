package com.arnold.spaceinvaders.screens;

import com.arnold.spaceinvaders.SpaceInvaders;
import com.arnold.spaceinvaders.utils.AssetManager;
import com.arnold.spaceinvaders.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HighscoreScreen extends AbstractGameScreen implements Input.TextInputListener {

    private SpaceInvaders game;
    private Stage stage;

    private Texture highScoreTexture;
    private ImageButton backButton;
    private BitmapFont font;

    private HighScoreEntry[] scores;
    private int possibleNewHighscore;
    private String playerName;
    private boolean scoresUpdated;

    public HighscoreScreen(SpaceInvaders game) {
        this.game = game;
    }

    public HighscoreScreen(SpaceInvaders game, int score) {
        this.game = game;
        this.possibleNewHighscore = score;
        this.scoresUpdated = false;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        highScoreTexture = assetManager.textures.get("HighscoreMenu");
        initMovingBackgroung();
        playMusic(assetManager.sounds.get("MenuMusic"));
        font = assetManager.fonts.get("HighScoreFont");
        loadHighScoreFile();

        backButton = new ImageButton(new TextureRegionDrawable(AssetManager.getAssetManager().textures.get("BackButton")));
        backButton.setX((Gdx.graphics.getWidth() / 2) - (backButton.getWidth() / 2));
        backButton.setY(150);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void render(float v) {
        clear();
        renderMovingBackground();
        Utils.spriteBatch.begin();
        Utils.spriteBatch.draw(highScoreTexture, 0, 0);
        backButton.draw(Utils.spriteBatch,1);
        Utils.spriteBatch.end();
        renderHighScore();
        stage.act();

        if(isNewHighScore(possibleNewHighscore) && !scoresUpdated) {
            scoresUpdated = true;
            Gdx.input.getTextInput(this, "New Highscore, Enter name:", "", "name");
        }
    }

    boolean isNewHighScore(int score) {
        return score > scores[9].score;
    }

    void updateHighscores() {
        int index = findHighScorePostion();
        for (int i = scores.length -1; i > index; i--) {
            scores[i] = scores[i-1];
        }
        scores[index] = new HighScoreEntry(playerName, possibleNewHighscore);
        updateHighscoreFile();
    }

    private int findHighScorePostion() {
        for (int i = 0; i < scores.length; i++) {
            if (possibleNewHighscore > scores[i].score) {
                return i;
            }
        }
        return -1;
    }

    void updateHighscoreFile() {
        try (InputStream input = new FileInputStream("highscore/highscore.properties")) {
            Properties props = new Properties();
            props.load(input);

            for (int i = 0; i <= 9; i++) {
                props.setProperty(i + ".name", scores[i].name);
                props.setProperty(i + ".score", String.valueOf(scores[i].score));
            }
            props.store(new FileOutputStream("highscore/highscore.properties"),null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadHighScoreFile() {
        try (InputStream input = new FileInputStream("highscore/highscore.properties")) {
            Properties props = new Properties();
            props.load(input);

            scores = new HighScoreEntry[10];
            for (int i = 0; i <= 9; i++) {
                scores[i] = new HighScoreEntry((String) props.get(i + ".name"),
                                                Integer.parseInt((String) props.get(i + ".score")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void renderHighScore() {
        Utils.spriteBatch.begin();
        for (int i = 0; i <=9; i++) {
            int j = i+1;
            String msg = j + ". " + scores[i].name + ": " + scores[i].score;
            font.draw(Utils.spriteBatch, msg, 225, 670 - i*40);
        }
        Utils.spriteBatch.end();
    }

    @Override
    public void input(String s) {
        playerName = s;
        updateHighscores();
    }

    @Override
    public void canceled() {
        playerName = "defaultName";
        updateHighscores();
    }

    private class HighScoreEntry {

        public String name;
        public int score;

        public HighScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}
