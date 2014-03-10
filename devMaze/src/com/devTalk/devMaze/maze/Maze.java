package com.devTalk.devMaze.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * @author max
 */
public class Maze {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	public Tile end;
	public List<Tile> openTiles;
	public List<Hallway> hallways;
	public Tile[][] tiles;
	// May want to throw a new OutOfMaze exception or something
	public int col(float xPos) {
		int calculated = (int) ((xPos + (DevMaze.PLAYER_SIZE_PX / 2)) / DevMaze.EDGE_SIZE_PX);
	
		if (calculated > tiles[0].length - 1 || calculated < 0)
			calculated = -1;
	
		return calculated;
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

	public void dispose() {
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles[0].length; j++)
				tiles[i][j].dispose();
	}

	public int distanceToEnd() {
		return -1;
	}

	public List<Wall> get_Neighbors(int row, int col) {
		List<Wall> temp = new ArrayList<Wall>();
	
		// Check top
		if (tiles.length - row > 3 && !tiles[row + 1][col].inMaze)
			temp.add(new Wall(row, col, row + 1, col));
	
		// Check right
		if (tiles[0].length - col > 3 && !tiles[row][col + 1].inMaze)
			temp.add(new Wall(row, col, row, col + 1));
	
		// Check bottom
		if (row > 2 && !tiles[row - 1][col].inMaze)
			temp.add(new Wall(row, col, row - 1, col));
	
		// Check left
		if (col > 2 && !tiles[row][col - 1].inMaze)
			temp.add(new Wall(row, col, row, col - 1));
	
		return temp;
	}

	public Maze(DevMaze g) {
		this.camera = g.camera;
		this.batch = g.batch;
		this.tiles = new Tile[0][0];
		this.openTiles = new ArrayList<Tile>();
	}

	public void render() {
		batch.begin();
		for (int i = 0; i < this.tiles.length; i++)
			for (int j = 0; j < this.tiles[0].length; j++) {
				float x = this.tiles[i][j].rectangle.x;
				float y = this.tiles[i][j].rectangle.y;
				Vector3 tile = new Vector3(x, y, 0);

				if (camera.frustum.sphereInFrustum(tile, DevMaze.EDGE_SIZE_PX))
					batch.draw(this.tiles[i][j].texture(), x, y);
			}
		batch.end();
	}

	// May want to throw a new OutOfMaze exception or something
	public int row(float yPos) {
		int calculated = (int) ((yPos + (DevMaze.PLAYER_SIZE_PX / 2)) / DevMaze.EDGE_SIZE_PX);

		if (calculated > tiles.length - 1 || calculated < 0)
			calculated = -1;

		return calculated;
	}

	public Tile tileAtLocation(float xPos, float yPos) {
		int row = row(yPos);
		int col = col(xPos);

		if (row < 0 || col < 0)
			return null;

		return tiles[row(yPos)][col(xPos)];
	}
	
	private void analyze() {
		Direction dir;
		
		for (int row = 0; row < tiles.length; row++)
			for (int col = 0; col < tiles[0].length; col++)
				if (tiles[row][col].inMaze) {
					openTiles.add(tiles[row][col]);
					setNeighbors(row, col);
					
					dir = isLeaf(row, col);
					if(dir != null){
						defineHallway(row, col, dir);
					}
				}
	}

	// Returns the direction out of the leaf, or null if this is not one
	private Direction isLeaf(int row, int col) {
		int count = 0;
		Direction dir = null;
		
		try{
			if(tiles[row + 1][col].inMaze){ 
				count += 1;
				dir = Direction.UP;
			}
		}
		catch(Exception e){};
		
		try{
			if(tiles[row][col + 1].inMaze){ 
				count += 1;
				dir = Direction.RIGHT;
			}
		}
		catch(Exception e){};
		
		try{
			if(tiles[row - 1][col].inMaze){ 
				count += 1;
				dir = Direction.DOWN;
			}
		}
		catch(Exception e){};
		
		try{
			if(tiles[row][col - 1].inMaze){ 
				count += 1;
				dir = Direction.LEFT;
			}
		}
		catch(Exception e){};
		
		// Exactly one inMaze neighbor
		if(count == 1)
			return dir;
		else
			return null; // Not a leaf
	}

	private void defineHallway(int row, int col, Direction dir) {
		Hallway hallway = new Hallway();
		Tile thisTile = tiles[row][col];
		boolean wall = false;
		int x = row; int y = col;
		
		while(!wall){
			hallway.addSpace(thisTile);
			
			switch(dir){
				case UP:
					thisTile = tiles[x + 1][y];
					x++;
					
					if(!thisTile.inMaze)
						wall = true;
					break;
				case DOWN:
					thisTile = tiles[x - 1][y];
					x--;
					
					if(!thisTile.inMaze)
						wall = true;
					break;
				case LEFT:
					thisTile = tiles[x][y - 1];
					y--;
					
					if(!thisTile.inMaze)
						wall = true;
					break;
				case RIGHT:
					thisTile = tiles[x][y + 1];
					y++;
					
					if(!thisTile.inMaze)
						wall = true;
					break;
			}
		}
		
	}

	/**
	 * Generates a maze within the boundaries using Randomized Prim's Algorithm
	 * defined: http://en.wikipedia.org/wiki/Prim's_algorithm
	 */
	private void generate() {
		Random gen = new Random();
		List<Wall> walls = new ArrayList<Wall>();
	
		// Start with a grid full of walls
		int row, col;
		row = col = 1;
		Tile start = tiles[row][col];
	
		// Mark as part of the maze
		start.inMaze(true);
	
		// Add the walls of the cell to the wall list
		walls.addAll(get_Neighbors(row, col));
	
		// While there are walls in the list:
		while (walls.size() > 1) {
	
			// Pick a random wall from the list
			Wall wall = walls.get(gen.nextInt(walls.size() - 1));
	
			// If the cell on the opposite side isn't in the maze yet
			if (!getOppositeTile(wall).inMaze) {
				// Mark the edge a passage
				tiles[wall.row][wall.col].inMaze(true);
	
				// Mark the cell on the opposite side a passage
				getOppositeTile(wall).inMaze(true);
	
				row = wall.row + wall.rowOffset;
				col = wall.col + wall.colOffset;
	
				// Add the walls of the cell to the wall list
				walls.addAll(get_Neighbors(row, col));
	
			} else
				// Remove wall from list
				walls.remove(wall);
	
		}
	
		// Mark end tile
		end = tiles[tiles.length - 2][tiles[0].length - 1];
		end.inMaze(true);
	
		analyze();
	}

	private Tile getOppositeTile(Wall wall) {
		return tiles[wall.row + wall.rowOffset][wall.col + wall.colOffset];
	}

	private void setNeighbors(int row, int col) {
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				try {
					if (i != 0 || j != 0)
						tiles[row][col].addNeighbor(tiles[row + i][col + j]);
				} catch (IndexOutOfBoundsException e) {};
	}
}
