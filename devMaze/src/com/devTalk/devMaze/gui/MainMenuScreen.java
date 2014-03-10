package com.devTalk.devMaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devTalk.devMaze.maze.DevMaze;

public class MainMenuScreen implements Screen{
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	Texture bgimage = new Texture(Gdx.files.internal("bgimage.png"));
	
	HUD hud;
	InputProcessor inputProcessor;

	public MainMenuScreen(DevMaze g) {
		// Reference game objects
		camera = g.camera;
		batch = g.batch;

		// Create HUD and add modules
		hud = new HUD();
		hud.addModule(new NewGameModule(g));
		
		// Set our input processor
		inputProcessor = new HUDInputProcessor(hud);
	}

	public void dispose() {
		bgimage.dispose();
	}

	@Override
	public void hide() {
	// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
	// TODO Auto-generated method stub
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1); // R,G,B,A (0.0f - 1.0f)
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		batch.setProjectionMatrix(camera.combined);
		
		// **DRAW BACKGROUND** //
		batch.begin();
		batch.draw(bgimage, 0, 0, camera.viewportWidth, camera.viewportHeight);
		batch.end();

		// **DRAW BUTTONS** //
		hud.render();
	}

	public void resize(int width, int height) {
		camera.setToOrtho(false, width, height);
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	public void show() {
		Gdx.input.setInputProcessor(inputProcessor);
	}
	
}