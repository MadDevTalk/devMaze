package com.devTalk.devMaze.maze;


public class PathNode {

	public PathNode parent;
	public Tile tile;
	public int G, H;

	public PathNode(Tile tile, PathNode parent, int G, int H) {
		this.tile = tile;
		this.parent = parent;
		this.G = G;
		this.H = H;
	}

	public boolean equals(Tile tile) {
		return tile.equals(this.tile);
	}

	public int F() {
		return G + H;
	}

}
