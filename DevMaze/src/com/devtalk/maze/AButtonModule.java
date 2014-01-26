package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class AButtonModule implements HUDModule {

	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Player player;
	private MonsterHandler monsterHandler;

	private boolean pressed;

	private Texture button = new Texture(Gdx.files.internal("ABUTTON.png"));

	public AButtonModule(DevMaze g) {
		this.batch = g.batch;
		this.camera = g.camera;

		this.pressed = false;

		this.player = g.player;
		this.monsterHandler = g.monsterHandler;
	}

	@Override
	public boolean actionedAt(int x, int y) {
		if (this.rectangle().contains(x, y) && !pressed) {
			this.pressed = true;
			monsterHandler.detectHit(player.getHitRectangle());
			return true;
		}

		return false;
	}

	public void dispose() {
		button.dispose();
	}

	private Rectangle rectangle() {
		float x = camera.position.x + (camera.viewportWidth / 2)
				- (3 * button.getWidth() / 2);
		float y = camera.position.y - (camera.viewportHeight / 2)
				+ (3 * button.getHeight() / 2);
		return new Rectangle(x, y, button.getWidth(), button.getHeight());
	}

	@Override
	public void render() {
		float x = camera.position.x + (camera.viewportWidth / 2)
				- (3 * button.getWidth() / 2);
		float y = camera.position.y - (camera.viewportHeight / 2)
				+ (3 * button.getHeight() / 2);
		batch.draw(button, x, y, button.getWidth(), button.getHeight());
	}

	@Override
	public void stopAction(int x, int y) {
		pressed = false;
	}

}
