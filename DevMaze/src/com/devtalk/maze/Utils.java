package com.devtalk.maze;

public class Utils extends Maze {
	
	public static Tile tileAtLocation(float xPos, float yPos) {
		int row = row(yPos);
		int col = col(xPos);
		
		if (row < 0 || col < 0)
			return null;
					
		return Maze.tiles[row(yPos)][col(xPos)];
	}
	
	// May want to throw a new OutOfMaze exception or something
	public static int row(float yPos) {
		int calculated = (int) ((yPos + (GameScreen.PLAYER_SIZE_PX / 2)) / GameScreen.EDGE_SIZE_PX);
		
		if (calculated > tiles.length - 1 || calculated < 0) 
			calculated = -1;
		
		return calculated;
	}

	// May want to throw a new OutOfMaze exception or something
	public static int col(float xPos) {
		int calculated = (int) ((xPos + (GameScreen.PLAYER_SIZE_PX / 2)) / GameScreen.EDGE_SIZE_PX);
		
		if (calculated > tiles[0].length - 1 || calculated < 0) 
			calculated = -1; 
		
		return calculated;
	}

}
