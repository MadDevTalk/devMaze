package com.devtalk.maze;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface Monster {
	
	public static enum MonsterType {
		EASY,
		MEDIUM,
		HARD,
	};
	
	public static enum State {
		FOLLOWING_PLAYER,
		FINDING_DESTINATION,
		AT_DESTINATION,
		IN_COMBAT,
	};
	
	public State getState();
	public void setState(State state);
	public Vector2 getPosition();
	public Vector2 getPrevPosition();
	public Vector2 getVelocity();
	public Vector2 getVelocityLatch();
	public boolean sawPlayer();
	public Tile getDestination();
	public void setDestination(Tile destination);
	public int getAttackFrequency();
	public int getVelocityScale();
	public void setCount(int count);
	public int getCount();
	public List<Tile> getPath();
	public void setPath(List<Tile> path);
	public Rectangle getRectangle();
	public int getCurrentHealth();
	public void setCurrentHealth(int health);
	public int getTotalHealth();
	public int getHitDamage();
	public void updatePos();
	public boolean isAlive();
	public TextureRegion texture(float stateTime);
	public float angle();
	public boolean isMoving();
	public Rectangle getHitRectangle();
	public String toString();
	public void dispose();
	
}
