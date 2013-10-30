package com.devtalk.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DevMaze extends Game {

	SpriteBatch batch;
	BitmapFont font;
	OrthographicCamera camera;

	Maze maze;
	Player player;
	PuppetMaster monsterHandler;	

	public void create() {
		// Create batch and font
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		// Create Camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		this.setScreen(new LoadingScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		
		maze.dispose();
		player.dispose();
		monsterHandler.dispose();
	}

}