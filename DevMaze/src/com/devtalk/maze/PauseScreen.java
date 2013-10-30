package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PauseScreen implements Screen {

	private DevMaze game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	
	Texture menuColor, resumeColor;
	Rectangle menu, resume;

	public PauseScreen(DevMaze g) {
		this.game = g;
		this.camera = g.camera;
		this.batch = g.batch;
		this.font = g.font;
		
		menuColor = new Texture(Gdx.files.internal("MENU.png"));
		resumeColor = new Texture(Gdx.files.internal("RESUME.png"));

		int x, y;
		// Place the buttons
		x = 100;
		y = 75;
		menu = new Rectangle(x, y, 192, 64);
		y = 155;
		resume = new Rectangle(x, y, 192, 64);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);   // R,G,B,A (0.0f - 1.0f)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		{
			batch.draw(menuColor, 100, 75);   // Double draw is a hack 
			batch.draw(menuColor, 164, 75);   // around the ^2 rule
			font.draw(game.batch, "MENU", 125, 100);
			
			batch.draw(resumeColor, 100, 155);
			batch.draw(resumeColor, 164, 155);
			font.draw(game.batch, "RESUME", 125, 180);
		}
		game.batch.end();

		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = 480 - Gdx.input.getY();   // Translate to Camera coordinates
			
			if(menu.contains(x, y)) {
				game.setScreen(game.mainMenuScreen);
			}
			else if(resume.contains(x, y)) {
				game.setScreen(game.gameScreen);
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
		menuColor.dispose();
		resumeColor.dispose();

	}
}
