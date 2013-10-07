package com.devtalk.maze;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DevMaze {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "DevMaze";
		cfg.useGL20 = false;
		cfg.width = 1243;
		cfg.height = 768;
		
		new LwjglApplication(new GameFrame(), cfg);
	}
}