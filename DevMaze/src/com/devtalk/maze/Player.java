package com.devtalk.maze;

import com.badlogic.gdx.math.Vector3;

public class Player {
	
	Vector3 velocity;
	Vector3 position;
	
	public Player(int xPos, int yPos) {
		position = new Vector3(xPos, yPos, 0);
		velocity = new Vector3();
	}

	public void updatePos() {
		position.add(velocity);
	}
	
	public void move(int xVel, int yVel) {
		velocity.add(xVel, yVel, 0);
	}
	
	public void stop(int xVel, int yVel) {
		velocity.sub(xVel, yVel, 0);
	}
	
}
