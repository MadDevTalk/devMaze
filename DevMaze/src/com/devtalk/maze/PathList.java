package com.devtalk.maze;
import java.util.ArrayList;

public class PathList extends ArrayList<PathNode> {

	private static final long serialVersionUID = 1L;

	public boolean contains(Tile tile) {
		for (PathNode node : this) {
			if (node.equals(tile))
				return true;
		}
		
		return false;
	}
	
	public PathNode get(Tile tile) {
		for (PathNode node : this) {
			if (node.equals(tile))
				return node;
		}
		
		return null;
	}
}
