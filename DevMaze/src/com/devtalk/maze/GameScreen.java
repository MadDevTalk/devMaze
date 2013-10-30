package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.devtalk.maze.Monster.MonsterType;

public class GameScreen implements Screen {

	public static final int EDGE_SIZE_PX = 64;
	public static final int PLAYER_SIZE_PX = 32;
	public static final int KEY_VEL_PxPer60S = 5;
	public static final int SPEED_LATCH_PX = 32;
	
	DevMaze game;

	public GameScreen(DevMaze g) {
		 
		// Get reference to our game
		this.game = g;
		
		// For the objects down below, do we want to actually create
		// new objects or 'reset' our current objects? I think it makes
		// more sense to do a reset to reduce garbage collection, but
		// then we would need to decide what to do with the objects when
		// they are not in use. Maybe a clear function...
		
		// Create maze
		game.maze = new Maze(11, 15);// must be odd

		// Create player
		game.player = new Player(EDGE_SIZE_PX + 2, EDGE_SIZE_PX + 2, g);
		
		// Create Monsters
		game.monsterHandler = new PuppetMaster(50, MonsterType.EASY, g);

		// Set our input processor
		Gdx.input.setInputProcessor(new MazeInputProcessor(game));
		
	}

	// The main loop, fires @ 60 fps
	// LibGDX combines the main and user input threads
	public void render(float delta) {
		
		// Set the camera on the player's current position
		game.player.updatePos();
		game.camera.position.set(game.player.position);
		
		// Update the monsters' current position
		game.monsterHandler.updateMonsters();

		// Clear the screen to deep blue and update the camera
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);   // R,G,B,A (0.0f - 1.0f)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		game.camera.update();

		// Tell batch to use the same coordinates as the camera
		game.batch.setProjectionMatrix(game.camera.combined);
		
		// Draw everything
		game.batch.begin();
		{
			// **DRAW MAZE** //
			for (int i = 0; i < game.maze.tiles.length; i++)
				for (int j = 0; j < game.maze.tiles[0].length; j++) {
					float x = game.maze.tiles[i][j].rectangle().x;
					float y = game.maze.tiles[i][j].rectangle().y;
					Vector3 tile = new Vector3(x, y, 0);
		
					if (game.camera.frustum.sphereInFrustum(tile, EDGE_SIZE_PX))
						if (game.maze.tiles[i][j].inMaze())
							game.batch.draw(game.maze.tiles[i][j].texture(), 
									j * EDGE_SIZE_PX, i * EDGE_SIZE_PX);
				}
		
			// **DRAW PLAYER** //
			TextureRegion tmp = game.player.texture(Gdx.graphics.getDeltaTime());
			game.batch.draw(tmp, game.player.position.x, game.player.position.y,
					(tmp.getRegionWidth() / 2), (tmp.getRegionHeight() / 2),
					tmp.getRegionWidth(), tmp.getRegionHeight(), 1, 1,
					game.player.angle());
			game.font.draw(game.batch, "HP: " + game.player.currentHealth + "/" + game.player.totalHealth,
					game.player.position.x, game.player.position.y);
		
			// **DRAW ITEMS** //
			
			// **DRAW MONSTERS** //
			for (Monster monster : game.monsterHandler.monsters)
			{
				tmp = monster.texture(Gdx.graphics.getDeltaTime());
				game.batch.draw(tmp, monster.position.x, monster.position.y,
						(tmp.getRegionWidth() / 2), (tmp.getRegionHeight() / 2),
						tmp.getRegionWidth(), tmp.getRegionHeight(), 1, 1,
						monster.angle());
				
				// TODO: one debug bool that toggles all debug drawing
				// game.font.draw(game.batch, monster.toString(), monster.position.x, monster.position.y);
				game.font.draw(game.batch, "HP: " + monster.currentHealth + "/" + monster.totalHealth,
						monster.position.x, monster.position.y);
			}
		}
		game.batch.end();

		// TODO: Put in game screen input processor
		// **REGISTER INPUTS** //
		boolean space = Gdx.input.isKeyPressed(Keys.SPACE);
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();

			if ((x < 64 && y < 64) || space) {
				game.setScreen(new PauseScreen(game, this));
				this.dispose();
			}
		}
	}

	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	// Save user app information
	public void pause() {

	}

	// Return from pause
	public void resume() {

	}

	// Kills the app. Calls a pause first
	public void dispose() {

	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}
}
