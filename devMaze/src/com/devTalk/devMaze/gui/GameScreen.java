package com.devTalk.devMaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devTalk.devMaze.actors.ItemHandler;
import com.devTalk.devMaze.actors.ActorHandler;
import com.devTalk.devMaze.actors.Player;
import com.devTalk.devMaze.maze.DevMaze;
import com.devTalk.devMaze.maze.Maze;

public class GameScreen implements Screen {

	private DevMaze game;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Maze maze;
	private Player player;
	private ActorHandler monsterHandler;
	private ItemHandler itemHandler;
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
		this.itemHandler = g.itemHandler;
		this.hud = new HUD(g);

		// Set our input processor
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new HUDInputProcessor(g, this.hud));
		inputMultiplexer.addProcessor(new MazeInputProcessor(g));
		Gdx.input.setInputProcessor(inputMultiplexer);
	}


	// Kills the app. Calls a pause first
	public void dispose() {
		this.hud.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	// Save user app information
	public void pause() {
		// TODO
	}

	// The main loop, fires @ 60 fps
	// LibGDX combines the main and user input threads
	public void render(float delta) {

		if (!game.pause) {

			// Set the camera on the player's current position
			player.updatePos();
			camera.position.set(player.position);

			// Check if at end
			if (maze.end.rectangle.contains(player.rectangle)) {
				if (!game.levels.isEmpty()) {
					game.currentLevel = game.levels.remove(0);
					game.setScreen(game.levelFinishScreen);
				}
			}

			// Update the monsters' current position
			monsterHandler.updateMonsters();

		}

		// update items
		itemHandler.updateItems();

		// Clear the screen to deep blue and update the camera
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); // R,G,B,A (0.0f - 1.0f)
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
			itemHandler.render();

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
		//TODO;
	}

	// Return from pause
	public void resume() {
		// TODO
	}

	@Override
	public void show() {
		// TODO
	}
}