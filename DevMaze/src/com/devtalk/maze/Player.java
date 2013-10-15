package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Player {
	
	private static Texture image = new Texture(Gdx.files.internal("char.png"));
	
	Vector3 velocity;
	Vector3 position;
	boolean dragged;
	
	public Player(int xPos, int yPos) {
		position = new Vector3(xPos, yPos, 0);
		velocity = new Vector3();
		dragged = false;
	}

	public void updatePos() {
		position.add(velocity);
	}
	
	public void start(int xVel, int yVel) {
		velocity.add(xVel, yVel, 0);
	}
	
	public void stop(int xVel, int yVel) {
		velocity.sub(xVel, yVel, 0);
	}
	
	public void translate(int xOffset, int yOffset)
	{
		velocity = new Vector3(xOffset, yOffset, 0);
		dragged = true;
	}
	
	public Texture texture()
	{
		return image;
	}
	
}
