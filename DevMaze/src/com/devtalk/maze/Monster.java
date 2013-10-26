package com.devtalk.maze;

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
	Vector2 prevPosition;
	float prevAngle;
	
	float stateTime;
	
	private boolean alive;
	private int health;
	
	public WalkState walkState;
	
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
		this.alive = true;
		this.position = new Vector2(xPos, yPos);
		this.velocity = new Vector2(1, 1);
		this.walkState = WalkState.AT_DESTINATION;
		
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
			this.health = 5;
			break;
		case MEDIUM:
			this.health = 10;
			break;
		case HARD:
			this.health = 15;
			break;
		}
	}
	
	public void updatePos() {
		if (velocity.x != 0 || velocity.y != 0)
			prevPosition = position.cpy();
		else
			return;
		
		position.add(velocity);
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public int getHealth()
	{
		return health;
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
	
}
