package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PauseScreen implements Screen {
	
	final DevMaze game;
	OrthographicCamera camera;
	TextureAtlas charsheet;
	MazeInputProcessor inputProcessor; 
	GameScreen gamestate;
	
	public PauseScreen(final DevMaze game, GameScreen gamestate) {
		this.game = game;
		//this.gamestate = gamestate;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		inputProcessor = new MazeInputProcessor(camera);
		Gdx.input.setInputProcessor(inputProcessor);
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
//		game.batch.setProjectionMatrix(camera.combined);
//		game.batch.begin();
//		
//		//game.batch.draw();
//		
//		game.batch.end();
	
		if (Gdx.input.isTouched()) {
			//game.setScreen(gamestate);
			System.out.println("Pause");
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
