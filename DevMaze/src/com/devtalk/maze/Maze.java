package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author max
 *
 */
public class Maze {

	private static final int DEFAULT_HEIGHT = 50;
	private static final int DEFAULT_WIDTH = 20;
	
	private boolean[][] edges;
	private List<Tile> tiles;
	
	public Maze() 
	{
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public Maze(int width, int height) 
	{
		tiles = new ArrayList<Tile>();
		edges = new boolean[width][height];
		
		this.generate();
	}
	
	
	/**
	 * Generates a maze within the boundaries using Randomized Prim's Algorithm defined:
	 * 	http://en.wikipedia.org/wiki/Prim's_algorithm
	 * 
	 * 
	 * unfinished
	 */
	private void generate()
	{
		
		Random gen = new Random();
		List<Tile[]> walls= new ArrayList<Tile[]>();
		
		// Start with a grid full of walls
		
		// Pick a (random) cell 
		Tile start = tiles.get(gen.nextInt(tiles.size() - 1));
		
		// Mark as part of the maze
		start.set_State(Tile.STATE.IN_MAZE);
		
		// Add the walls of the cell to the wall list
		List<Tile> neighbors = get_Neighbors(start);
		
		for (Tile neighbor : neighbors)
		{
			walls.add(new Tile[]{start, neighbor});
		}
		
		// While there are walls in the list:
		while (!walls.isEmpty())
		{
			// Pick a random wall from the list
			Tile[] wall = walls.get(gen.nextInt(walls.size() - 1));
			
			// If the cell on the opposite side isn't in the maze yet
			if (wall[1].get_State() == Tile.STATE.NOT_IN_MAZE)
			{
				// Mark the edge a passage
				edges[tiles.indexOf(wall[0])][tiles.indexOf(wall[1])] = true;
				
				// Add the walls of the cell to the wall list
				
			}
			else
			{
				// Remove wall from list
				walls.remove(wall);
			}
			
		}
	}
	
	private List<Tile> get_Neighbors(Tile ref)
	{
		List<Tile> temp = new ArrayList<Tile>();
		
		// Node to the right
		if (tiles.indexOf(ref) % edges.length != (edges.length - 1) )
			temp.add(tiles.get(tiles.indexOf(ref) + 1));
		
		// Node to the left
		if (tiles.indexOf(ref) % edges.length != 0)
			temp.add(tiles.get(tiles.indexOf(ref) - 1));
		
		// Node to the top
		if (tiles.indexOf(ref) + edges.length < tiles.size())
			temp.add(tiles.get(tiles.indexOf(ref) + edges.length));
		
		// Node to the bottom
		if (tiles.indexOf(ref) - edges.length >= 0)
			temp.add(tiles.get(tiles.indexOf(ref) - edges.length));
		
		return temp;
	}
}
