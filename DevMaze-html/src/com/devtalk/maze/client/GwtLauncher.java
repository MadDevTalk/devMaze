package com.devtalk.maze.client;

import com.devtalk.maze.DevMaze;
import com.devtalk.maze.GameScreen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig() {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(480,
				320);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener() {
		return new DevMaze();
	}
}