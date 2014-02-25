package com.devTalk.devMaze.actors;

import java.util.List;

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
	TextureRegion[] walkFrames;
	public Rectangle rectangle;
	
	private int count;
	private Tile destination;
	private List<Tile> path;
	private ActorState state;
	private boolean alive, moving, alerted;
	private Vector2 position;
	private int velocityScale;
	private Vector2 velocity;
	private Vector2 velocityLatch;
	private Vector2 prevPosition;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Player player;
	private Maze maze;
	
	public Guard(DevMaze g, Tile location) {
		
		camera = g.camera;
		batch = g.batch;
		maze = g.maze;
		player = g.player;
		
		int centered_x = 1;
		int centered_y = 1;
		
		this.rectangle = new Rectangle(centered_x, centered_y, DevMaze.MONSTER_SIZE_PX, DevMaze.MONSTER_SIZE_PX);
		this.walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				this.walkFrames[index++] = tmp[i][j];
		

		int xPos = (int) location.center.x;
		int yPos = (int) location.center.y;
		
		state = ActorState.AT_DESTINATION;		
		position = new Vector2(xPos, yPos);
		prevPosition = position.cpy();
		velocity = new Vector2();
		velocityLatch = new Vector2();
	}

	public int dirIndex() {
		return 0;
	}
	
	public int getCount() {
		return count;
	}

	@Override
	public int getVelocityScale() {
		return velocityScale;
	}

	@Override
	public void render() {
		Vector3 pos = new Vector3(this.getPosition().x, this.getPosition().y, 0);
		if (camera.frustum.sphereInFrustum(pos, this.getRectangle().getWidth())) {
			batch.draw(texture(), position.x, position.y,
					(texture().getRegionWidth() / 2), (texture().getRegionHeight() / 2),
					texture().getRegionWidth(), texture().getRegionHeight(), 1, 1, 0);
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

	@Override
	public void updatePos() {
		prevPosition.set(position.cpy());
		position.add(velocity);
		rectangle.set(position.x, position.y,
				DevMaze.MONSTER_SIZE_PX, DevMaze.MONSTER_SIZE_PX);

		float xPos = position.x;
		float yPos = position.y;

		// TODO this can be more efficient by checking by tile
		if (!alerted)
			if (velocity.x != 0 || velocity.y != 0)
				while (maze.tileAtLocation(xPos, yPos) != null && maze.tileAtLocation(xPos, yPos).inMaze) {
					if (player.rectangle.contains(xPos, yPos)) {
						alerted = true;
						break;
					}

					xPos += (velocity.x * (DevMaze.MONSTER_SIZE_PX / 2));
					xPos += (velocity.y * (DevMaze.MONSTER_SIZE_PX / 2));
				}
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isMoving() {
		return moving;
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
		return walkFrames[dirIndex()];
	}
	
}
