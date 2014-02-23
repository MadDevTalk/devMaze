package com.devTalk.devMaze.actors;

import java.util.List;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.devTalk.devMaze.maze.Tile;

public interface Monster {

	public static enum MonsterType {
		EASY, MEDIUM, HARD,
	};

	public static enum MonsterState {
		FINDING_DESTINATION,
		FOLLOWING_PLAYER,
		AT_DESTINATION, 
		IN_COMBAT,
		DYING,
	};

	public int getAttackFrequency();
	public int getCount();
	public int getCurrentHealth();
	public int getTotalHealth();
	public int getVelocityScale();
	public int getHitDamage();
	public void setCount(int count);
	public void setCurrentHealth(int health);
	public void setDestination(Tile destination);
	public void setPath(List<Tile> path);
	public void setState(MonsterState state);
	public void dispose();
	public void updatePos();
	public void attack();
	public void render();
	public float angle();
	public boolean attacking();
	public boolean isAlive();
	public boolean isMoving();
	public boolean sawPlayer();
	
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