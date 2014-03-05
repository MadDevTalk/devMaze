package com.devTalk.devMaze.actors;

import java.util.List;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.devTalk.devMaze.maze.Tile;

public interface Actor {

	public static enum ActorType {
		EASY, MEDIUM, HARD,
	};

	public static enum ActorState {
		FOLLOWING_PLAYER,
		FINDING_DESTINATION,
		AT_DESTINATION,
	};

	public int           getCount();
	public int           getVelocityScale();
	public void			 alert();
	public void          render();
	public void		     setCount(int count);
	public void          setPath(List<Tile> path);
	public void          setDestination(Tile destination);
	public void          setState(ActorState state);
	public void          dispose();
	public void          updatePos();
	public boolean       isAlive();
	public boolean       isMoving();
	public boolean       isAlerted();
	public boolean       isHitBy(Rectangle hitArea);
	
	public Tile          getDestination();
	public List<Tile>    getPath();
	public Vector2       getPosition();
	public Vector2       getPrevPosition();
	public Vector2       getVelocity();
	public Vector2		 getVelocityLatch();
	
	public Rectangle     getRectangle();
	public ActorState    getState();
	public TextureRegion texture();
	public String        toString();

}