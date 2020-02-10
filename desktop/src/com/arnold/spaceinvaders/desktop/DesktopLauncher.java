package com.arnold.spaceinvaders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.arnold.spaceinvaders.SpaceInvaders;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title="SpaceInvaders";
		config.fullscreen = false;
		config.resizable = false;
		config.height = 1000;
		config.width = 1000;

		new LwjglApplication(new SpaceInvaders(), config);
	}
}
