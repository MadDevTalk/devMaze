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
		batch = new SpriteBatch();
		roommates = new Array<Roommate>();
	
		// Load assets
		imgDevTalk = new Texture(Gdx.files.internal("devtalk.png"));
		roommates.add(new Roommate("matt", 0, 0));
		roommates.add(new Roommate("max", 1243-256, 768-256));
		roommates.add(new Roommate("ale", 0, 768-256));
		roommates.add(new Roommate("christian", 1243-256, 0));
		musTunak = Gdx.audio.newMusic(Gdx.files.internal("tunak.mp3"));
		
		// Immediately set the camera and set the music
		camera.setToOrtho(false, 1243, 768);
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
		
		// Move the roommates
		for(Roommate roommate: roommates) 
		{

			int direction = MathUtils.random(0, 3);
	        roommate.move(direction);
	        
	        for(int i = 0; i < roommates.size; i++)
	        {
	        	Roommate other = roommates.peek();
	        	roommates.swap(i, roommates.size-1);
	        	if(roommate.boundary().overlaps(other.boundary()))
	        	{
	        		musTunak.play();
	        		if(direction < 2) {
	        			roommate.move(direction + 2);
	        		}
	        		else {
	        			roommate.move(direction - 2);
	        		}
	        	}
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
}
