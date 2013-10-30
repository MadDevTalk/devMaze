package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author max
 * 
 */
public class Maze {

	public Tile[][] tiles;
	public List<Tile> openTiles;

	public Maze() {
		this.tiles = new Tile[0][0];
		this.openTiles = new ArrayList<Tile>();
	}
	
	public void create(int row, int col) {
		tiles = new Tile[row][col];
		openTiles.clear();

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}

		this.generate();
	}

	/**
	 * Generates a maze within the boundaries using Randomized Prim's Algorithm
	 * defined: http://en.wikipedia.org/wiki/Prim's_algorithm
	 */
	private void generate() {
		Random gen = new Random();
		List<Wall> walls = new ArrayList<Wall>();

		// Start with a grid full of walls

		// There are certain places an initial tile could be such that the maze
		// could go the the edge of the 2d array. Therefor, I'm just starting 
		// at 1,1 for now which should work as long as the grid coords are 
		// prime (or just odd maybe). This makes choosing a maze "start" 
		// pretty easy, but is also kind of shit.
		//
		int row, col;
		// do {
		// Pick a (random, but not on edge) cell
		// row = gen.nextInt(tiles.length - 2) + 1;
		// col = gen.nextInt(tiles[0].length - 2) + 1;
		// } while ();

		row = col = 1;

		Tile start = tiles[row][col];

		// Mark as part of the maze
		start.set_inMaze(true);

		// Add the walls of the cell to the wall list
		walls.addAll(get_Neighbors(row, col));

		// While there are walls in the list:
		while (walls.size() > 1) {

			// Pick a random wall from the list
			Wall wall = walls.get(gen.nextInt(walls.size() - 1));

			// If the cell on the opposite side isn't in the maze yet
			if (!getOppositeTile(wall).inMaze()) {
				// Mark the edge a passage
				tiles[wall.row][wall.col].set_inMaze(true);

				// Mark the cell on the opposite side a passage
				getOppositeTile(wall).set_inMaze(true);

				row = wall.row + wall.rowOffset;
				col = wall.col + wall.colOffset;

				// Add the walls of the cell to the wall list
				walls.addAll(get_Neighbors(row, col));

			} else {
				// Remove wall from list
				walls.remove(wall);
			}

		}
		
		// Mark beginning and end tiles.
		//tiles[1][0].set_inMaze(true);
		//tiles[tiles.length - 2][tiles[0].length - 1].set_inMaze(true);
		
		analyze();
	}
	
	private void analyze() {
		for (int row = 0; row < tiles.length; row++)
			for (int col = 0; col < tiles[0].length; col++)
				if (tiles[row][col].inMaze()) {
					openTiles.add(tiles[row][col]);
					setNeighbors(row, col);
				}
	}
	
	private void setNeighbors(int row, int col) {
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				try {
					if (i != 0 || j != 0)
						tiles[row][col].addNeighbor(tiles[row + i][col + j]);
				} catch (IndexOutOfBoundsException e) {};
	}

	public List<Wall> get_Neighbors(int row, int col) {
		List<Wall> temp = new ArrayList<Wall>();

		// Check top
		if (tiles.length - row > 3 && !tiles[row + 1][col].inMaze())
			temp.add(new Wall(row, col, row + 1, col));

		// Check right
		if (tiles[0].length - col > 3 && !tiles[row][col + 1].inMaze())
			temp.add(new Wall(row, col, row, col + 1));

		// Check bottom
		if (row > 2 && !tiles[row - 1][col].inMaze())
			temp.add(new Wall(row, col, row - 1, col));

		// Check left
		if (col > 2 && !tiles[row][col - 1].inMaze())
			temp.add(new Wall(row, col, row, col - 1));

		return temp;
	}

	private Tile getOppositeTile(Wall wall) {
		return tiles[wall.row + wall.rowOffset][wall.col + wall.colOffset];
	}
	
	public Tile tileAtLocation(float xPos, float yPos) {
		int row = row(yPos);
		int col = col(xPos);
		
		if (row < 0 || col < 0)
			return null;
					
		return tiles[row(yPos)][col(xPos)];
	}
	
	// May want to throw a new OutOfMaze exception or something
	public int row(float yPos) {
		int calculated = (int) ((yPos + (GameScreen.PLAYER_SIZE_PX / 2)) / GameScreen.EDGE_SIZE_PX);
		
		if (calculated > tiles.length - 1 || calculated < 0) 
			calculated = -1;
		
		return calculated;
	}

	// May want to throw a new OutOfMaze exception or something
	public int col(float xPos) {
		int calculated = (int) ((xPos + (GameScreen.PLAYER_SIZE_PX / 2)) / GameScreen.EDGE_SIZE_PX);
		
		if (calculated > tiles[0].length - 1 || calculated < 0) 
			calculated = -1; 
		
		return calculated;
	}

	public void dispose() {
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles[0].length; j++)
				tiles[i][j].dispose();
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
