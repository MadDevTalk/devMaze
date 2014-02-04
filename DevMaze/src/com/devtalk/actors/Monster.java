package com.devtalk.actors;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.devtalk.maze.Tile;

public interface Monster {

	public static enum MonsterType {
		EASY, MEDIUM, HARD,
	};

	public static enum MonsterState {
		FOLLOWING_PLAYER,
		FINDING_DESTINATION,
		AT_DESTINATION, 
		IN_COMBAT,
		ATTACKING,
		DYING,
	};

	public int getAttackFrequency();
	public int getCount();
	public int getCurrentHealth();
	public int getTotalHealth();
	public int getVelocityScale();
	public int getHitDamage();
	public void attack();
	public void setCount(int count);
	public void setCurrentHealth(int health);
	public void setDestination(Tile destination);
	public void setPath(List<Tile> path);
	public void setState(MonsterState state);
	public void dispose();
	public void updatePos();
	public float angle();
	public boolean isAlive();
	public boolean isMoving();
	public boolean sawPlayer();
	public boolean isAttacking();
	
	public Tile getDestination();
	public Rectangle getHitRectangle();
	public List<Tile> getPath();
	public Vector2 getPosition();
	public Vector2 getPrevPosition();
	public Vector2 getVelocity();
	public Vector2 getVelocityLatch();
	
	public Rectangle getRectangle();
	public MonsterState getState();
	public TextureRegion texture(float stateTime);
	public String toString();

}
