package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	private static Texture IN_MAZE = new Texture(
			Gdx.files.internal("IN_MAZE.png"));
	private static Texture NOT_IN_MAZE = new Texture(
			Gdx.files.internal("NOT_IN_MAZE.png"));
	private static Texture SWATCH = new Texture(
			Gdx.files.internal("SWATCH.png"));
	private static Texture PORTAL = new Texture(
			Gdx.files.internal("PORTAL.png"));
	private Vector2 position;
	private List<Tile> neighbors;

	public boolean inMaze, inSwatch, isPortal, tread;
	public Rectangle rectangle;
	public Vector2 center;

	public Tile(int row, int col) {
		this.inMaze = false;
		this.inSwatch = false;
		this.isPortal = false;

		neighbors = new ArrayList<Tile>();
		position = new Vector2(col, row);
		rectangle = new Rectangle(col * DevMaze.EDGE_SIZE_PX, row
				* DevMaze.EDGE_SIZE_PX, DevMaze.EDGE_SIZE_PX,
				DevMaze.EDGE_SIZE_PX);
		center = new Vector2(col * DevMaze.EDGE_SIZE_PX
				+ (DevMaze.EDGE_SIZE_PX / 2), row * DevMaze.EDGE_SIZE_PX
				+ (DevMaze.EDGE_SIZE_PX / 2));
	}

	public void addNeighbor(Tile neighbor) {
		this.neighbors.add(neighbor);
	}

	public void dispose() {
		IN_MAZE.dispose();
		NOT_IN_MAZE.dispose();
		SWATCH.dispose();
		PORTAL.dispose();
	}

	public List<Tile> getNeighbors() {
		return this.neighbors;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public void inMaze(boolean bool) {
		this.inMaze = bool;
	}

	public void inSwatch(boolean bool) {
		this.inSwatch = bool;
	}

	public void isPortal(boolean bool) {
		this.isPortal = bool;
	}

	public Texture texture() {
		if (this.inMaze)
			if (this.tread)
				return PORTAL;
			else
				return IN_MAZE;
		else return NOT_IN_MAZE;
	}

	public String toString() {
		return "(" + (int) this.position.x + ", " + (int) this.position.y + ")";
	}
}