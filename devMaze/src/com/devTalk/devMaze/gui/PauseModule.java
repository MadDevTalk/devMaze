package com.devTalk.devMaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.devTalk.devMaze.actors.Player;
import com.devTalk.devMaze.maze.DevMaze;

public class PauseModule implements HUDModule {

	private DevMaze game;
	private Player player;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Texture button = new Texture(Gdx.files.internal("pack.png"));
	private Texture menu = new Texture(Gdx.files.internal("pause_menu.png"));

	public PauseModule(DevMaze g) {
		this.game = g;
		this.batch = g.batch;
		this.player = g.player;
		this.camera = g.camera;
	}

	@Override
	public boolean actionedAt(int x, int y) {
		if (this.rectangle().contains(x, y)) {
			game.pause = !game.pause;
			return true;
		} else if (game.pause)
			return player.pack.actionedAt(x, y);

		return false;
	}

	public void dispose() {
		button.dispose();
		menu.dispose();
	}

	private Rectangle rectangle() {
		return new Rectangle(camera.position.x - camera.viewportWidth / 2,
				(camera.position.y + camera.viewportHeight / 2)
						- button.getHeight(), button.getWidth(),
				button.getHeight());
	}

	@Override
	public void render() {
		batch.begin();
		if (game.pause) {
			batch.draw(
					menu,
					camera.position.x - camera.viewportWidth / 2,
					(camera.position.y + camera.viewportHeight / 2)
							- menu.getHeight());

			player.pack.render();
		}

		batch.draw(
				button,
				camera.position.x - camera.viewportWidth / 2,
				(camera.position.y + camera.viewportHeight / 2)
						- button.getHeight());
		
		batch.end();
	}

	@Override
	public void stopAction(int x, int y) {
		if (game.pause)
			;// itemHandler.stopAction(x, y);
	}

	@Override
	public boolean draggedAt(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
