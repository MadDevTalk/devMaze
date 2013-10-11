package com.devtalk.maze;

import com.badlogic.gdx.graphics.Texture;

public class Tile {
	
	Texture image;
	private STATE state;
	private TYPE type;
	
	public static enum STATE 
	{
		IN_MAZE,
		NOT_IN_MAZE,
	}
	
	public enum TYPE {
		SINGLE_TOP,
		SINGLE_RIGHT,
		SINGLE_BOTTOM,
		SINGLE_LEFT,
		DOUBLE_TOP_RIGHT,
	}
	
	public Tile()
	{
		state = STATE.NOT_IN_MAZE;
	}
	
	public void set_State(STATE state) 
	{
		this.state = state;
	}
	
	public STATE get_State()
	{
		return this.state;
	}
	
	public TYPE get_Type()
	{
		return this.type;
	}
	
	public void set_Type(TYPE type)
	{
		this.type = type;
	}
	
	public Texture get_Img() {
		return this.image;
	}
}