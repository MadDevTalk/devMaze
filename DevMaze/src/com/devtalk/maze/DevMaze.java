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
	protected PauseScreen pauseScreen;

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
		monsterHandler = new PuppetMaster(this);
		
		// Create screens
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		pauseScreen = new PauseScreen(this);
		
		// Start at menu
		this.setScreen(mainMenuScreen);
		
	}
	
	public void newGame() {
		// Reset game elements based on current level
		maze.create(11, 15);
		player.set(GameScreen.EDGE_SIZE_PX + 2, GameScreen.EDGE_SIZE_PX + 2);
		monsterHandler.set(10, MonsterType.EASY);
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		// Dispose LibGDX stuff
		batch.dispose();
		font.dispose();
		
		// Dispose game objects
		maze.dispose();
		player.dispose();
		monsterHandler.dispose();
		
		// Dispose Screens
		mainMenuScreen.dispose();
	    gameScreen.dispose();
	}

}