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
		x = 100;
		y = 75;
		menu = new Rectangle(x, y, 192, 64);
		y = 155;
		resume = new Rectangle(x, y, 192, 64);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(menuColor, 100, 75);
		game.batch.draw(menuColor, 164, 75);
		game.batch.draw(resumeColor, 100, 155);
		game.batch.draw(resumeColor, 164, 155);
		game.batch.end();

		if (Gdx.input.justTouched()) {
			x = Gdx.input.getX();
			y = Gdx.input.getY();
			
			if(menu.contains(x, 480 - y)) {
				//System.out.println("Boom");
				game.setScreen(new MainMenuScreen(game));
				this.dispose();
			}
			else if(resume.contains(x, 480 - y)) {
				//System.out.println("Pow");
				game.setScreen(gamestate);
				this.dispose();
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
