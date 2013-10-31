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
		this.batch = new SpriteBatch();
		this.font = new BitmapFont();
		
		// Create Camera
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, 800, 480);
		
		// Create game objects
		this.maze = new Maze(this);
		this.player = new Player(this);
		this.monsterHandler = new PuppetMaster(this);
		
		// Create screens
		this.mainMenuScreen = new MainMenuScreen(this);
		this.gameScreen = new GameScreen(this);
		this.pauseScreen = new PauseScreen(this);
		
		// Start at menu
		this.setScreen(mainMenuScreen);
		
	}
	
	public void newGame() {
		// Reset game elements based on current level
		this.maze.create(11, 15);
		this.player.set(GameScreen.EDGE_SIZE_PX + 2, GameScreen.EDGE_SIZE_PX + 2);
		this.monsterHandler.set(10, MonsterType.EASY);
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		// Dispose LibGDX stuff
		this.batch.dispose();
		this.font.dispose();
		
		// Dispose game objects
		this.maze.dispose();
		this.player.dispose();
		this.monsterHandler.dispose();
		
		// Dispose Screens
		this.mainMenuScreen.dispose();
	    this.gameScreen.dispose();
	}

}