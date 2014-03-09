package com.devTalk.devMaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.devTalk.devMaze.maze.DevMaze;

public class NewGameModule implements HUDModule {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private DevMaze game;
	
	Texture button = new Texture(Gdx.files.internal("SINGLE_PLAYER.png"));
	Texture button_onClick = new Texture(Gdx.files.internal("onclick_singleplayer.png"));
	
	Rectangle rectangle;
	boolean actioned;

	public NewGameModule(DevMaze g) {
		game = g;
		batch = g.batch;
		camera = g.camera;
		
		int x = (int) (camera.position.x - (button.getWidth() / 2));
		int y = (int) (camera.position.y - (button.getHeight() / 2));
		rectangle = new Rectangle(x, y + 80, 512, 64);
		
		actioned = false;
	}
	
	public boolean actionedAt(int x, int y) {
		x += camera.position.x - camera.viewportWidth / 2;
		y = (int) (camera.position.y + camera.viewportHeight / 2 - y);
		
		if (rectangle.contains(x, y))
			return actioned = true;
		
		return false;
	}

	public boolean draggedAt(int x, int y) {
		x += camera.position.x - camera.viewportWidth / 2;
		y = (int) (camera.position.y + camera.viewportHeight / 2 - y);
		
		if (actioned && !rectangle.contains(x, y))
			actioned = false;
		
		return false;
	}

	public void dispose() {
		button.dispose();
		button_onClick.dispose();
	}

	public void render() {
		batch.begin();
		
		if (actioned)
			batch.draw(button_onClick, rectangle.x, rectangle.y);
		else
			batch.draw(button, rectangle.x, rectangle.y);
		
		batch.end();
	}

	public void stopAction(int x, int y) {
		x += camera.position.x - camera.viewportWidth / 2;
		y = (int) (camera.position.y + camera.viewportHeight / 2 - y);
		
		if (actioned && rectangle.contains(x, y)) {
			actioned = false;
			game.newGame();
			game.setScreen(game.gameScreen);
		}
	}

}
