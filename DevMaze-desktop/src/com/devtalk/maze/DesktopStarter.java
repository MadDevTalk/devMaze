package com.devtalk.maze;

import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class DesktopStarter {
	public static void main(String[] args) throws IOException {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		TexturePacker2.process("img", "../DevMaze-android/assets/", "starters.pak");
		cfg.title = "DevMaze";
		cfg.useGL20 = false;
		cfg.width = 800;
		cfg.height = 480;
		
		new LwjglApplication(new DevMaze(), cfg);
	}
}
