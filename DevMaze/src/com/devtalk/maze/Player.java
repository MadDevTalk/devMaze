package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {
	
	private static Texture image = new Texture(Gdx.files.internal("char.png"));
	
	Vector3 velocity;
	Vector3 position;
	
	Maze maze;
	
	public Player(int xPos, int yPos, Maze maze) {
		this.position = new Vector3(xPos, yPos, 0);
		this.velocity = new Vector3();
		this.maze = maze;
	}

	public void set(int x, int y) {
		position = new Vector3(x, y, 0);
	}
	
	public void updatePos() {
		updatePos((int)velocity.x, (int)velocity.y);
	}
	
	public void updatePos(int xOffset, int yOffset)
	{
		
		for (Tile wall : getNeighborWalls())
		{
			if (wall.rectangle().overlaps(new Rectangle(position.x + xOffset, position.y, GameScreen.PLAYER_SIZE_PX, GameScreen.PLAYER_SIZE_PX)))
				xOffset = 0;
			
			if (wall.rectangle().overlaps(new Rectangle(position.x, position.y + yOffset, GameScreen.PLAYER_SIZE_PX, GameScreen.PLAYER_SIZE_PX)))
				yOffset = 0;
		}
		
		position.add(xOffset, yOffset, 0);
	}
	
	public int row() {
		return (int) ((position.y + (GameScreen.PLAYER_SIZE_PX / 2)) / GameScreen.EDGE_SIZE_PX);
	}
	
	public int col() {
			return (int) ((position.x + (GameScreen.PLAYER_SIZE_PX / 2)) / GameScreen.EDGE_SIZE_PX);
	}
	
	public void start(int xVel, int yVel) {
		velocity.add(xVel, yVel, 0);
	}
	
	public void stop(int xVel, int yVel) {
		velocity.sub(xVel, yVel, 0);
	}
	
	public Tile tileLocation()
	{
		return maze.tiles[row()][col()];
	}
	
	public List<Tile> getNeighborWalls() {
		List<Tile> neighbors = new ArrayList<Tile>();
	
		int row = row();
		int col = col();
		
		// Check top
		if (maze.tiles.length - row > 1 && !maze.tiles[row + 1][col].inMaze())
			neighbors.add(maze.tiles[row + 1][col]);
			
		// Check right
		if (maze.tiles[0].length - col > 1 && !maze.tiles[row][col + 1].inMaze())
			neighbors.add(maze.tiles[row][col + 1]);
		
		// Check bottom
		if (row > 0 && !maze.tiles[row - 1][col].inMaze())
			neighbors.add(maze.tiles[row - 1][col]);
		
		// Check left
		if (col > 0 && !maze.tiles[row][col - 1].inMaze())
			neighbors.add(maze.tiles[row][col - 1]);
		
		// Check top right
		if (maze.tiles.length - row > 1 
				&& maze.tiles[0].length - col > 1 
				&& !maze.tiles[row + 1][col + 1].inMaze())
			neighbors.add(maze.tiles[row + 1][col + 1]);
		
		// Check bottom right
		if (row > 0 
				&& maze.tiles[0].length - col > 1 
				&& !maze.tiles[row - 1][col + 1].inMaze())
			neighbors.add(maze.tiles[row - 1][col + 1]);
		
		// Check bottom left
		if (row > 0 
				&& col > 0 
				&& !maze.tiles[row - 1][col - 1].inMaze())
			neighbors.add(maze.tiles[row - 1][col - 1]);
		
		// Check top left
		if (maze.tiles.length - row > 1
				&& col > 0 
				&& !maze.tiles[row + 1][col - 1].inMaze())
			neighbors.add(maze.tiles[row + 1][col - 1]);
		
		return neighbors;
	}
	
	public Texture texture()
	{
		return image;
	}
	
}
