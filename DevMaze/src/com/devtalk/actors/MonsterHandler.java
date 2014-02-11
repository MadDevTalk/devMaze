package com.devtalk.actors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.devtalk.actors.Monster.MonsterState;
import com.devtalk.actors.Monster.MonsterType;
import com.devtalk.maze.DevMaze;
import com.devtalk.maze.Maze;
import com.devtalk.maze.PathList;
import com.devtalk.maze.PathNode;
import com.devtalk.maze.Tile;

public class MonsterHandler {

	private static final int G_WEIGHT_AXIAL = 10;

	private Maze maze;
	private DevMaze game;
	private Player player;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	public List<Monster> monsters;

	public MonsterHandler(DevMaze g) {
		this.game = g;
		this.maze = g.maze;
		this.batch = g.batch;
		this.camera = g.camera;
		this.player = g.player;
		this.monsters = new ArrayList<Monster>();
	}

	public void detectHit(Rectangle hitArea) {
		List<Monster> deadMonsters = new ArrayList<Monster>();
		for (Monster monster : monsters)
			if (hitArea.overlaps(monster.getRectangle())) {
				monster.setCurrentHealth(monster.getCurrentHealth()
						- player.hitDamage);
				if (!monster.isAlive())
					deadMonsters.add(monster);
			}

		monsters.removeAll(deadMonsters);

	}

	public void dispose() {
		for (Monster monster : monsters)
			monster.dispose();
	}

	/**
	 * A* pathing implemented as per
	 * http://www.policyalmanac.org/games/aStarTutorial.htm
	 */
	private boolean findPath(Monster monster, Tile end) {
		Tile start = maze.tileAtLocation(monster.getPosition().x,
				monster.getPosition().y);
		PathList openList = new PathList();
		PathList closedList = new PathList();
		PathNode currentNode = new PathNode(start, null, 0, heuristic(start,
				end));

		// Add the starting node to the open list
		openList.add(currentNode);
		while (!openList.isEmpty()) {

			// Look for lowest cost node in the open list
			PathNode tmp = openList.get(0);
			for (PathNode node : openList)
				if (node.F() <= tmp.F())
					currentNode = node;

			// Switch it to the closed list
			closedList.add(openList.remove(openList.indexOf(currentNode)));

			// If the path has been found
			if (closedList.contains(end)) {
				monster.setPath(readPath(closedList.get(end)));
				monster.setDestination(end);
				return true;
			}

			// For each of the adjacent nodes...
			List<Tile> neighbors = currentNode.tile.getNeighbors();
			for (Tile neighbor : neighbors)
				// If the tile is in the maze and not already in the path
				if (neighbor.inMaze && !closedList.contains(neighbor)) {
					float horizontal = Math.abs(neighbor.getPosition().x
							- currentNode.tile.getPosition().x);
					float vertical = Math.abs(neighbor.getPosition().y
							- currentNode.tile.getPosition().y);

					if (horizontal + vertical == 1)
						if (openList.contains(neighbor)) {

							// Check to see if this path is better using G cost
							PathNode node = openList.get(neighbor);
							if (node.G <= currentNode.G) {
								node.parent = currentNode;
								node.G = currentNode.G + G_WEIGHT_AXIAL;
							}

						} else
							openList.add(new PathNode(neighbor, currentNode,
									currentNode.G + G_WEIGHT_AXIAL, heuristic(
											neighbor, end)));
				}
		}
		return false;
	}

	private int heuristic(Tile current, Tile destination) {
		int horizontal = Math.abs((int) (destination.getPosition().x - current
				.getPosition().x));
		int vertical = Math.abs((int) (destination.getPosition().y - current
				.getPosition().y));
		return (horizontal + vertical);// * G_WEIGHT_AXIAL;
	}

	private List<Tile> readPath(PathNode path) {
		List<Tile> tmp = new ArrayList<Tile>();

		// add ending tile
		tmp.add(path.tile);

		// Works backwards through list
		while (path.parent != null) {
			tmp.add(path.tile);
			path = path.parent;
		}

		// Return in correct order
		Collections.reverse(tmp);

		return tmp;
	}

	public void render() {
		for (Monster monster : this.monsters) {
			Vector3 pos = new Vector3(monster.getPosition().x, monster.getPosition().y, 0);
			if (camera.frustum.sphereInFrustum(pos, monster.getRectangle().getWidth())) {
				TextureRegion tmp = monster.texture(Gdx.graphics.getDeltaTime());
				batch.draw(tmp, monster.getPosition().x, monster.getPosition().y,
						(tmp.getRegionWidth() / 2), (tmp.getRegionHeight() / 2),
						tmp.getRegionWidth(), tmp.getRegionHeight(), 1, 1,
						monster.angle());
	
				if (DevMaze.DEBUG)
					game.font.draw(batch, "HP: " + monster.getCurrentHealth() + "/"
							+ monster.getTotalHealth(), monster.getPosition().x,
							monster.getPosition().y);
			}
		}
	}

	private boolean seekDestination(Monster monster) {
		Tile currentPosition = maze.tileAtLocation(monster.getPosition().x,
				monster.getPosition().y);
		Tile lastPosition = maze.tileAtLocation(monster.getPrevPosition().x,
				monster.getPrevPosition().y);

		if (lastPosition != currentPosition)
			monster.setCount((DevMaze.EDGE_SIZE_PX / 2)
					/ monster.getVelocityScale());

		if (monster.getPath().size() > 1) {
			monster.getPath().remove(currentPosition);
			Tile next = monster.getPath().get(0);

			float x = next.getPosition().x - currentPosition.getPosition().x;
			float y = next.getPosition().y - currentPosition.getPosition().y;

			if (monster.getCount() > 0) {
				monster.getVelocity().set(monster.getVelocityLatch());
				monster.setCount(monster.getCount() - 1);
			} else {
				monster.getVelocity().set(x * monster.getVelocityScale(),
						y * monster.getVelocityScale());
				monster.getVelocityLatch().set(monster.getVelocity().cpy());
			}

			return true;
		} else {
			monster.getVelocity().set(new Vector2());
			monster.getPath().clear();
			return false;
		}
	}

	public void set(int monsterCount, MonsterType difficulty) {
		monsters.clear();
		Random r = new Random();
		for (int i = 0; i < monsterCount; i++) {
			Tile openTile = maze.openTiles
					.get(r.nextInt(maze.openTiles.size()));
			if (i % 2 == 0)
				monsters.add(new Goblin(
						(float) ((openTile.getPosition().x * DevMaze.EDGE_SIZE_PX) + (DevMaze.EDGE_SIZE_PX / 4)),
						(float) ((openTile.getPosition().y * DevMaze.EDGE_SIZE_PX) + (DevMaze.EDGE_SIZE_PX / 4)),
						difficulty, game));
			else
				monsters.add(new Mech(
						(float) ((openTile.getPosition().x * DevMaze.EDGE_SIZE_PX) + (DevMaze.EDGE_SIZE_PX / 4)),
						(float) ((openTile.getPosition().y * DevMaze.EDGE_SIZE_PX) + (DevMaze.EDGE_SIZE_PX / 4)),
						difficulty, game));
		}
	}

	private boolean setDestination(Monster monster, Player player) {
		Tile end;

		if (player == null) {
			Random r = new Random();
			end = maze.openTiles.get(r.nextInt(maze.openTiles.size() - 1));
		} else if (monster.getDestination() == maze.tileAtLocation(
				player.position.x, player.position.y)) {
			return true;
		} else {
			end = maze.tileAtLocation(player.position.x, player.position.y);
		}

		// find path from monster position to end
		return findPath(monster, end);

	}

	public void updateMonsters() {
		for (Monster monster : monsters) {
			switch (monster.getState()) {
			case IN_COMBAT:
				// TODO:
				// Move towards player if needed - fan out (not all monsters
				// overlap)
				
				if (!monster.attack()) {
					Random r = new Random();
					int random = r.nextInt(monster.getAttackFrequency());
					if (random == 0)
						monster.attack();
				}
				break;
			case FINDING_DESTINATION:
				if (!seekDestination(monster)) monster.setState(MonsterState.AT_DESTINATION);
				break;
			case AT_DESTINATION:
				if (monster.sawPlayer()) monster.setState(MonsterState.IN_COMBAT);
				else {
					setDestination(monster, null);
					monster.setState(MonsterState.FINDING_DESTINATION);
				}
				break;
			default:
				// OH NOOOOOOOOOO
				break;
			}

			monster.updatePos();
		}
	}

}
