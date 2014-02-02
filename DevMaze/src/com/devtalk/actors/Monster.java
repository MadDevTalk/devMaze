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

	public static enum State {
		FOLLOWING_PLAYER,
		FINDING_DESTINATION,
		AT_DESTINATION, 
		IN_COMBAT,
		DYING,
	};

	public float angle();

	public void dispose();

	public int getAttackFrequency();

	public int getCount();

	public int getCurrentHealth();

	public Tile getDestination();

	public int getHitDamage();

	public Rectangle getHitRectangle();

	public List<Tile> getPath();

	public Vector2 getPosition();

	public Vector2 getPrevPosition();

	public Rectangle getRectangle();

	public State getState();

	public int getTotalHealth();

	public Vector2 getVelocity();

	public Vector2 getVelocityLatch();

	public int getVelocityScale();

	public boolean isAlive();

	public boolean isMoving();

	public boolean sawPlayer();

	public void setCount(int count);

	public void setCurrentHealth(int health);

	public void setDestination(Tile destination);

	public void setPath(List<Tile> path);

	public void setState(State state);

	public TextureRegion texture(float stateTime);

	public String toString();

	public void updatePos();

}
