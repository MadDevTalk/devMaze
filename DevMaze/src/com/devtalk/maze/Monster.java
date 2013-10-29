package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Monster {
	
	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 4;
	
	Animation walkAnimation;
	Texture walkSheet = new Texture(Gdx.files.internal("dude_sheet.png"));
	TextureRegion[] walkFrames;
	
	Vector2 position;
	Vector2 velocity;
	Vector2 velocityLatch;
	Vector2 prevPosition;
	float prevAngle;
	
	float stateTime;
	
	private int currentHealth;
	private int totalHealth;
	
	public WalkState walkState;
	public List<Tile> path;
	public Tile destination;
	int count;
	
	public static enum MonsterType {
		EASY,
		MEDIUM,
		HARD,
	};
	
	public static enum WalkState {
		FOLLOWING_PLAYER,
		FINDING_DESTINATION,
		AT_DESTINATION,
	};
	
	public Monster(float xPos, float yPos, MonsterType type) {
		this.position = new Vector2(xPos, yPos);
		this.prevPosition = position.cpy();
		this.velocity = new Vector2();
		this.velocityLatch = new Vector2();
		this.walkState = WalkState.AT_DESTINATION;
		this.path = new ArrayList<Tile>();
		this.count = 0;
		
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
		
		switch (type) {
		case EASY:
			totalHealth = 5;
			break;
		case MEDIUM:
			totalHealth = 10;
			break;
		case HARD:
			totalHealth = 15;
			break;
		}
		
		// Start with full health
		currentHealth = totalHealth;
	}
	
	public void updatePos() {
		prevPosition = position.cpy();
		position.add(velocity);
	}
	
	public boolean isAlive()
	{
		return currentHealth == 0;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public int getTotalHealth()
	{
		return totalHealth;
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
				-velocity.x, velocity.y)));
	}
	
	public boolean isMoving()
	{
		return !(velocity.x == 0 && velocity.y == 0);
	}
	
	public boolean seesPlayer()
	{
		return false;
	}
	
	public String toString() {
		return ": " + position + " v: " + velocity + "\n" + 
				"lV: " + velocityLatch;
	}
	
}
