package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {
	final DevMaze game;
	
	public static final int EDGE_SIZE_PX = 64;
	public static final int PLAYER_SIZE_PX = 32;
	public static final int KEY_VEL_PxPer60S = 10;
	
	OrthographicCamera camera;
	Texture IN_MAZE;
	Texture NOT_IN_MAZE;
	Texture PLAYER;
	Maze maze;
	MazeInputProcessor inputProcessor;
	Player player;

	public GameScreen(final DevMaze g) {
		this.game = g;
		maze = new Maze(50, 30);
		
		// Create Camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		player = new Player((int)camera.position.x, (int)camera.position.y, maze);
		
		Gdx.input.setInputProcessor(new MazeInputProcessor(player));

		// Load assets
		IN_MAZE = new Texture(Gdx.files.internal("IN_MAZE.png"));
		NOT_IN_MAZE = new Texture(Gdx.files.internal("NOT_IN_MAZE.png"));
		PLAYER = new Texture(Gdx.files.internal("char.png"));

		openTile:
		for (int i = 0; i < maze.tiles.length; i ++)
			for (int j = 0; j < maze.tiles[0].length; j ++)
				if (maze.tiles[i][j].inMaze()) 
				{
					player.set(j * EDGE_SIZE_PX + 5, i * EDGE_SIZE_PX + 5);
					break openTile;
				}
	}
	
	// The main loop, fires @ 60 fps 
	// LibGDX combines the main and user input threads
	public void render(float delta) {
		player.updatePos();
		camera.position.set(player.position);
		
		// Clear the screen to deep blue and update the camera
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		// Tell batch to use the same coordinates as the camera
		game.batch.setProjectionMatrix(camera.combined);
		// Draw everything
		game.batch.begin();
		
		for (int i = 0; i < maze.tiles.length; i ++)
			for (int j = 0; j < maze.tiles[0].length; j++)
			{
				Vector3 tile = new Vector3(maze.tiles[i][j].rectangle().x, maze.tiles[i][j].rectangle().y, 0);
				
				if (camera.frustum.sphereInFrustum(tile, EDGE_SIZE_PX))
					game.batch.draw(maze.tiles[i][j].texture(), j * EDGE_SIZE_PX, i * EDGE_SIZE_PX);
			}
		
		game.batch.draw(player.texture(), camera.position.x, camera.position.y);

		game.batch.end();
		
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
