package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	Texture pauseButton = new Texture(Gdx.files.internal("PAUSE.png"));
	
	public HUD(DevMaze g) {
		camera = g.camera;
		batch = g.batch;
	}
	
	public void render() {
		batch.draw(pauseButton, camera.position.x - camera.viewportWidth / 2, 
				camera.position.y + camera.viewportHeight / 2 - pauseButton.getHeight());
	}
}
