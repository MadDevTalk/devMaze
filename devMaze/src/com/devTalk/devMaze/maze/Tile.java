package com.devTalk.devMaze.maze;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	private Texture IN_MAZE = new Texture(Gdx.files.internal("IN_MAZE.png"));
	private Texture NOT_IN_MAZE = new Texture(Gdx.files.internal("NOT_IN_MAZE.png"));
	private Texture SHADOW = new Texture(Gdx.files.internal("SHADOW.png"));
	private Texture TREAD = new Texture(Gdx.files.internal("TREAD.png"));
	private Texture SHADOW_TREAD = new Texture(Gdx.files.internal("SHADOW_TREAD.png"));
	
	private Vector2 position;
	private List<Tile> neighbors;

	public boolean inMaze, inSwatch, isPortal, isShadow, tread;
	public Rectangle rectangle;
	public Vector2 center;

	public Tile(int row, int col) {
		inMaze = false;
		inSwatch = false;
		isPortal = false;
		isShadow = false;

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
		SHADOW.dispose();
		TREAD.dispose();
	}

	public List<Tile> getNeighbors() {
		return neighbors;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void inMaze(boolean bool) {
		inMaze = bool;
	}

	public void inSwatch(boolean bool) {
		inSwatch = bool;
	}

	public void isPortal(boolean bool) {
		isPortal = bool;
	}
	
	public void isShadow(boolean bool){
		isShadow = bool;
	}

	public Texture texture() {
		if (inMaze)
			if (tread)
				if(isShadow)
					return SHADOW_TREAD;
				else
					return TREAD;
			else if(isShadow)
				return SHADOW;
			else
				return IN_MAZE;
		else return NOT_IN_MAZE;
	}

	public String toString() {
		return "(" + (int) position.x + ", " + (int) position.y + ")";
	}
}