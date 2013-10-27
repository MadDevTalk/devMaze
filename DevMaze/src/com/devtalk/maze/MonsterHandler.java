package com.devtalk.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import com.devtalk.maze.Monster.MonsterType;
import com.devtalk.maze.Monster.WalkState;

public class MonsterHandler {
	
	private static final int G_WEIGHT_AXIAL = 10;
	
	public List<Monster> monsters;
	
	public MonsterHandler(int monsterCount, MonsterType difficulty) {
		monsters = new ArrayList<Monster>();
		
		Random r = new Random();
		
		for (int i = 0; i < monsterCount; i++)
		{
			Tile openTile = Maze.openTiles.get(r.nextInt(Maze.openTiles.size()));
			monsters.add(new Monster((float) ((openTile.getPosition().x * GameScreen.EDGE_SIZE_PX) + (GameScreen.EDGE_SIZE_PX / 4)),
					(float) ((openTile.getPosition().y * GameScreen.EDGE_SIZE_PX) + (GameScreen.EDGE_SIZE_PX / 4)),
					difficulty));
		}
	}

	public void updateMonsters() {
		for (Monster monster : monsters) 
		{
			switch (monster.walkState) {
			case FOLLOWING_PLAYER:
				followPlayer(monster);
				break;
			case FINDING_DESTINATION:
				seekDestination(monster);
				break;
			case AT_DESTINATION:
				setDestination(monster);
				break;
			default:
				// OH NOOOOOOOOOO
				break;
			}
			
			monster.updatePos();
		}
	}
	
	private void followPlayer(Monster monster) {
		
	}
	
	private void setDestination(Monster monster) {
		
		Random r = new Random();
		Tile end = Maze.openTiles.get(r.nextInt(Maze.openTiles.size() - 1));
		
		// find path from monster position to end
		if (findPath(monster, Utils.tileAtLocation(monster.position.x, monster.position.y), end))
			monster.walkState = WalkState.FINDING_DESTINATION;
		
	}
	
	private void seekDestination(Monster monster) {
		Tile currentPosition = Utils.tileAtLocation(monster.position.x, monster.position.y);
		
		if (currentPosition == monster.destination) {
			monster.walkState = WalkState.AT_DESTINATION;
			monster.path.clear();
			return;
		}
		
		monster.path.remove(currentPosition);
		Tile next = monster.path.get(0);
		
		float x = next.getPosition().x - currentPosition.getPosition().x;
		float y = next.getPosition().y - currentPosition.getPosition().y;
		
		monster.velocity.set(x, y);
	}
	
	/**
	 * A* pathing implemented as per http://www.policyalmanac.org/games/aStarTutorial.htm
	 */
	private boolean findPath(Monster monster, Tile start, Tile end) {
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
				monster.path = readPath(closedList.get(end));
				monster.destination = end;
				return true;
			}
			
			// For each of the adjacent nodes...
			List<Tile> neighbors = currentNode.tile.getNeighbors();
			for (Tile neighbor : neighbors)
				// If the tile is in the maze and not already in the path
				if (neighbor.inMaze() && !closedList.contains(neighbor)) {
					if (openList.contains(neighbor)) {
						
						// Check to see if this path is better using G cost
						PathNode node = openList.get(neighbor);
						if (node.G <= currentNode.G) {
							node.parent = currentNode;
							node.G = currentNode.G + G_WEIGHT_AXIAL;
						}
						
					} else
						openList.add(new PathNode(neighbor, currentNode, 
								currentNode.G + G_WEIGHT_AXIAL, heuristic(neighbor, end)));
				}
			
		}
		
		return false;
	}
	
	private int heuristic(Tile current, Tile destination) {
		int horizontal = Math.abs((int) (destination.getPosition().x - current.getPosition().x));
		int vertical = Math.abs((int) (destination.getPosition().y - current.getPosition().y));
		return (horizontal + vertical) * G_WEIGHT_AXIAL;
	}
	
	private List<Tile> readPath(PathNode path) {
		List<Tile> tmp = new ArrayList<Tile>();
		
		do {
			tmp.add(path.tile);
			path = path.parent;
		} while (path.parent != null);
		
		// Return in correct order
		Collections.reverse(tmp);
		
		return tmp;
	}
	
}
