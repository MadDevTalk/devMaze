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
		x = a; y = b; 
		
		boundary = new Rectangle();
		boundary.x = x;
		boundary.y = y;
		boundary.width = 256;
		boundary.height = 256;
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
	
	public void	move(int direction) 
	{
		switch(direction) 
		{
			case 0:
				y += 50;
				boundary.y += 50;
				break;
				
			case 1:
				x += 50;
				boundary.x += 50;
				break;	
				
			case 2:
				y -= 50;
				boundary.y -= 50;
				break;
				
			case 3:
				x -= 50;
				boundary.x -= 50;
				break;
		}
	}

}
