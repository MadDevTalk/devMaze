package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pack {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;

	public int PACK_HEIGHT = 6;
	public int PACK_WIDTH = 9;

	@SuppressWarnings("unchecked")
	List<Item>[][] pack = new List[PACK_HEIGHT][PACK_WIDTH];

	public Pack(DevMaze g) {
		this.camera = g.camera;
		this.batch = g.batch;
		this.font = g.font;

		for (int i = 0; i < pack.length; i++)
			for (int j = 0; j < pack[0].length; j++)
				pack[i][j] = new ArrayList<Item>();

	}

	public boolean actionedAt(int x, int y) {
		for (int i = 0; i < pack.length; i++)
			for (int j = 0; j < pack[0].length; j++)
				if (!pack[i][j].isEmpty()
						&& pack[i][j].get(0).packRectangle().contains(x, y)) {
					pack[i][j].remove(0).action();
					return true;
				}

		return false;
	}

	public void add(Item item) {
		loop: for (int i = 0; i < pack.length; i++)
			for (int j = 0; j < pack[0].length; j++)
				if (pack[i][j].isEmpty() || pack[i][j].get(0).equals(item)) {
					pack[i][j].add(item);
					break loop;
				}
	}

	public void clear() {
		for (int i = 0; i < pack.length; i++)
			for (int j = 0; j < pack[0].length; j++)
				pack[i][j].clear();

	}

	public void render() {
		for (int i = 0; i < pack.length; i++)
			for (int j = 0; j < pack[0].length; j++)
				if (!pack[i][j].isEmpty()) {
					pack[i][j]
							.get(0)
							.packRectangle()
							.setX(camera.position.x - camera.viewportWidth / 2
									+ 74 * (j + 1));
					pack[i][j]
							.get(0)
							.packRectangle()
							.setY(camera.position.y + camera.viewportHeight / 2
									- (74 * (i + 2)));
					pack[i][j].get(0).render();
					if (DevMaze.DEBUG)
						font.draw(batch, Integer.toString(pack[i][j].size()),
								pack[i][j].get(0).packRectangle().x, pack[i][j]
										.get(0).packRectangle().y);
				}
	}

}
