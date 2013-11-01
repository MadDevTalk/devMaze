package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PauseModule implements HUDModule {
	
	private DevMaze game;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private static Texture pauseButton = new Texture(Gdx.files.internal("PAUSE.png"));
	
	public PauseModule(DevMaze g) {
		this.game = g;
		this.batch = g.batch;
		this.camera = g.camera;
	}

	@Override
	public void render() {
        batch.draw(pauseButton, camera.position.x - camera.viewportWidth / 2, 
                        (camera.position.y + camera.viewportHeight / 2) - pauseButton.getHeight());
	}

	@Override
	public Rectangle rectangle() {
		return new Rectangle(camera.position.x - camera.viewportWidth / 2, 
                (camera.position.y + camera.viewportHeight / 2) - pauseButton.getHeight(),
                pauseButton.getWidth(), pauseButton.getHeight());
	}

	@Override
	public void action() {
        game.setScreen(game.pauseScreen);
	}
	
}
