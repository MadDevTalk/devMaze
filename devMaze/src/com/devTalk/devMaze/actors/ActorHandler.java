package com.devTalk.devMaze.actors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.devTalk.devMaze.actors.Actor.ActorState;
import com.devTalk.devMaze.actors.Actor.ActorType;
import com.devTalk.devMaze.maze.DevMaze;
import com.devTalk.devMaze.maze.Maze;
import com.devTalk.devMaze.maze.PathList;
import com.devTalk.devMaze.maze.PathNode;
import com.devTalk.devMaze.maze.Tile;

public class ActorHandler {

	private static final int G_WEIGHT_AXIAL = 10;

	private Maze maze;
	private Player player;
	private DevMaze game;

	public List<Actor> actors;

	public ActorHandler(DevMaze g) {
		this.game = g;
		this.maze = g.maze;
		this.player = g.player;
		this.actors = new ArrayList<Actor>();
	}

	public void detectHit(Rectangle hitArea) {
		List<Actor> deadMonsters = new ArrayList<Actor>();
		for (Actor monster : actors)
			if(monster.isHitBy(hitArea))
				if (!monster.isAlive())
					deadMonsters.add(monster);

		actors.removeAll(deadMonsters);

	}

	public void dispose() {
		for (Actor monster : actors)
			monster.dispose();
	}
	
	public void playerMoveAlert(Tile newTile) {
		for (Actor actor : actors) {
			if (actor.isAlerted())
			{
				List<Tile> tmp = actor.getPath();
				if(tmp.contains(newTile))
					tmp.remove(newTile);
				else
					tmp.add(newTile);
				
				actor.setDestination(newTile);
			}
		}
	}

	/**
	 * A* pathing implemented as per
	 * http://www.policyalmanac.org/games/aStarTutorial.htm
	 */
	private boolean findPath(Actor monster, Tile end) {
		Tile start = maze.tileAtLocation(monster.getPosition().x, monster.getPosition().y);
		PathList openList = new PathList();
		PathList closedList = new PathList();
		PathNode currentNode = new PathNode(start, null, 0, heuristic(start, end));

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
		int horizontal = Math.abs((int) (destination.getPosition().x - current.getPosition().x));
		int vertical = Math.abs((int) (destination.getPosition().y - current.getPosition().y));
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
		for (Actor monster : this.actors)
			monster.render();
	}

	private boolean seekDestination(Actor monster) {
		Tile currentPosition = maze.tileAtLocation(monster.getPosition().x, monster.getPosition().y);
		Tile lastPosition = maze.tileAtLocation(monster.getPrevPosition().x, monster.getPrevPosition().y);

		if (lastPosition != currentPosition)
			monster.setCount((DevMaze.EDGE_SIZE_PX / 2) / monster.getVelocityScale());

		if (monster.getPath().size() > 1) {
			monster.getPath().remove(currentPosition);
			Tile next = monster.getPath().get(0);

			float x = next.getPosition().x - currentPosition.getPosition().x;
			float y = next.getPosition().y - currentPosition.getPosition().y;

			if (monster.getCount() > 0) {
				monster.getVelocity().set(monster.getVelocityLatch());
				monster.setCount(monster.getCount() - 1);
			} else {
				monster.getVelocity().set(x * monster.getVelocityScale(), y * monster.getVelocityScale());
				monster.getVelocityLatch().set(monster.getVelocity().cpy());
			}
			
			// TODO this can be more efficient
			if (!monster.isAlerted()) {
				float xPos = monster.getPosition().x;
				float yPos = monster.getPosition().y;
				
				while (maze.tileAtLocation(xPos, yPos) != null && maze.tileAtLocation(xPos, yPos).inMaze) {
					if (player.rectangle.contains(xPos, yPos)) {
						monster.alert();
						setDestination(monster, player);
						break;
					}

					xPos += (monster.getVelocity().x * (DevMaze.MONSTER_SIZE_PX / 2));
					xPos += (monster.getVelocity().y * (DevMaze.MONSTER_SIZE_PX / 2));
				}
			}

			return true;
		} else {
			monster.getVelocity().set(new Vector2());
			monster.getPath().clear();
			return false;
		}
	}

	public void set(int monsterCount, ActorType difficulty) {
		actors.clear();
		Random r = new Random();
		for (int i = 0; i < monsterCount; i++) {
			Tile openTile = maze.openTiles.get(r.nextInt(maze.openTiles.size()));
			actors.add(new Guard(game, openTile));
		}
	}

	private boolean setDestination(Actor monster, Player player) {
		Tile end;

		if (player == null) {
			Random r = new Random();
			end = maze.openTiles.get(r.nextInt(maze.openTiles.size() - 1));
		} else {
			end = maze.tileAtLocation(player.position.x, player.position.y);
		}

		if (monster.getDestination() != end)
			return findPath(monster, end); // find path from monster position to end
		else
			return true;
	}

	public void updateMonsters() {
		for (Actor monster : actors) {
			switch (monster.getState()) {
			case FINDING_DESTINATION:
				if (!seekDestination(monster) && !monster.isAlerted()) 
					monster.setState(ActorState.AT_DESTINATION);
				break;
			case AT_DESTINATION:
				setDestination(monster, monster.isAlerted() ? player : null);
				monster.setState(ActorState.FINDING_DESTINATION);
				break;
			default:
				break;
			}

			monster.updatePos();
		}
	}

}
