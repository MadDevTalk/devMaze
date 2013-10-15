package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Input;

/**
 * @author max
 *
 */
public class Maze {

	private static final int DEFAULT_HEIGHT = 50;
	private static final int DEFAULT_WIDTH = 20;
	
	public Tile[][] tiles;
	
	public Maze() 
	{
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public Maze(int width, int height) 
	{
		tiles = new Tile[width][height];
		
		for (int i = 0; i < tiles.length; i++)
		{
			for (int j = 0; j < tiles[0].length; j++)
			{
				tiles[i][j] = new Tile();
			}
		}
		
		this.generate();
	}
	
	
	/**
	 * Generates a maze within the boundaries using Randomized Prim's Algorithm defined:
	 * 	http://en.wikipedia.org/wiki/Prim's_algorithm
	 */
	private void generate()
	{
		Random gen = new Random();
		List<Wall> walls = new ArrayList<Wall>();
		
		// Start with a grid full of walls
		
		// Pick a (random) cell 
		int row = gen.nextInt(tiles.length - 1);
		int col = gen.nextInt(tiles[0].length - 1);
		
		Tile start = tiles[row][col];
		
		// Mark as part of the maze
		start.set_inMaze(true);
		
		// Add the walls of the cell to the wall list
		walls.addAll(get_Neighbors(row, col));
		
		// While there are walls in the list:
		while (walls.size() > 1)
		{
			
			// Pick a random wall from the list
			Wall wall = walls.get(gen.nextInt(walls.size() - 1));
			
			// If the cell on the opposite side isn't in the maze yet
			if (!getOppositeTile(wall).inMaze())
			{
				// Mark the edge a passage
				tiles[wall.gety2()][wall.getx2()].set_inMaze(true);
				
				// Mark the cell on the opposite side a passage
				getOppositeTile(wall).set_inMaze(true);
				
				// Add the walls of the cell to the wall list

				int xOffset = wall.getx2() - wall.getx1();
				int yOffset = wall.gety2() - wall.gety1();
				
				walls.addAll(get_Neighbors(wall.gety2() + yOffset, wall.getx2() + xOffset));
				
			}
			else
			{
				// Remove wall from list
				walls.remove(wall);
			}
			
		}
	}
	
	private List<Wall> get_Neighbors(int y, int x)
	{
		List<Wall> temp = new ArrayList<Wall>();
		
		// Check top
		if (tiles.length - y > 2 && !tiles[y + 1][x].inMaze())
			temp.add(new Wall(y, x, y + 1, x));
		
		// Check right
		if (tiles[0].length - x > 2 && !tiles[y][x + 1].inMaze())
			temp.add(new Wall(y, x, y, x + 1));
		
		// Check bottom
		if (y > 2 && !tiles[y - 1][x].inMaze())
			temp.add(new Wall(y, x, y - 1, x));
		
		// Check left
		if (x > 2 && !tiles[y][x - 1].inMaze())
			temp.add(new Wall(y, x, y, x - 1));
		
		return temp;
	}
	
	private Tile getOppositeTile(Wall wall)
	{
		int xOffset = wall.getx2() - wall.getx1();
		int yOffset = wall.gety2() - wall.gety1();
		
		return tiles[wall.gety2() + yOffset][wall.getx2() + xOffset];
	}
}
