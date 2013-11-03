package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

	public GameScreen(DevMaze g) {
		
		// Get reference to our game objects
		this.game = g;
		this.camera = g.camera;
		this.batch = g.batch;
		this.maze = g.maze;
		this.player = g.player;
		this.monsterHandler = g.monsterHandler;
<<<<<<< HEAD
		
		// Reset game elements based on current level
		maze.reset(); maze.makeSwatch(5, 5);
		player.reset();
		monsterHandler.reset();
=======
		this.hud = new HUD(g);
>>>>>>> Combat

		// Set our input processor
		Gdx.input.setInputProcessor(new MazeInputProcessor(g));
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
			// **DRAW MAZE** //
<<<<<<< HEAD
			for (int i = 0; i < maze.tiles.length; i++)
				for (int j = 0; j < maze.tiles[0].length; j++) {
					float x = maze.tiles[i][j].rectangle.x;
					float y = maze.tiles[i][j].rectangle.y;
					Vector3 tile = new Vector3(x, y, 0);
		
					if (camera.frustum.sphereInFrustum(tile, EDGE_SIZE_PX)) {
						if (maze.tiles[i][j].inMaze) {
							batch.draw(maze.tiles[i][j].texture(), 
									j * EDGE_SIZE_PX, i * EDGE_SIZE_PX);
							
						}
						// Toggle index overlay
						font.draw(batch, maze.tiles[i][j].toString(), j * EDGE_SIZE_PX + 15, i * EDGE_SIZE_PX + 35);
					}
				}
=======
			maze.render();
			
			// **DRAW ITEMS** //			
			
			// **DRAW MONSTERS** //
			monsterHandler.render();
>>>>>>> Combat
		
			// **DRAW PLAYER** //
			player.render();
			
<<<<<<< HEAD
			// **DRAW MONSTERS** //
//			for (Monster monster : monsterHandler.monsters)
//			{
//				tmp = monster.texture(Gdx.graphics.getDeltaTime());
//				batch.draw(tmp, monster.position.x, monster.position.y,
//						(tmp.getRegionWidth() / 2), (tmp.getRegionHeight() / 2),
//						tmp.getRegionWidth(), tmp.getRegionHeight(), 1, 1,
//						monster.angle());
//				
//				// TODO: one debug bool that toggles all debug drawing
//				font.draw(batch, monster.toString(), monster.position.x, monster.position.y);
//				font.draw(batch, "HP: " + monster.currentHealth + "/" + monster.totalHealth,
//						monster.position.x, monster.position.y);
//			}
=======
			// **DRAW HUD** //
			hud.render();
		}
>>>>>>> Combat
		batch.end();

		// TODO: Put in game screen input processor
		// **REGISTER INPUTS** //
		boolean space = Gdx.input.isKeyPressed(Keys.SPACE);
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();

			if ((x < 64 && y < 64) || space) {
<<<<<<< HEAD
				// Pause?
=======
				game.setScreen(game.pauseScreen);
>>>>>>> Combat
			}
		}
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
