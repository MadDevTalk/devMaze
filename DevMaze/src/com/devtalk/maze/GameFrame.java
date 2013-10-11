package com.devtalk.maze;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

public class GameFrame implements ApplicationListener {
	OrthographicCamera camera;
	SpriteBatch batch;
	
	Texture IN_MAZE = new Texture(Gdx.files.internal("IN_MAZE.png"));
	Texture NOT_IN_MAZE = new Texture(Gdx.files.internal("NOT_IN_MAZE.png"));
	
	Array<Roommate> roommates;
	
	// Runs when the application is first instantiated
	public void create() {

		// Create Camera and SpriteBatch (pretty much always)
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
		roommates = new Array<Roommate>();
	
		Maze maze = new Maze();
		
		// Load assets
		roommates.add(new Roommate("triple_left", 0, 0));
		roommates.add(new Roommate("double_top_bottom", 32, 0));
		roommates.add(new Roommate("double_top_bottom", 64, 0));
		roommates.add(new Roommate("double_bottom_right", 96, 0));
		roommates.add(new Roommate("double_top_right", 96, 32));
		roommates.add(new Roommate("double_top_bottom", 64, 32));
		roommates.add(new Roommate("double_bottom_left", 32, 32));
		roommates.add(new Roommate("triple_right", 0, 32));
		roommates.add(new Roommate("double_bottom_right", 0, 64));
		roommates.add(new Roommate("double_left_right", 32, 64));

	}
	
	// The main loop, fires @ 60 fps 
	// LibGDX combines the main and user input threads
	public void render() {
		// Clear the screen to deep blue and update the camera
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		// Tell batch to use the same coordinates as the camera
		batch.setProjectionMatrix(camera.combined);
		// Draw everything
		batch.begin();
		for(Roommate roommate: roommates) {
	        batch.draw(roommate.texture(), roommate.getx(), roommate.gety());
	    }
		
		
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
