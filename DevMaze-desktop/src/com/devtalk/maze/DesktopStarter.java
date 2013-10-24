package com.devtalk.maze;

import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopStarter {
	public static void main(String[] args) throws IOException {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();

		cfg.title = "DevMaze";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;

		new LwjglApplication(new DevMaze(), cfg);
	}
}
