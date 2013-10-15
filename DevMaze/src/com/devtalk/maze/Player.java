package com.devtalk.maze;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player extends GameFrame {
	
	private static Texture image = new Texture(Gdx.files.internal("char.png"));
	
	Vector3 velocity;
	Vector3 position;
	List<Tile> walls;
	
	public Player(int xPos, int yPos, List<Tile> walls) {
		position = new Vector3(xPos, yPos, 0);
		velocity = new Vector3();
		this.walls = walls;
	}

	public void updatePos() {
		updatePos((int)velocity.x, (int)velocity.y);
	}
	
	public void updatePos(int xOffset, int yOffset)
	{
		//boolean collision = false;
		//for (Tile wall : walls)
		//	if (wall.rectangle().overlaps(new Rectangle(position.x + xOffset, position.y + yOffset, PLAYER_SIZE_PX, PLAYER_SIZE_PX)))
		//		collision = true;
		//
		//if (!collision)
			position.add(xOffset, yOffset, 0);
	}
	
	public void start(int xVel, int yVel) {
		velocity.add(xVel, yVel, 0);
	}
	
	public void stop(int xVel, int yVel) {
		velocity.sub(xVel, yVel, 0);
	}
	
	public void set(int x, int y) {
		position = new Vector3(x, y, 0);
	}
	
	public Texture texture()
	{
		return image;
	}
	
}
