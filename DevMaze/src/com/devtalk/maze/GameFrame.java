package com.devtalk.maze;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameFrame implements ApplicationListener {
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
		
<<<<<<< HEAD
		maze = new Maze(50, 30);
		inputProcessor = new MazeInputProcessor(camera);
		Gdx.input.setInputProcessor(inputProcessor);
=======
		Gdx.input.setInputProcessor(new MazeInputProcessor(camera));
		
		maze = new Maze(50, 30);

		openTile:
		for (int i = 0; i < maze.tiles.length; i ++)
			for (int j = 0; j < maze.tiles[0].length; j ++)
				if (maze.tiles[i][j].inMaze()) 
				{
					camera.translate(i*32 - camera.viewportWidth/2, j*32 - camera.viewportHeight/2);
					break openTile;
				}
>>>>>>> origin/CameraPanning
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
		for (int i = 0; i < maze.tiles.length; i ++)
		{
			for (int j = 0; j < maze.tiles[0].length; j++)
			{
				if (maze.tiles[i][j].inMaze() )
					batch.draw(IN_MAZE, i*32, j*32);
				else
					batch.draw(NOT_IN_MAZE, i*32, j*32);
			}
		}
		batch.draw(PLAYER, camera.position.x, camera.position.y);
		batch.end();
		
	}
	
	private boolean handleKeys()
	{
		Vector3 test = camera.position;
		
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
    		int x = (int) ((test.x - 3) / 32);
    		int y = (int) (test.y / 32);
    		
    		System.out.println(x + ", " + y);
    		System.out.println(maze.tiles[x][y].inMaze());
    		
    		if (maze.tiles[x][y].inMaze())
    			camera.translate(-3, 0, 0);
        }
        
	    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
	    {
    		int x = (int) ((test.x + 3 + 32) / 32);
    		int y = (int) (test.y / 32);
	    	
    		if (maze.tiles[x][y].inMaze()) 
    			camera.translate(3, 0, 0);
	    }
	    	
	    if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
	    {
    		int x = (int) (test.x / 32);
    		int y = (int) ((test.y - 3) / 32);
    		
    		if (maze.tiles[x][y].inMaze()) 
    			camera.translate(0, -3, 0);
	    }
	    
	    if(Gdx.input.isKeyPressed(Input.Keys.UP))
	    {
    		int x = (int) (test.x / 32);
    		int y = (int) ((test.y + 3 + 32) / 32);
    		
    		if (maze.tiles[x][y].inMaze()) 
    			camera.translate(0, +3, 0);
	    }
	    
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
