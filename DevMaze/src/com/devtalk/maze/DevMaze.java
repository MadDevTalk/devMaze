package com.devtalk.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devtalk.maze.Monster.MonsterType;

public class DevMaze extends Game {

	protected SpriteBatch batch;
	protected BitmapFont font;
	protected OrthographicCamera camera;
    
	protected Maze maze;
	protected Player player;
	protected PuppetMaster monsterHandler;	
	
	protected MainMenuScreen mainMenuScreen;
	protected GameScreen gameScreen;

	public void create() {
		
		// Create batch and font
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		// Create Camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		// Create game objects
		maze = new Maze();
		player = new Player(this);
		monsterHandler = new PuppetMaster(50, MonsterType.EASY, this);
		
		// Create screens
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		
		// Start at menu
		this.setScreen(mainMenuScreen);
		
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