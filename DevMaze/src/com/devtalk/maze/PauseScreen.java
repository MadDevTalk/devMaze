package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PauseScreen implements Screen {

	final DevMaze game;
	OrthographicCamera camera;
	Texture menuColor, resumeColor;
	GameScreen gamestate;
	Rectangle menu, resume;
	int x, y;

	public PauseScreen(final DevMaze game, GameScreen gamestate) {
		this.game = game;
		this.gamestate = gamestate;
		
		menuColor = new Texture(Gdx.files.internal("MENU.png"));
		resumeColor = new Texture(Gdx.files.internal("RESUME.png"));
		
		// Place the buttons
		x = 800/2 - 192/2;
		y = 480/2 + 64/2 + 32;
		menu = new Rectangle(x, y, 128, 64);
		y = 480/2 - 64/2 - 32;
		resume = new Rectangle(x, y, 128, 64);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(menuColor, menu.x, menu.y);
		game.batch.draw(resumeColor, resume.x, resume.y);
		game.batch.end();

		if (Gdx.input.justTouched()) {
			x = Gdx.input.getX();
			y = Gdx.input.getY();
			
			if(x < 64) {
				if(y < 64) {
					game.setScreen(new MainMenuScreen(game));
					this.dispose();
				}
				else if(y > 64*4 && y < 64*5) {
					game.setScreen(gamestate);
					this.dispose();
				}
			}
		}
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
		// TODO Auto-generated method stub

	}
}
