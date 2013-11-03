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
	
	private static Texture button = new Texture(Gdx.files.internal("PAUSE.png"));
	
	public PauseModule(DevMaze g) {
		this.game = g;
		this.batch = g.batch;
		this.camera = g.camera;
	}

	@Override
	public void render() {
        batch.draw(button, camera.position.x - camera.viewportWidth / 2, 
                        (camera.position.y + camera.viewportHeight / 2) - button.getHeight());
	}

	@Override
	public boolean actionedAt(int x, int y) {
		if (this.rectangle().contains(x, y)) {
			game.setScreen(game.pauseScreen);
			return true;
		}
		
		return false;
	}

	private Rectangle rectangle() {
		return new Rectangle(camera.position.x - camera.viewportWidth / 2, 
                (camera.position.y + camera.viewportHeight / 2) - button.getHeight(),
                button.getWidth(), button.getHeight());
	}

	@Override
	public void stopAction(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
}
