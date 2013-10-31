package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devtalk.maze.Level.LEVEL;

public class DevMaze extends Game {

	//private static Texture SWATCH = new Texture(Gdx.files.internal("SWATCH.png"));
	
	protected SpriteBatch batch;
	protected BitmapFont font;
	protected OrthographicCamera camera;
    
	protected Maze maze;
	protected Player player;
	protected PuppetMaster monsterHandler;	
	
	protected MainMenuScreen mainMenuScreen;
	protected GameScreen gameScreen;
	protected PauseScreen pauseScreen;
	protected LevelFinishScreen levelFinishScreen;
	
	protected List<Level> levels;
	protected Level currentLevel;

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
		this.levels = new ArrayList<Level>();
		
		// Create screens
		this.mainMenuScreen = new MainMenuScreen(this);
		this.gameScreen = new GameScreen(this);
		this.pauseScreen = new PauseScreen(this);
		this.levelFinishScreen = new LevelFinishScreen(this);
		
		// Start at menu
		this.setScreen(mainMenuScreen);	
	}
	
	public void newGame() {
		// Reset levels
		this.levels.clear();
		this.levels.add(new Level(LEVEL.LEVEL_1));
		this.levels.add(new Level(LEVEL.LEVEL_2));
		this.levels.add(new Level(LEVEL.LEVEL_3));
		this.levels.add(new Level(LEVEL.LEVEL_4));
		this.levels.add(new Level(LEVEL.LEVEL_5));
		this.levels.add(new Level(LEVEL.LEVEL_6));
		
		// Start at current level
		this.currentLevel = this.levels.remove(0);
		
		// Set game objects
		this.maze.create(currentLevel.mazeHeight, currentLevel.mazeWidth);
		this.player.set(GameScreen.EDGE_SIZE_PX + 2, GameScreen.EDGE_SIZE_PX + 2);
		this.monsterHandler.set(currentLevel.numMonsters, currentLevel.monsterDifficulty);
	}
	
	public void newLevel() {
		// Set game objects
		this.maze.create(currentLevel.mazeHeight, currentLevel.mazeWidth);
		this.player.set(GameScreen.EDGE_SIZE_PX + 2, GameScreen.EDGE_SIZE_PX + 2);
		this.monsterHandler.set(currentLevel.numMonsters, currentLevel.monsterDifficulty);
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