package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {

	public static final int EDGE_SIZE_PX = 64;
	public static final int PLAYER_SIZE_PX = 32;
	public static final int KEY_VEL_PxPer60S = 5;
	public static final int SPEED_LATCH_PX = 32;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	
	private Maze maze;
	private Player player;
	private PuppetMaster monsterHandler;

	public GameScreen(DevMaze g) {
		// Get reference to our game objects
		this.camera = g.camera;
		this.batch = g.batch;
		this.font = g.font;
		
		this.maze = g.maze;
		this.player = g.player;
		this.monsterHandler = g.monsterHandler;
		
		// Reset game elements based on current level
		maze.reset(); maze.makeSwatch(5, 5);
		player.reset();
		monsterHandler.reset();

		// Set our input processor
		Gdx.input.setInputProcessor(new MazeInputProcessor(g));
	}

	// The main loop, fires @ 60 fps
	// LibGDX combines the main and user input threads
	public void render(float delta) {
		
		// Set the camera on the player's current position
		player.updatePos();
		camera.position.set(player.position);
		
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
		
			// **DRAW PLAYER** //
			TextureRegion tmp = player.texture(Gdx.graphics.getDeltaTime());
			batch.draw(tmp, player.position.x, player.position.y,
					(tmp.getRegionWidth() / 2), (tmp.getRegionHeight() / 2),
					tmp.getRegionWidth(), tmp.getRegionHeight(), 1, 1,
					player.angle());
			font.draw(batch, "HP: " + player.currentHealth + "/" + player.totalHealth,
					player.position.x, player.position.y);
		
			// **DRAW ITEMS** //
			
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
		batch.end();

		// TODO: Put in game screen input processor
		// **REGISTER INPUTS** //
		boolean space = Gdx.input.isKeyPressed(Keys.SPACE);
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();

			if ((x < 64 && y < 64) || space) {
				// Pause?
			}
		}
	}

	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	// Save user app information
	public void pause() {
		// TODO
		// game.setScreen(new PauseScreen(game, this));
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
