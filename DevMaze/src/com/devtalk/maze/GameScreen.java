package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

	public static final int EDGE_SIZE_PX = 64;
	public static final int PLAYER_SIZE_PX = 32;
	public static final int KEY_VEL_PxPer60S = 5;
	public static final int SPEED_LATCH_PX = 32;
	
	private DevMaze game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Maze maze;
	private Player player;
	private PuppetMaster monsterHandler;
	private HUD hud;
	
	private InputMultiplexer inputMultiplexer;

	public GameScreen(DevMaze g) {
		
		// Get reference to our game objects
		this.game = g;
		this.camera = g.camera;
		this.batch = g.batch;
		this.maze = g.maze;
		this.player = g.player;
		this.monsterHandler = g.monsterHandler;
		this.hud = new HUD(g);

		// Set our input processor
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
		//inputMultiplexer.addProcessor(new MazeInputProcessor(g));
		inputMultiplexer.addProcessor(new HUDInputProcessor(g, this.hud));
	}

	// The main loop, fires @ 60 fps
	// LibGDX combines the main and user input threads
	public void render(float delta) {
		
		// Set the camera on the player's current position
		player.updatePos();
		camera.position.set(player.position);
		
		// Check if at end
		if (maze.end.rectangle().contains(player.rectangle)) {
			if (!game.levels.isEmpty()) {
				game.currentLevel = game.levels.remove(0);
				game.setScreen(game.levelFinishScreen);
			}
		}
		
		// Update the monsters' current position
		monsterHandler.updateMonsters();

		// Clear the screen to deep blue and update the camera
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);   // R,G,B,A (0.0f - 1.0f)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		// Tell batch to use the same coordinates as the camera
		batch.setProjectionMatrix(camera.combined);
		
		// Draw everything
		batch.begin();
		{
			// **DRAW MAZE** //
			maze.render();
			
			// **DRAW ITEMS** //			
			
			// **DRAW MONSTERS** //
			monsterHandler.render();
		
			// **DRAW PLAYER** //
			player.render();
			
			// **DRAW HUD** //
			hud.render();
		}
		batch.end();

	}

	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	// Save user app information
	public void pause() {
		// TODO
	}

	// Return from pause
	public void resume() {
		// TODO
	}

	// Kills the app. Calls a pause first
	public void dispose() {
		// TODO
	}

	@Override
	public void show() {
		// TODO
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}
}
