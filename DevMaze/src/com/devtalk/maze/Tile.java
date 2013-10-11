package com.devtalk.maze;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
	
	Texture image;
	private boolean inMaze;
	
	public Tile()
	{
		this.inMaze = false;
	}
	
	public void set_inMaze(boolean inMaze)
	{
		this.inMaze = inMaze;
	}
	
	public boolean inMaze()
	{
		return this.inMaze;
	}
}