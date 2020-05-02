package com.arnold.spaceinvaders.screens;

import com.arnold.spaceinvaders.SpaceInvaders;
import com.arnold.spaceinvaders.model.gameobjects.Player;
import com.arnold.spaceinvaders.spawners.EnemySpawner;
import com.arnold.spaceinvaders.utils.AnimationManager;
import com.arnold.spaceinvaders.utils.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Class containing the gameplay screen
 *
 * author: Marc Arnold
 */
public class GameplayScreen extends AbstractGameScreen {

    SpaceInvaders game;
    private Stage stage;

    EntityManager entityManager = EntityManager.getEntityManager();
    AnimationManager animationManager = AnimationManager.getAnimationManager();
    EnemySpawner enemySpawner = EnemySpawner.getEnemySpawner();

    private Player player;

    public GameplayScreen(SpaceInvaders game){
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        player = new Player();
        entityManager.addEntity(player);
        initMovingBackgroung();
        playMusic(assetManager.sounds.get("GameMusic"));
    }

    @Override
    public void render(float delta) {
        clear();
        renderMovingBackground();
        animationManager.renderAnimations();
        entityManager.updateEntities();
        entityManager.renderEntities();
        enemySpawner.spawn();

        if (isGameOver()) {
            stopMusic();
            entityManager.removeAllEntities();
            animationManager.removeAllAnimations();
            enemySpawner.reset();
            game.setScreen(new HighscoreScreen(game, player.getScore()));
        }
    }

    /**
     *  Checks whether the game is over
     *
     * @return boolean
     */
    boolean isGameOver() {
        return player.getLives() == 0;
    }
}
