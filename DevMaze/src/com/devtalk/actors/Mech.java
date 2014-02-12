package com.devtalk.actors;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.devtalk.maze.DevMaze;
import com.devtalk.maze.Maze;
import com.devtalk.maze.Tile;

public class Mech implements Monster {

	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 2;

	private DevMaze game;
	private Player player;
	private Maze maze;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	float stateTime;
	float attackTime;
	Texture walkSheet = new Texture(Gdx.files.internal("mech_walking.png"));
	Texture attackSheet = new Texture(Gdx.files.internal("mech_attacking.png"));
	TextureRegion[] walkFrames = new TextureRegion[6];
	TextureRegion[] attackFrames = new TextureRegion[6];
	Animation walkAnimation = new Animation(0.025f, walkFrames);
	Animation attackAnimation = new Animation(0.025f, attackFrames);
	Animation dyingAnimation = new Animation(0.025f, walkFrames);
	Rectangle rectangle;

	float prevAngle;
	int velocityScale;
	Vector2 position;
	Vector2 velocity;
	Vector2 velocityLatch;
	Vector2 prevPosition;

	int currentHealth;
	int totalHealth;
	int hitRadius;
	int hitDamage;
	int attackFrequency;
	int attackMotionLength;
	boolean sawPlayer;

	MonsterState state;
	List<Tile> path;
	Tile destination;
	int count;

	public Mech(float xPos, float yPos, MonsterType type, DevMaze g) {
		this.game = g;
		this.player = g.player;
		this.maze = g.maze;
		this.camera = g.camera;
		this.batch = g.batch;

		this.state = MonsterState.AT_DESTINATION;
		this.path = new ArrayList<Tile>();
		this.count = 0;

		this.rectangle = new Rectangle(xPos, yPos, DevMaze.MONSTER_SIZE_PX,
				DevMaze.MONSTER_SIZE_PX);

		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		
		TextureRegion[][] temp = TextureRegion.split(attackSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
				/ FRAME_ROWS);

		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++)
			for (int j = 0; j < FRAME_COLS; j++)
				if (index < walkFrames.length) {
					this.attackFrames[index] = temp[i][j];
					this.walkFrames[index++] = tmp[i][j];
				}

		this.stateTime = 0.0f;
		this.position = new Vector2(xPos, yPos);
		this.prevPosition = position.cpy();
		this.velocity = new Vector2();
		this.velocityLatch = new Vector2();

		switch (type) {
		case EASY:
			this.hitRadius = DevMaze.MONSTER_SIZE_PX / 8;
			this.totalHealth = 10;
			this.hitDamage = 2;
			this.attackFrequency = 100;
			this.velocityScale = 1;
			break;
		case MEDIUM:
			this.hitRadius = DevMaze.MONSTER_SIZE_PX / 6;
			this.totalHealth = 15;
			this.hitDamage = 4;
			this.attackFrequency = 55;
			this.velocityScale = 2;
			break;
		case HARD:
			this.hitRadius = DevMaze.MONSTER_SIZE_PX / 4;
			this.totalHealth = 20;
			this.hitDamage = 5;
			this.attackFrequency = 45;
			this.velocityScale = 3;
			break;
		}

		// Start with full health
		this.currentHealth = this.totalHealth;
	}

	public float angle() {
		if (!isMoving())
			return prevAngle;

		return prevAngle = (float) (Math.toDegrees(Math.atan2(-velocity.x,
				velocity.y)));
	}

	public void dispose() {
		walkSheet.dispose();
	}

	public int getAttackFrequency() {
		return attackFrequency;
	}

	public int getCount() {
		return count;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public Tile getDestination() {
		return destination;
	}

	public int getHitDamage() {
		return hitDamage;
	}

	public Rectangle getHitRectangle() {
		return new Rectangle(this.position.x - hitRadius, this.position.y
				- hitRadius, this.rectangle.width + (2 * hitRadius),
				this.rectangle.height + (2 * hitRadius));
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

	public Rectangle getRectangle() {
		return rectangle;
	}

	public MonsterState getState() {
		return state;
	}

	public int getTotalHealth() {
		return totalHealth;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Vector2 getVelocityLatch() {
		return velocityLatch;
	}

	public int getVelocityScale() {
		return velocityScale;
	}

	public boolean isAlive() {
		return this.currentHealth > 0;
	}

	public boolean isMoving() {
		return !(this.velocity.x == 0 && this.velocity.y == 0);
	}

	public boolean sawPlayer() {
		return sawPlayer;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setCurrentHealth(int health) {
		this.currentHealth = health;
	}

	public void setDestination(Tile destination) {
		this.destination = destination;
	}

	public void setPath(List<Tile> path) {
		this.path = path;
	}

	public void setState(MonsterState state) {
		this.state = state;
	}
	
	public void render() {
		this.attackMotionLength --;
		
		Vector3 pos = new Vector3(this.getPosition().x, this.getPosition().y, 0);
		if (camera.frustum.sphereInFrustum(pos, this.getRectangle().getWidth())) {
			TextureRegion tmp = this.texture(Gdx.graphics.getDeltaTime());
			batch.draw(tmp, this.getPosition().x, this.getPosition().y,
					(tmp.getRegionWidth() / 2), (tmp.getRegionHeight() / 2),
					tmp.getRegionWidth(), tmp.getRegionHeight(), 1, 1,
					this.angle());

			if (DevMaze.DEBUG)
				game.font.draw(batch, "HP: " + this.getCurrentHealth() + "/"
						+ this.getTotalHealth(), this.getPosition().x,
						this.getPosition().y);
		}
	}

	public TextureRegion texture(float stateTime) {
		this.stateTime += stateTime;

		if (!game.pause) {
			switch (state) {
			case FINDING_DESTINATION:
			case FOLLOWING_PLAYER:
				return this.walkAnimation.getKeyFrame(this.stateTime, true);
			case AT_DESTINATION:
				return this.walkFrames[0];
			case IN_COMBAT:
				return this.attackAnimation.getKeyFrame(this.stateTime, true);
			case DYING:
				//return this.dyingAnimation.getKeyFrame(this.stateTime, false);
				break;
			}
		}
		
		return this.walkFrames[0];
	}

	public String toString() {
		return ": " + this.position + " v: " + this.velocity + "\n" + "lV: "
				+ this.velocityLatch;
	}

	public void updatePos() {
		this.prevPosition = this.position.cpy();
		this.position.add(this.velocity);
		this.rectangle.set(this.position.x, this.position.y,
				DevMaze.MONSTER_SIZE_PX, DevMaze.MONSTER_SIZE_PX);

		float xPos = this.position.x;
		float yPos = this.position.y;

		// TODO this can be more efficient - sucks currently
		if (!this.sawPlayer)
			if (this.velocity.x != 0 || this.velocity.y != 0)
				while (maze.tileAtLocation(xPos, yPos) != null
						&& maze.tileAtLocation(xPos, yPos).inMaze) {
					if (player.rectangle.contains(xPos, yPos)) {
						this.sawPlayer = true;
						break;
					}

					xPos += (velocity.x * (DevMaze.MONSTER_SIZE_PX / 2));
					xPos += (velocity.y * (DevMaze.MONSTER_SIZE_PX / 2));
				}
	}

	public void attack() {
		attackMotionLength = 5;
	}
	
	public boolean attacking() {
		return (attackMotionLength > 0);
	}

}
