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
	
	Texture button_landscape = new Texture(Gdx.files.internal("SINGLE_PLAYER.png"));
	Texture onClick_button_landscape = new Texture(Gdx.files.internal("onclick_singleplayer.png"));
	Texture button_portrait = new Texture(Gdx.files.internal("single_player_portrait.png"));
	Texture onClick_button_portrait = new Texture(Gdx.files.internal("single_player_portrait_onClick.png")); 
	
	Rectangle rectangle;
	boolean actioned;

	public NewGameModule(DevMaze g) {
		game = g;
		batch = g.batch;
		camera = g.camera;

		actioned = false;
		rectangle = new Rectangle();
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
		button_landscape.dispose();
		onClick_button_landscape.dispose();
		button_portrait.dispose();
		onClick_button_portrait.dispose();
	}

	public void render() {
		Texture texture = texture();
		rectangle.set(camera.position.x - (texture.getWidth() / 2), 
				      camera.position.y - (texture.getHeight() / 2), 
				      texture.getWidth(), 
				      texture.getHeight());
		
		batch.begin();
		batch.draw(texture(), rectangle.x, rectangle.y);
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
	
	private Texture texture() {
		if(camera.viewportHeight >= camera.viewportWidth)
			return actioned ? onClick_button_portrait : button_portrait;
		else
			return actioned ? onClick_button_landscape : button_landscape;
	}

}
