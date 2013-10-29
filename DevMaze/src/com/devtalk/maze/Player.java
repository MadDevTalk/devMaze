package com.devtalk.maze;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {

	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 4;
	
	Animation walkAnimation;
	Texture walkSheet = new Texture(Gdx.files.internal("dude_sheet.png"));
	TextureRegion[] walkFrames;

	float stateTime;

	Vector3 velocity;
	Vector3 position;
	Vector3 prevPosition;
	float prevAngle;

	public boolean walking;

	Maze maze;

	public Player(int xPos, int yPos, Maze maze) {
		this.position = new Vector3(xPos, yPos, 0);
		this.prevPosition = new Vector3(position);
		this.velocity = new Vector3();
		this.maze = maze;

		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		walkAnimation = new Animation(0.025f, walkFrames);
		stateTime = 0.0f;

		walking = false;
	}

	public void set(int x, int y) {
		position = new Vector3(x, y, 0);
	}

	public void updatePos() {
		updatePos((int) velocity.x, (int) velocity.y);
	}

	public void updatePos(int xOffset, int yOffset) {

		if (xOffset != 0 || yOffset != 0)
		{
			walking = true;
			prevPosition = position.cpy();

			xOffset = Math.min(GameScreen.SPEED_LATCH_PX, xOffset);
			yOffset = Math.min(GameScreen.SPEED_LATCH_PX, yOffset);
		} else {
			return;
		}

		// DEBUG: A printout of the players neighbors
		List<Tile> neighbors = tileLocation().getNeighbors();
		if(!neighbors.equals(previous)) {
			for(Tile neighbor : neighbors) {
				System.out.print(neighbor + " ");
			}
			System.out.println();
			previous = neighbors;
		}
		
		// TODO: fine tune collision detection to allow player to get closer to walls.
		for (Tile neighbor : neighbors) {
			if (!neighbor.inMaze()) {
				if (neighbor.rectangle().overlaps(
						new Rectangle(position.x + xOffset, position.y,
								GameScreen.PLAYER_SIZE_PX,
								GameScreen.PLAYER_SIZE_PX)))
					xOffset = 0;
	
				if (neighbor.rectangle().overlaps(
						new Rectangle(position.x, position.y + yOffset,
								GameScreen.PLAYER_SIZE_PX,
								GameScreen.PLAYER_SIZE_PX)))
					yOffset = 0;
			}
		}

		position.add(xOffset, yOffset, 0);
	}

	public void start(int xVel, int yVel) {
		velocity.add(xVel, yVel, 0);
	}

	public void stop(int xVel, int yVel) {
		velocity.sub(xVel, yVel, 0);
		
		if (velocity.isZero())
			walking = false;
	}

	public TextureRegion texture(float stateTime) {
		this.stateTime += stateTime;

		if (isMoving())
			return walkAnimation.getKeyFrame(this.stateTime, true);
		else
			return walkFrames[4];
	}

	public float angle() {

		if (!isMoving())
			return prevAngle;

		return prevAngle = (float) (Math.toDegrees(Math.atan2(
				-(position.x - prevPosition.x), position.y - prevPosition.y)));
	}

	public boolean isMoving() {
		return walking || !velocity.isZero();
	}

}
