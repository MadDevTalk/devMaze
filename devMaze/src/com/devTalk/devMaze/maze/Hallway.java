package com.devTalk.devMaze.maze;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Hallway {

	private static Random random = new Random();
	List<Tile> spaces;
	int on;
	
	public Hallway(){
		spaces = new LinkedList<Tile>();
		on = (int) random.nextInt(2);
	}
	
	public void addSpace(Tile space){
		spaces.add(space);
		
		if(on == 1)
			space.isShadow(true);
	}
	
	
}
