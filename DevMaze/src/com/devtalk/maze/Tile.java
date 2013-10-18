package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Tile {

	private boolean inMaze;
	private static Texture IN_MAZE = new Texture(
			Gdx.files.internal("IN_MAZE.png"));
	private static Texture NOT_IN_MAZE = new Texture(
			Gdx.files.internal("NOT_IN_MAZE.png"));

	private Rectangle rectangle;

	public Tile(int row, int col) {
		this.inMaze = false;
		rectangle = new Rectangle(col * GameScreen.EDGE_SIZE_PX, row
				* GameScreen.EDGE_SIZE_PX, GameScreen.EDGE_SIZE_PX,
				GameScreen.EDGE_SIZE_PX);
	}

	public void set_inMaze(boolean inMaze) {
		this.inMaze = inMaze;
	}

	public boolean inMaze() {
		return this.inMaze;
	}

	public Texture texture() {
		if (inMaze)
			return IN_MAZE;
		else
			return NOT_IN_MAZE;
	}

	public Rectangle rectangle() {
		return rectangle;
	}
}