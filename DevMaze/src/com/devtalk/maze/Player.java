package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {
	
	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 4;
	private static final int INIT_HEALTH = 10;
	private static final int INIT_HIT_RAD = GameScreen.PLAYER_SIZE_PX / 2;
	private static final int INIT_HIT_DMG = 1;
	private static final int INIT_X = GameScreen.EDGE_SIZE_PX * (3/2);
	private static final int INIT_Y = GameScreen.EDGE_SIZE_PX * (3/2);
	
	private DevMaze game;
	private Maze maze;
	private SpriteBatch batch;
	private BitmapFont font;

	float stateTime;
	Animation walkAnimation;
	Texture walkSheet = new Texture(Gdx.files.internal("dude_sheet.png"));
	TextureRegion[] walkFrames;
	Rectangle rectangle;

	boolean walking;
	float prevAngle;
	Vector3 velocity;
	Vector3 position;
	Vector3 prevPosition;
	
	int currentHealth;
	int totalHealth;
	int hitRadius;
	int hitDamage;
	
	List<Item> pack;
	Item equippedItem;
	
	public Player(DevMaze g) {

		this.game = g;
		this.maze = g.maze;
		this.batch = g.batch;
		this.font = g.font;

		this.rectangle = new Rectangle(INIT_X, INIT_Y, GameScreen.PLAYER_SIZE_PX, GameScreen.PLAYER_SIZE_PX);
		this.walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
		
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
		
		this.equippedItem = null;
		this.pack = new ArrayList<Item>();
		
	}

	public void reset(int x, int y) {
		this.position.set(x, y, 0);
		
		this.totalHealth = INIT_HEALTH;
		this.currentHealth = totalHealth;
		this.hitRadius = INIT_HIT_RAD;
		this.hitDamage = INIT_HIT_DMG;
		
		this.equippedItem = null;
		this.pack = new ArrayList<Item>();
	}

	public void updatePos() {
		updatePos((int) this.velocity.x, (int) this.velocity.y);
	}

	public void updatePos(int xOffset, int yOffset) {

		if (xOffset != 0 || yOffset != 0)
		{
			this.walking = true;
			this.prevPosition.set(position.cpy());

			xOffset = Math.min(GameScreen.SPEED_LATCH_PX, xOffset);
			yOffset = Math.min(GameScreen.SPEED_LATCH_PX, yOffset);
		} else
			return;

		List<Tile> neighbors = maze.tileAtLocation(position.x, position.y).getNeighbors();
		
		// TODO: fine tune collision detection to allow player to get closer to walls.
		for (Tile neighbor : neighbors) {
			if (!neighbor.inMaze) {
				if (neighbor.rectangle.overlaps(
						new Rectangle(position.x + xOffset, position.y,
								GameScreen.PLAYER_SIZE_PX,
								GameScreen.PLAYER_SIZE_PX)))
					xOffset = 0;
	
				if (neighbor.rectangle.overlaps(
						new Rectangle(position.x, position.y + yOffset,
								GameScreen.PLAYER_SIZE_PX,
								GameScreen.PLAYER_SIZE_PX)))
					yOffset = 0;
			}
		}

		this.position.add(xOffset, yOffset, 0);
		this.rectangle.set(this.position.x, this.position.y, 
				GameScreen.PLAYER_SIZE_PX, GameScreen.PLAYER_SIZE_PX);
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

		if (isMoving())
			return this.walkAnimation.getKeyFrame(this.stateTime, true);
		else
			return this.walkFrames[4];
	}

	public float angle() {

		if (!isMoving())
			return this.prevAngle;

		return this.prevAngle = (float) (Math.toDegrees(Math.atan2(
				-(this.position.x - this.prevPosition.x), this.position.y - this.prevPosition.y)));
	}

	public boolean isMoving() {
		return this.walking || !this.velocity.isZero();
	}
	
	public boolean isAlive()
	{
		return this.currentHealth > 0;
	}
	
	public Rectangle getHitRectangle() {
		return new Rectangle(this.position.x - hitRadius,
				this.position.y - hitRadius,
				this.rectangle.width + (2 * hitRadius),
				this.rectangle.height + (2 * hitRadius));
	}
	
	public void detectHit(Monster monster) {
		if (monster.getHitRectangle().overlaps(this.rectangle)) {
			this.currentHealth -= monster.hitDamage;
			if (!this.isAlive()) {
				game.setScreen(game.mainMenuScreen);
			}
		}
	}

	public void render() {

		TextureRegion tmp = this.texture(Gdx.graphics.getDeltaTime());
		batch.draw(tmp, this.position.x, this.position.y,
				(tmp.getRegionWidth() / 2), (tmp.getRegionHeight() / 2),
				tmp.getRegionWidth(), tmp.getRegionHeight(), 1, 1,
				this.angle());
		font.draw(batch, "HP: " + this.currentHealth + "/" + this.totalHealth,
				this.position.x, this.position.y);
		
	}

	public void dispose() {
		this.walkSheet.dispose();
	}

}
