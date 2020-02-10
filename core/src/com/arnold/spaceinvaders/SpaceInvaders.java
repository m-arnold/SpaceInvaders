package com.arnold.spaceinvaders;

import com.arnold.spaceinvaders.model.gameobjects.Player;
import com.arnold.spaceinvaders.spawners.EnemySpawner;
import com.arnold.spaceinvaders.utils.AnimationManager;
import com.arnold.spaceinvaders.utils.AssetManager;
import com.arnold.spaceinvaders.utils.EntityManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceInvaders extends ApplicationAdapter {

	EntityManager entityManager;
	EnemySpawner enemySpawner;
	AssetManager assetManager;

	private Texture background1;
	private float background1Pos;
	private Texture background2;
	private float background2Pos;

	private AnimationManager animationManager;

	@Override
	public void create () {
		entityManager = EntityManager.getEntityManager();
		animationManager = AnimationManager.getAnimationManager();
		// Important to do here. This will trigger the preload of all assests.
		assetManager = AssetManager.getAssetManager();

		entityManager.addEntity(new Player());
		enemySpawner = EnemySpawner.getEnemySpawner();

		background1 = assetManager.textures.get("Background");
		background1Pos = 0;
		background2 = assetManager.textures.get("Background");
		background2Pos = background1.getHeight();
	}

	@Override
	public void render () {
		clear();
		renderBackground();
		animationManager.renderAnimations();
		entityManager.updateEntities();
		entityManager.renderEntities();
		enemySpawner.spawn();
	}
	
	@Override
	public void dispose () {}

	private void clear(){
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void renderBackground(){
		SpriteBatch spriteBatch = new SpriteBatch();
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
}
