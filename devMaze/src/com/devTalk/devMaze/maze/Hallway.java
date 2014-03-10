package com.devTalk.devMaze.maze;

import java.util.LinkedList;
import java.util.List;

public class Hallway {

	List<Tile> spaces;
	
	public Hallway(){
		spaces = new LinkedList<Tile>();
	}
	
	public void addSpace(Tile space){
		spaces.add(space);
		space.isShadow(true);
	}
	
	
}
