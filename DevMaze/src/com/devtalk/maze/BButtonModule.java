package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BButtonModule implements HUDModule {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private static Texture button = new Texture(Gdx.files.internal("BBUTTON.png"));
	
	public BButtonModule(DevMaze g) {
		this.batch = g.batch;
		this.camera = g.camera;
	}

	@Override
	public void render() {
		float x = camera.position.x + (camera.viewportWidth / 2) - (3 * button.getWidth());
		float y = camera.position.y - (camera.viewportHeight / 2) + (button.getHeight() / 2);
		batch.draw(button, x, y, button.getWidth(), button.getHeight());
	}

	@Override
	public boolean actionedAt(int x, int y) {
		return false;
	}
	
	private Rectangle rectangle() {
		float x = camera.position.x + (camera.viewportWidth / 2) - (3 * button.getWidth());
		float y = camera.position.y - (camera.viewportHeight / 2) + (button.getHeight() / 2);
		return new Rectangle(x, y, button.getWidth(), button.getHeight());
	}

	@Override
	public void stopAction(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
