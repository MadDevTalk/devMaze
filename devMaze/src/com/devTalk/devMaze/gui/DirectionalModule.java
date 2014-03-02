package com.devTalk.devMaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.devTalk.devMaze.maze.DevMaze;

public class DirectionalModule implements HUDModule {

	public static int DPAD_SIZE_PX = 192;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Texture button = new Texture(Gdx.files.internal("DPAD.png"));
	private boolean active;
	private Vector2 midPoint;

	public DirectionalModule(DevMaze g) {
		camera = g.camera;
		batch = g.batch;
		active = false;
		midPoint = new Vector2();
	}

	public boolean actionedAt(int x, int y) {
		midPoint.set(x, y);
		return active = true;
	}

	public void dispose() {
		button.dispose();
	}

	public void render() {
		if (active) {
			float x = (camera.position.x - camera.viewportWidth / 2) + midPoint.x - (button.getWidth() / 2);
			float y = (camera.position.y + camera.viewportHeight / 2) - midPoint.y - (button.getHeight() / 2);
			batch.draw(button, x, y);
		}
	}

	public void stopAction(int x, int y) {
		active = false;
	}

}