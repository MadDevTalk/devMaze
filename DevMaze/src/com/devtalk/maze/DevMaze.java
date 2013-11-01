package com.devtalk.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devtalk.maze.Monster.MonsterType;

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

	public void create() {
		
		// Create batch and font
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		// Create Camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		// Create game objects
		// TODO: these constructors should not take params,
		// instead, these params should be set when necessary,
		// with some kind of reset function
		maze = new Maze(11, 15);// must be odd
		player = new Player(GameScreen.EDGE_SIZE_PX + 2, GameScreen.EDGE_SIZE_PX + 2, this);
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