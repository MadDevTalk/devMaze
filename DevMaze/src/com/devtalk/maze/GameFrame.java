package com.devtalk.maze;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameFrame implements ApplicationListener {
	
	public static final int EDGE_SIZE_PX = 32;
	public static final int KEY_VEL_PxPer60S = 4;
	
	OrthographicCamera camera;
	SpriteBatch batch;
	
	Texture IN_MAZE;
	Texture NOT_IN_MAZE;
	Texture PLAYER;
	
	Maze maze;
	Player player;
	
	// Runs when the application is first instantiated
	public void create() {

		// Create Camera and SpriteBatch (pretty much always)
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
		IN_MAZE = new Texture(Gdx.files.internal("IN_MAZE.png"));
		NOT_IN_MAZE = new Texture(Gdx.files.internal("NOT_IN_MAZE.png"));
		PLAYER = new Texture(Gdx.files.internal("char.png"));
		
		player = new Player((int) camera.position.x, (int) camera.position.y);
		Gdx.input.setInputProcessor(new MazeInputProcessor(player));
		
		maze = new Maze(50, 30);

		// search for an open tile to place player, this should be change
		// TODO: place at start of maze
		openTile:
		for (int i = 0; i < maze.tiles.length; i ++)
			for (int j = 0; j < maze.tiles[0].length; j ++)
				if (maze.tiles[i][j].inMaze()) 
				{
					camera.translate(i*EDGE_SIZE_PX - camera.viewportWidth/2, j*EDGE_SIZE_PX - camera.viewportHeight/2);
					break openTile;
				}
	}
	
	// The main loop, fires @ 60 fps 
	// LibGDX combines the main and user input threads
	public void render() {
		camera.translate(player.velocity);
		if (player.dragged)
		{
			player.velocity = new Vector3();
			player.dragged = false;
		}
		
		// Clear the screen to deep blue and update the camera
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		// Tell batch to use the same coordinates as the camera
		batch.setProjectionMatrix(camera.combined);
		
		// Draw everything
		batch.begin();
		for (int i = 0; i < maze.tiles.length; i ++)
		{
			for (int j = 0; j < maze.tiles[0].length; j++)
			{
				if (maze.tiles[i][j].inMaze() )
					batch.draw(IN_MAZE, i * EDGE_SIZE_PX, j * EDGE_SIZE_PX);
				else
					batch.draw(NOT_IN_MAZE, i * EDGE_SIZE_PX, j * EDGE_SIZE_PX);
			}
		}
		batch.draw(PLAYER, camera.position.x, camera.position.y);
		batch.end();
		
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
}
