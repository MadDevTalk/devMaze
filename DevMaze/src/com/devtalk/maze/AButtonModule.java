package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class AButtonModule implements HUDModule {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private static Texture button = new Texture(Gdx.files.internal("ABUTTON.png"));
	
	public AButtonModule(DevMaze g) {
		this.batch = g.batch;
		this.camera = g.camera;
	}

	@Override
	public void render() {
		float x = camera.position.x + (camera.viewportWidth / 2) - (3 * button.getWidth() / 2);
		float y = camera.position.y - (camera.viewportHeight / 2) + (3 * button.getHeight() / 2);
		batch.draw(button, x, y, button.getWidth(), button.getHeight());
	}

	@Override
	public Rectangle rectangle() {
		float x = camera.position.x + (camera.viewportWidth / 2) - (3 * button.getWidth() / 2);
		float y = camera.position.y - (camera.viewportHeight / 2) + (3 * button.getHeight() / 2);
		return new Rectangle(x, y, button.getWidth(), button.getHeight());
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

}
