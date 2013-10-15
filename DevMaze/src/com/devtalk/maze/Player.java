package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player extends GameFrame {
	
	private static Texture image = new Texture(Gdx.files.internal("char.png"));
	private static Rectangle rectangle;
	
	Vector3 velocity;
	Vector3 position;
	
	public Player(int xPos, int yPos) {
		position = new Vector3(xPos, yPos, 0);
		velocity = new Vector3();
		
		rectangle = new Rectangle(xPos, yPos, PLAYER_SIZE_PX, PLAYER_SIZE_PX);
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
		position.add(xOffset, yOffset, 0);
	}
	
	public Texture texture()
	{
		return image;
	}
	
}
