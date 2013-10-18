package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Player {

	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 4;

	Animation walkAnimation;
	Texture walkSheet = new Texture(Gdx.files.internal("dude_sheet.png"));
	TextureRegion[] walkFrames;

	float stateTime;

	Vector3 velocity;
	Vector3 position;
	Vector3 prevPosition;
	float prevAngle;

	public boolean walking;

	Maze maze;

	public Player(int xPos, int yPos, Maze maze) {
		this.position = new Vector3(xPos, yPos, 0);
		this.prevPosition = new Vector3(position);
		this.velocity = new Vector3();
		this.maze = maze;

		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		walkAnimation = new Animation(0.025f, walkFrames);
		stateTime = 0.0f;

		walking = false;
	}

	public void set(int x, int y) {
		position = new Vector3(x, y, 0);
	}

	public void updatePos() {
		updatePos((int) velocity.x, (int) velocity.y);
	}

	public void updatePos(int xOffset, int yOffset) {

		for (Tile wall : getNeighborWalls()) {
			if (wall.rectangle().overlaps(
					new Rectangle(position.x + xOffset, position.y,
							GameScreen.PLAYER_SIZE_PX,
							GameScreen.PLAYER_SIZE_PX)))
				xOffset = 0;

			if (wall.rectangle().overlaps(
					new Rectangle(position.x, position.y + yOffset,
							GameScreen.PLAYER_SIZE_PX,
							GameScreen.PLAYER_SIZE_PX)))
				yOffset = 0;
		}

		if (xOffset != 0 || yOffset != 0)
			prevPosition = position.cpy();

		position.add(xOffset, yOffset, 0);
	}

	public int row() {
		return (int) ((position.y + (GameScreen.PLAYER_SIZE_PX / 2)) / GameScreen.EDGE_SIZE_PX);
	}

	public int col() {
		return (int) ((position.x + (GameScreen.PLAYER_SIZE_PX / 2)) / GameScreen.EDGE_SIZE_PX);
	}

	public void start(int xVel, int yVel) {
		velocity.add(xVel, yVel, 0);
	}

	public void stop(int xVel, int yVel) {
		velocity.sub(xVel, yVel, 0);
	}

	public Tile tileLocation() {
		return maze.tiles[row()][col()];
	}

	public List<Tile> getNeighborWalls() {
		List<Tile> neighbors = new ArrayList<Tile>();

		int row = row();
		int col = col();

		// Check top
		if (maze.tiles.length - row > 1 && !maze.tiles[row + 1][col].inMaze())
			neighbors.add(maze.tiles[row + 1][col]);

		// Check right
		if (maze.tiles[0].length - col > 1
				&& !maze.tiles[row][col + 1].inMaze())
			neighbors.add(maze.tiles[row][col + 1]);

		// Check bottom
		if (row > 0 && !maze.tiles[row - 1][col].inMaze())
			neighbors.add(maze.tiles[row - 1][col]);

		// Check left
		if (col > 0 && !maze.tiles[row][col - 1].inMaze())
			neighbors.add(maze.tiles[row][col - 1]);

		// Check top right
		if (maze.tiles.length - row > 1 && maze.tiles[0].length - col > 1
				&& !maze.tiles[row + 1][col + 1].inMaze())
			neighbors.add(maze.tiles[row + 1][col + 1]);

		// Check bottom right
		if (row > 0 && maze.tiles[0].length - col > 1
				&& !maze.tiles[row - 1][col + 1].inMaze())
			neighbors.add(maze.tiles[row - 1][col + 1]);

		// Check bottom left
		if (row > 0 && col > 0 && !maze.tiles[row - 1][col - 1].inMaze())
			neighbors.add(maze.tiles[row - 1][col - 1]);

		// Check top left
		if (maze.tiles.length - row > 1 && col > 0
				&& !maze.tiles[row + 1][col - 1].inMaze())
			neighbors.add(maze.tiles[row + 1][col - 1]);

		return neighbors;
	}

	public TextureRegion texture(float stateTime) {
		this.stateTime += stateTime;

		if (isMoving())
			return walkAnimation.getKeyFrame(this.stateTime, true);
		else
			return walkFrames[4];
	}

	public float angle() {

		if (!isMoving())
			return prevAngle;

		return prevAngle = (float) (Math.toDegrees(Math.atan2(
				-(position.x - prevPosition.x), position.y - prevPosition.y)));
	}

	public boolean isMoving() {
		return walking || !velocity.isZero();
	}

}
