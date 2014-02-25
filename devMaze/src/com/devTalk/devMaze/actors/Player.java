package com.devTalk.devMaze.actors;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.devTalk.devMaze.gui.Pack;
import com.devTalk.devMaze.maze.*;

public class Player {

	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 4;
	private static final int INIT_HEALTH = 10;
	private static final int INIT_HIT_RAD = DevMaze.PLAYER_SIZE_PX / 2;
	private static final int INIT_HIT_DMG = 1;
	private static final int INIT_X = DevMaze.EDGE_SIZE_PX * (3 / 2);
	private static final int INIT_Y = DevMaze.EDGE_SIZE_PX * (3 / 2);

	private DevMaze game;
	private Maze maze;
	private SpriteBatch batch;

	float stateTime;
	Animation walkAnimation;
	Texture walkSheet = new Texture(Gdx.files.internal("dude_sheet.png"));
	TextureRegion[] walkFrames;
	public Rectangle rectangle;

	public boolean walking;
	public float prevAngle;
	public Vector3 velocity;
	public Vector3 position;
	public Vector3 prevPosition;

	public int currentHealth;
	public int totalHealth;
	public int hitRadius;
	public int hitDamage;

	public Pack pack;

	public boolean attack;

	public Player(DevMaze g) {
		this.attack = false;
		this.pack = new Pack(g);

		this.game = g;
		this.maze = g.maze;
		this.batch = g.batch;

		this.rectangle = new Rectangle(INIT_X, INIT_Y, DevMaze.PLAYER_SIZE_PX, DevMaze.PLAYER_SIZE_PX);
		this.walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, 
				walkSheet.getWidth()  / FRAME_COLS, 
				walkSheet.getHeight() / FRAME_ROWS);

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				this.walkFrames[index++] = tmp[i][j];

		this.stateTime = 0.0f;
		this.walkAnimation = new Animation(0.025f, walkFrames);

		this.walking = false;
		this.position = new Vector3(INIT_X, INIT_Y, 0);
		this.prevPosition = new Vector3(position);
		this.velocity = new Vector3();

		this.totalHealth = INIT_HEALTH;
		this.currentHealth = totalHealth;
		this.hitRadius = INIT_HIT_RAD;
		this.hitDamage = INIT_HIT_DMG;
	}

	public float angle() {
		if (!isMoving())
			return this.prevAngle;

		return this.prevAngle = (float) (Math.toDegrees(Math.atan2(
				-(this.position.x - this.prevPosition.x), this.position.y
						- this.prevPosition.y)));
	}

	public void detectHit(Actor monster) {
		// TODO:
	}

	public void dispose() {
		this.walkSheet.dispose();
	}

	public boolean isAlive() {
		return this.currentHealth > 0;
	}

	public boolean isMoving() {
		return this.walking;
	}

	public void render() {
		TextureRegion tmp = this.texture(Gdx.graphics.getDeltaTime());
		batch.draw(tmp, this.position.x, this.position.y,
				(tmp.getRegionWidth() / 2), (tmp.getRegionHeight() / 2),
				tmp.getRegionWidth(), tmp.getRegionHeight(), 1, 1, angle());
	}

	public void reset(int x, int y, boolean resetHealth) {
		this.position.set(x, y, 0);
		this.totalHealth = INIT_HEALTH;
		this.hitRadius = INIT_HIT_RAD;
		this.hitDamage = INIT_HIT_DMG;

		if (resetHealth) {
			this.pack.clear();
			this.currentHealth = totalHealth;
		}
	}

	public void resetHealth() {
		currentHealth = INIT_HEALTH;
	}

	public void start(int xVel, int yVel) {
		this.velocity.add(xVel, yVel, 0);
	}

	public void stop(int xVel, int yVel) {
		this.velocity.sub(xVel, yVel, 0);

		if (this.velocity.isZero())
			this.walking = false;
	}

	public TextureRegion texture(float stateTime) {
		this.stateTime += stateTime;

		if (isMoving() && !game.pause)
			return this.walkAnimation.getKeyFrame(this.stateTime, true);
		else
			return this.walkFrames[4];
	}

	public void updatePos() {
		updatePos((int) this.velocity.x, (int) this.velocity.y);
	}

	public void updatePos(int xOffset, int yOffset) {
		// Skip if we are not moving
		if (xOffset == 0 && yOffset == 0) {
			walking = false;
			return;
		}
		
		// Collision checking
		List<Tile> neighbors = maze.tileAtLocation(position.x, position.y).getNeighbors();

		for (Tile neighbor : neighbors) {
			if (!neighbor.inMaze) {
				if (neighbor.rectangle.overlaps(new Rectangle(position.x
						+ xOffset, position.y, DevMaze.PLAYER_SIZE_PX,
						DevMaze.PLAYER_SIZE_PX)))
					xOffset = 0;

				if (neighbor.rectangle.overlaps(new Rectangle(position.x,
						position.y + yOffset, DevMaze.PLAYER_SIZE_PX,
						DevMaze.PLAYER_SIZE_PX)))
					yOffset = 0;
			}
		}
		
		if (xOffset != 0 || yOffset != 0) {
			walking = true;
			prevPosition.set(position.cpy());
			position.add(xOffset, yOffset, 0);
		} else
			walking = false;
		
		rectangle.set(position.x, position.y, DevMaze.PLAYER_SIZE_PX, DevMaze.PLAYER_SIZE_PX);
		maze.tileAtLocation(position.x, position.y).tread = true;
	}

}
