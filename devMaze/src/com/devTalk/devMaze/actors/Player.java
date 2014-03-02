package com.devTalk.devMaze.actors;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.devTalk.devMaze.gui.Pack;
import com.devTalk.devMaze.maze.*;

public class Player {

	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 1;
	private static final int INIT_HEALTH = 10;
	private static final int INIT_HIT_RAD = DevMaze.PLAYER_SIZE_PX / 2;
	private static final int INIT_HIT_DMG = 1;
	private static final int INIT_X = DevMaze.EDGE_SIZE_PX * (3 / 2);
	private static final int INIT_Y = DevMaze.EDGE_SIZE_PX * (3 / 2);

	private DevMaze game;
	private Maze maze;
	private SpriteBatch batch;
	
	Texture walkSheet = new Texture(Gdx.files.internal("player_walking.png"));
	TextureRegion[] walkFrames;
	public Rectangle rectangle;

	public boolean walking;
	public Vector3 velocity;
	public Vector3 position;
	public Vector3 prevPosition;

	public int currentHealth;
	public int totalHealth;
	public int hitRadius;
	public int hitDamage;
	public int prevAngle;

	public Pack pack;
	public Player(DevMaze g) {
		pack = new Pack(g);

		game = g;
		maze = g.maze;
		batch = g.batch;

		rectangle = new Rectangle(INIT_X, INIT_Y, DevMaze.PLAYER_SIZE_PX, DevMaze.PLAYER_SIZE_PX);
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, 
				walkSheet.getWidth()  / FRAME_COLS, 
				walkSheet.getHeight() / FRAME_ROWS);

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				walkFrames[index++] = tmp[i][j];

		walking = false;
		position = new Vector3(INIT_X, INIT_Y, 0);
		prevPosition = new Vector3(position);
		velocity = new Vector3();

		totalHealth = INIT_HEALTH;
		currentHealth = totalHealth;
		hitRadius = INIT_HIT_RAD;
		hitDamage = INIT_HIT_DMG;
	}

	public int directionIndex(double angle) {
		if (angle >= -45 && angle <= 45)
			return 1;
		else if (angle > 45 && angle <= 135)
			return 2;
		else if (angle >= -135 && angle < -45)
			return 3;
		else
			return 0;
	}
	
	public double angle() {
		if (!isMoving())
			return prevAngle;

		return prevAngle = (int) (Math.toDegrees(Math.atan2(
				-(position.x - prevPosition.x), position.y - prevPosition.y)));
	}

	public void detectHit(Actor monster) {
		// TODO:
	}

	public void dispose() {
		walkSheet.dispose();
	}

	public boolean isAlive() {
		return currentHealth > 0;
	}

	public boolean isMoving() {
		return walking;
	}

	public void render() {
		batch.draw(texture(), position.x, position.y);
	}

	public void reset(int x, int y, boolean resetHealth) {
		position.set(x, y, 0);
		totalHealth = INIT_HEALTH;
		hitRadius = INIT_HIT_RAD;
		hitDamage = INIT_HIT_DMG;

		if (resetHealth) {
			pack.clear();
			currentHealth = totalHealth;
		}
	}

	public void resetHealth() {
		currentHealth = INIT_HEALTH;
	}

	public void start(int xVel, int yVel) {
		velocity.add(xVel, yVel, 0);
	}

	public void stop(int xVel, int yVel) {
		velocity.sub(xVel, yVel, 0);

		if (this.velocity.isZero())
			walking = false;
	}

	public TextureRegion texture() {
		System.out.println(angle());
		return walkFrames[directionIndex(angle())];
	}

	public void updatePos() {
		updatePos((int) this.velocity.x, (int) this.velocity.y);
	}

	public void updatePos(int xOffset, int yOffset) {
		// Skip if we are not moving
		if (xOffset == 0 && yOffset == 0) {
			walking = false;
			return;
		}
		
		// Collision checking
		List<Tile> neighbors = maze.tileAtLocation(position.x, position.y).getNeighbors();

		for (Tile neighbor : neighbors) {
			if (!neighbor.inMaze) {
				if (neighbor.rectangle.overlaps(new Rectangle(position.x
						+ xOffset, position.y, DevMaze.PLAYER_SIZE_PX,
						DevMaze.PLAYER_SIZE_PX)))
					xOffset = 0;

				if (neighbor.rectangle.overlaps(new Rectangle(position.x,
						position.y + yOffset, DevMaze.PLAYER_SIZE_PX,
						DevMaze.PLAYER_SIZE_PX)))
					yOffset = 0;
			}
		}
		
		if (xOffset != 0 || yOffset != 0) {
			walking = true;
			prevPosition.set(position.cpy());
			position.add(xOffset, yOffset, 0);
		} else
			walking = false;
		
		Tile previousPosition = maze.tileAtLocation(prevPosition.x, prevPosition.y);
		Tile currentPosition = maze.tileAtLocation(position.x, position.y);
		
		if (previousPosition != currentPosition) {
			game.monsterHandler.playerMoveAlert(currentPosition);
		}
		
		rectangle.set(position.x, position.y, DevMaze.PLAYER_SIZE_PX, DevMaze.PLAYER_SIZE_PX);
		currentPosition.tread = true;
	}

}
