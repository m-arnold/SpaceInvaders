package com.arnold.spaceinvaders;

import com.arnold.spaceinvaders.screens.MainMenuScreen;
import com.arnold.spaceinvaders.utils.AssetManager;
import com.badlogic.gdx.Game;

public class SpaceInvaders extends Game {

	@Override
	public void create () {
		// It is important to call getAssetManager at this point, since this will
		// instantiate the singleton for the first time and trigger the preload of all assets.
		AssetManager.getAssetManager();
		this.setScreen(new MainMenuScreen(this));
	}
}
