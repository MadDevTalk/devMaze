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
	
	Maze maze;
	
	float stateTime;
	
	private boolean alive;
	private int health;
	
	public static enum MonsterType {
		EASY,
		MEDIUM,
		HARD,
	};
	
	public Monster(Maze maze, MonsterType type) {
		this.maze = maze;
		this.alive = true;
		
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
	
	public boolean isMoving()
	{
		return false;
	}
}
