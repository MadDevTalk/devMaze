package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LoadingScreen implements Screen {
	
	private DevMaze game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;

	public LoadingScreen(final DevMaze g) {
		this.game = g;
		this.camera = g.camera;
		this.batch = g.batch;
		this.font = g.font;
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);   // R,G,B,A (0.0f - 1.0f)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		{
			font.draw(game.batch, "Loading...", 100, 150);
		}
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	public void show() {
		 
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
