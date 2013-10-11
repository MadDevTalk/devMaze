package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Roommate {
	
	Texture image;
	Rectangle boundary;
	int x, y;
	
	public Roommate(String name, int a, int b) 
	{
		name = name.toLowerCase();
		image = new Texture(Gdx.files.internal(name + ".png"));
		x = a; 
		y = b; 
		
		boundary = new Rectangle(x, y, 32, 32);
	}
	
	public int getx()
	{
		return x;
	}
	
	public int gety()
	{
		return y;
	}
	
	public Texture texture()
	{
		return image;
	}
	
	public Rectangle boundary()
	{
		return boundary;
	}

}
