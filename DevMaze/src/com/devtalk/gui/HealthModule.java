package com.devtalk.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devtalk.actors.Player;
import com.devtalk.maze.DevMaze;

public class HealthModule implements HUDModule {
	
	private DevMaze game;
	private Player player;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	//private List<Heart> bar;
	private List<Texture> bar;
	
	public HealthModule(DevMaze g) {
		this.game = g;
		this.player = g.player;
		this.batch = g.batch;
		this.camera = g.camera;
		
		bar = new ArrayList<Texture>();
		
		for (int i = 0; i < 10; i++) {
			bar.add(new Texture(Gdx.files.internal("Heart-Plain.png")));
		}
	}
	
	@Override
	public boolean actionedAt(int x, int y) {
		return false;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		for (int i = 0; i < (player.currentHealth / 2); i++) {
			float x = camera.position.x + (camera.viewportWidth / 2) - (bar.get(0).getWidth() * (i + 1));
			float y = camera.position.y;
		
			batch.draw(bar.get(0), x, y);
		}
	}

	@Override
	public void stopAction(int x, int y) {
		//none
	}

}

/*class Heart {
	private Texture heart = new Texture(Gdx.files.internal("Heart-Plain.png"));
	
	public void dispose() {
		heart.dispose();
	}
}*/
