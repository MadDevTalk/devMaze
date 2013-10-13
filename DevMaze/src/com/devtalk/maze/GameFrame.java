package com.devtalk.maze;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameFrame implements ApplicationListener {
	MazeInputProcessor inputProcessor;
	OrthographicCamera camera;
	SpriteBatch batch;
	
	Texture IN_MAZE;
	Texture NOT_IN_MAZE;
	Texture PLAYER;
	
	Maze maze;
	
	// Runs when the application is first instantiated
	public void create() {

		// Create Camera and SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		// Load assets
		IN_MAZE = new Texture(Gdx.files.internal("IN_MAZE.png"));
		NOT_IN_MAZE = new Texture(Gdx.files.internal("NOT_IN_MAZE.png"));
		PLAYER = new Texture(Gdx.files.internal("char.png"));
		
		maze = new Maze(50, 30);
		inputProcessor = new MazeInputProcessor(camera);
		Gdx.input.setInputProcessor(inputProcessor);
	}
	
	// The main loop, fires @ 60 fps 
	// LibGDX combines the main and user input threads
	public void render() {
		handleKeys();
		
		// Clear the screen to deep blue and update the camera
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		// Tell batch to use the same coordinates as the camera
		batch.setProjectionMatrix(camera.combined);
		
		// Draw everything
		batch.begin();
		
		int row, col = 0;
		for (int i = 0; i < maze.tiles.length; i ++)
		{
			row = i * 32;
			for (int j = 0; j < maze.tiles[0].length; j++)
			{
				col = j * 32;
				if (maze.tiles[i][j].inMaze() )
					batch.draw(IN_MAZE, row, col);
				else
					batch.draw(NOT_IN_MAZE, row, col);
			}
		}

		batch.draw(PLAYER, camera.position.x, camera.position.y);
		batch.end();
		
	}
	
	private boolean handleKeys()
	{
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
        	camera.translate(-3, 0, 0);
        
	    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
	    	camera.translate(3, 0, 0);
	    
	    if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
	    	camera.translate(0, -3, 0);
	    
	    if(Gdx.input.isKeyPressed(Input.Keys.UP))
	    	camera.translate(0, 3, 0);
	    
	    return false;
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
