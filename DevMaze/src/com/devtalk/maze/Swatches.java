package com.devtalk.maze;

public class Swatches {

	public Tile[][] overlay;
	boolean inSwatch, isPortal;

	public Swatches(Maze maze) {
		overlay = new Tile[maze.tiles[0].length][maze.tiles.length];

		makeSwatch(5, 5);
	}

	public void makeSwatch(int topLeft, int topRight) {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {

			}
		}
	}
}
