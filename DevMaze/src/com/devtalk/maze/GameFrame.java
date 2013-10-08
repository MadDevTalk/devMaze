package com.devtalk.maze;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameFrame implements ApplicationListener {
	OrthographicCamera camera;
	SpriteBatch batch;
	
	Texture imgMatt, imgAle, imgChristian, imgMax;
	Music musTunak;
	Rectangle matt, ale, max, christian;
	
	// Runs when the application is first instantiated
	public void create() {

		// Create Camera and SpriteBatch (pretty much always)
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
	
		// Load assets
		imgMatt = new Texture(Gdx.files.internal("matt.png"));
		imgAle = new Texture(Gdx.files.internal("ale.png"));
		imgChristian = new Texture(Gdx.files.internal("christian.png"));
		imgMax = new Texture(Gdx.files.internal("max.png"));
		
		// Immediately set the camera and start the music
		camera.setToOrtho(false, 1243, 768);

		// Create rectangles for the textures, because SpriteBatchs can draw 
		// rectangles, but not textures
		max = new Rectangle();
		max.x = 1243 - 266;
		max.y = 768 - 266;
		max.width = 256;
		max.height = 256;
		
		matt = new Rectangle();
		matt.x = 10;
		matt.y = 768 - 266;
		matt.width = 256;
		matt.height = 256;
		
		ale = new Rectangle();
		ale.x = 10;
		ale.y = 10;
		ale.width = 256;
		ale.height = 256;
		
		christian = new Rectangle();
		christian.x = 1243 - 266;
		christian.y = 10;
		christian.width = 256;
		christian.height = 256;
	}
	
	// The main loop, fires @ 60 fps 
	// LibGDX combines the main and user input threads
	public void render() {
		// Clear the screen to Deep Blue
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Tell batch to use the same coordinates as the camera
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();	// Bunch draw calls after this
		batch.draw(imgMax, max.x, max.y);
		batch.draw(imgMatt, matt.x, matt.y);
		batch.draw(imgAle, ale.x, ale.y);
		batch.draw(imgChristian, christian.x, christian.y);
		batch.end();	// Bunch draw calls before this
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
