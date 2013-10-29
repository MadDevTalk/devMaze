package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	private boolean inMaze;
	private static Texture IN_MAZE = new Texture(
			Gdx.files.internal("IN_MAZE.png"));
	private static Texture NOT_IN_MAZE = new Texture(
			Gdx.files.internal("NOT_IN_MAZE.png"));
	
	private Vector2 position;
	private Vector2 center;
	private List<Tile> neighbors;

	private Rectangle rectangle;

	public Tile(int row, int col) {
		this.inMaze = false;
		neighbors = new ArrayList<Tile>();
		position = new Vector2(col, row);
		rectangle = new Rectangle(col * GameScreen.EDGE_SIZE_PX, 
				row * GameScreen.EDGE_SIZE_PX, GameScreen.EDGE_SIZE_PX,
				GameScreen.EDGE_SIZE_PX);
		center = new Vector2(col * GameScreen.EDGE_SIZE_PX + (GameScreen.EDGE_SIZE_PX / 2), 
				row * GameScreen.EDGE_SIZE_PX + (GameScreen.EDGE_SIZE_PX / 2));
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
	
	public Vector2 getPosition() {
		return position;
	}
	
	public List<Tile> getNeighbors() {
		return neighbors;
	}
	
	public void addNeighbor(Tile neighbor) {
		neighbors.add(neighbor);
	}

	public Rectangle rectangle() {
		return rectangle;
	}

	public Vector2 getCenter() {
		return center;
	}
	
	public String toString() {
		return "(" + (int)position.x + ", "+ (int)position.y + ")";
	}
}