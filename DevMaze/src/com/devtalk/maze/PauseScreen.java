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
		menu = new Rectangle(x, y, 128, 64);
		y = 155;
		resume = new Rectangle(x, y, 128, 64);
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);   // R,G,B,A (0.0f - 1.0f)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		{
			batch.draw(menuColor, menu.x, menu.y);   // Double draw is a hack 
			batch.draw(menuColor, menu.x + menuColor.getWidth(), menu.y);   // around the ^2 rule
			font.draw(game.batch, "MENU", menu.x + 20, menu.y + 20);
			
			batch.draw(resumeColor, resume.x, resume.y);
			batch.draw(resumeColor, resume.x + resumeColor.getWidth(), resume.y);
			font.draw(game.batch, "RESUME", resume.x + 20, resume.y + 20);
		}
		game.batch.end();

		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = (int) (camera.viewportHeight - Gdx.input.getY());   // Translate to Camera coordinates
			
			if(menu.contains(x, y)) {
				game.setScreen(game.mainMenuScreen);
			} else if(resume.contains(x, y))
				game.setScreen(game.gameScreen);
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
