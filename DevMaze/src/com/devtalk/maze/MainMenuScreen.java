package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreen implements Screen {

	final DevMaze game;
	OrthographicCamera camera;
	Rectangle newGame, resume;
	int i;

	public MainMenuScreen(final DevMaze game) {
		this.game = game;
		i = 0;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);   // R,G,B,A (0.0f - 1.0f)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.font.draw(game.batch, "Welcome to DevMaze! ", 100, 150);
		game.font.draw(game.batch, "This screen is your first challenge.", 100,
				100);
		game.batch.end();

		if (Gdx.input.justTouched()) {
			game.setScreen(new GameScreen(game));
			this.dispose();
//			System.out.println(i);
//			i++;
		}
//		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
//			game.setScreen(new GameScreen(game));
//			this.dispose();
//		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

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

	}

}
