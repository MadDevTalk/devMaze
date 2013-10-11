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
	
	Array<Roommate> roommates;
	
	Texture imgDevTalk;
	Music musTunak;
	Rectangle devtalk;
	
	// Runs when the application is first instantiated
	public void create() {

		// Create Camera and SpriteBatch (pretty much always)
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		batch = new SpriteBatch();
		
		roommates = new Array<Roommate>();
	
		// Load assets
		imgDevTalk = new Texture(Gdx.files.internal("devtalk.png"));
		roommates.add(new Roommate("triple_left", 0, 0));
		roommates.add(new Roommate("double_top_bottom", 32, 0));
		roommates.add(new Roommate("double_top_bottom", 64, 0));
		roommates.add(new Roommate("double_bottom_right", 96, 0));
		roommates.add(new Roommate("double_top_right", 96, 32));
		roommates.add(new Roommate("double_top_bottom", 64, 32));
		musTunak = Gdx.audio.newMusic(Gdx.files.internal("tunak.mp3"));
		
		musTunak.setLooping(true);

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
