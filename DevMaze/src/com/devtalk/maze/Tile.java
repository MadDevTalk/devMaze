package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Tile {
	
	private boolean inMaze;
	private static Texture IN_MAZE = new Texture(Gdx.files.internal("IN_MAZE.png"));
	private static Texture NOT_IN_MAZE = new Texture(Gdx.files.internal("NOT_IN_MAZE.png"));
	
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
	
	public Texture texture()
	{
		if (inMaze)
			return IN_MAZE;
		else
			return NOT_IN_MAZE;
	}
}