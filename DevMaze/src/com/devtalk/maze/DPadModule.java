package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DPadModule implements HUDModule {

	public static int DPAD_SIZE_PX = 192;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Player player;

	private Texture button = new Texture(Gdx.files.internal("DPAD.png"));

	public DPadModule(DevMaze g) {
		this.camera = g.camera;
		this.batch = g.batch;

		this.player = g.player;
	}

	@Override
	public boolean actionedAt(int x, int y) {
		Rectangle[][] rectangles = this.rectangles();

		for (int i = 0; i < rectangles.length; i++) {
			for (int j = 0; j < rectangles[0].length; j++) {
				if (rectangles[i][j].contains(x, y)) {
					player.velocity.set((j - 1) * DevMaze.KEY_VEL_PxPer60S,
							(i - 1) * DevMaze.KEY_VEL_PxPer60S, 0);
					return true;
				}
			}
		}

		return false;
	}

	public void dispose() {
		button.dispose();
	}

	private Rectangle[][] rectangles() {
		Rectangle[][] temp = new Rectangle[3][3]; // split into 9ths

		float x = camera.position.x - (camera.viewportWidth / 2) + 32;
		float y = camera.position.y - (camera.viewportHeight / 2) + 32;

		for (int i = 0; i < temp.length; i++)
			for (int j = 0; j < temp[0].length; j++)
				temp[i][j] = new Rectangle(x + (j * DPAD_SIZE_PX / 3), y
						+ (i * DPAD_SIZE_PX / 3), DPAD_SIZE_PX / 3,
						DPAD_SIZE_PX / 3);

		return temp;
	}

	@Override
	public void render() {
		float x = camera.position.x - (camera.viewportWidth / 2) + 32;
		float y = camera.position.y - (camera.viewportHeight / 2) + 32;
		batch.draw(button, x, y, button.getWidth(), button.getHeight());
	}

	@Override
	public void stopAction(int x, int y) {
		float xPos = camera.position.x - (camera.viewportWidth / 2) + 32;
		float yPos = camera.position.y - (camera.viewportHeight / 2) + 32;
		Rectangle rectangle = new Rectangle(xPos, yPos, button.getWidth(),
				button.getHeight());

		if (rectangle.contains(x, y)) {
			player.walking = false;
			player.velocity.set(0, 0, 0);
		}
	}

}
