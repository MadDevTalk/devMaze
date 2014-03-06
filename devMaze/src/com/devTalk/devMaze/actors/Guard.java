package com.devTalk.devMaze.actors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.devTalk.devMaze.maze.*;

public class Guard implements Actor {

	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 1;
	
	Texture walkSheet = new Texture(Gdx.files.internal("guard_walking.png"));
	Texture exclamationMark = new Texture(Gdx.files.internal("exclamation_mark.png"));
	TextureRegion[] walkFrames;
	public Rectangle rectangle;
	
	private int count;
	private Tile destination;
	private List<Tile> path;
	private ActorState state;
	private boolean alive, alerted;
	private Vector2 position;
	private int velocityScale;
	private Vector2 velocity;
	private Vector2 velocityLatch;
	private Vector2 prevPosition;
	
	private OrthographicCamera camera;
	private Maze maze;
	private Player player;
	private SpriteBatch batch;
	private int prevAngle;

	Map<Integer, Integer> DIR_INDEX_MAP = new HashMap<Integer, Integer>();
	
	public Guard(DevMaze g, Tile location) {
		
		camera = g.camera;
		batch = g.batch;
		player = g.player;
		maze = g.maze;
		
		int xPos = (int) (location.center.x - (DevMaze.MONSTER_SIZE_PX / 2));
		int yPos = (int) (location.center.y - (DevMaze.MONSTER_SIZE_PX / 2));
		
		rectangle = new Rectangle(xPos, yPos, DevMaze.MONSTER_SIZE_PX, DevMaze.MONSTER_SIZE_PX);
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				walkFrames[index++] = tmp[i][j];
		
		state = ActorState.AT_DESTINATION;		
		position = new Vector2(xPos, yPos);
		prevPosition = position.cpy();
		velocity = new Vector2();
		velocityLatch = new Vector2();
		velocityScale = 2;
		
		DIR_INDEX_MAP.put(0, 1);
		DIR_INDEX_MAP.put(90, 2);			
		DIR_INDEX_MAP.put(-90, 3);
		DIR_INDEX_MAP.put(180, 0);
		DIR_INDEX_MAP.put(-180, 0);
		
	}
	
	public int getCount() {
		return count;
	}

	public int getVelocityScale() {
		return velocityScale + (alerted ? 2 : 0);
	}

	public void render() {
		Vector3 pos = new Vector3(getPosition().x, getPosition().y, 0);
		if (camera.frustum.sphereInFrustum(pos, getRectangle().getWidth())) {
			batch.begin();
			if (alerted)
				batch.draw(exclamationMark, 
						position.x + (DevMaze.MONSTER_SIZE_PX / 4), 
						position.y + DevMaze.MONSTER_SIZE_PX);
			
			batch.draw(texture(), position.x, position.y,
					(texture().getRegionWidth() / 2), (texture().getRegionHeight() / 2),
					texture().getRegionWidth(), texture().getRegionHeight(), 1, 1, 0);
			batch.end();
		}
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setPath(List<Tile> path) {
		this.path = path;
	}

	public void setDestination(Tile destination) {
		this.destination = destination;
	}

	public void setState(ActorState state) {
		this.state = state;
	}

	public void dispose() {
		walkSheet.dispose();
	}

	public void updatePos() {
		if (isMoving()) {
			prevPosition.set(position.cpy());
			position.add(velocity);
			rectangle.set(position.x, position.y, DevMaze.MONSTER_SIZE_PX, DevMaze.MONSTER_SIZE_PX);
			
			// TODO this can be more efficient
			Vector3 pos = new Vector3(getPosition().x, getPosition().y, 0);
			if (!isAlerted() && camera.frustum.sphereInFrustum(pos, getRectangle().getWidth())) {
				float xPos = getPosition().x;
				float yPos = getPosition().y;
				
				while (maze.tileAtLocation(xPos, yPos) != null && maze.tileAtLocation(xPos, yPos).inMaze) {
					if (player.rectangle.contains(xPos, yPos)) {
						alert();
						break;
					}

					xPos += (getVelocity().x * (DevMaze.MONSTER_SIZE_PX / 2));
					yPos += (getVelocity().y * (DevMaze.MONSTER_SIZE_PX / 2));
				}
			}
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isMoving() {
		return (velocity.x != 0 || velocity.y != 0);
	}
	
	public boolean isAlerted() {
		return alerted;
	}

	public boolean isHitBy(Rectangle hitArea) {
		return rectangle.overlaps(hitArea);
	}
	
	public Tile getDestination() {
		return destination;
	}

	public List<Tile> getPath() {
		return path;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getPrevPosition() {
		return prevPosition;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Vector2 getVelocityLatch() {
		return velocityLatch;
	}
	
	public Rectangle getRectangle() {
		return rectangle;
	}

	public ActorState getState() {
		return state;
	}

	public TextureRegion texture() {
		return walkFrames[DIR_INDEX_MAP.get(angle())];
	}
	
	private int angle() {
		if (!isMoving())
			return prevAngle;

		return prevAngle = (int) Math.toDegrees(Math.atan2(-velocity.x, velocity.y));
	}

	public void alert() {
		alerted = true;
	}
	
}
