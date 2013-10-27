package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.devtalk.maze.Monster.MonsterType;
import com.devtalk.maze.Monster.WalkState;

public class MonsterHandler {
	
	private static final int G_WEIGHT = 10;;
	
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
				//followPlayer(monster);
				break;
			case FINDING_DESTINATION:
				//seekDestination(monster);
				break;
			case AT_DESTINATION:
				//setDestination(monster);
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
		
		do {
			
		} while(monster.path.isEmpty());
		
		monster.walkState = WalkState.FINDING_DESTINATION;
	}
	
	private void seekDestination(Monster monster) {
		Tile currentPosition = Utils.tileLocation(monster.position.y, monster.position.x);
		Tile destination = monster.path.get(monster.path.size() - 1);
		
		if (currentPosition == destination) {
			monster.walkState = WalkState.AT_DESTINATION;
			monster.path.clear();
		}
	}
	
	/**
	 * A* pathing implemented as per http://www.policyalmanac.org/games/aStarTutorial.htm
	 */
	private boolean findPath(Monster monster, Tile start, Tile end) {
		PathList openList = new PathList();
		PathList closedList = new PathList();
		PathNode currentNode = new PathNode(start, null, 0, 0);
		
		// Add the starting node to the open list
		openList.add(currentNode);
		
		while (!openList.isEmpty()) {
			
			// Look for lowest cost node in the open list
			for (PathNode node : openList)
				if (node.F() <= currentNode.F())
					currentNode = node;
			
			// Switch it to the closed list
			closedList.add(openList.remove(openList.indexOf(currentNode)));
			
			// For each of the adjacent nodes...
			List<Tile> neighbors = currentNode.tile.getNeighbors();
			for (Tile neighbor : neighbors) {
				
				// If the tile is in the maze and not already in the path
				if (neighbor.inMaze() && !closedList.contains(neighbor)) {
					if (openList.contains(neighbor)) {
						
						// Check to see if this path is better using G cost
						PathNode node = openList.get(neighbor);
						if (node.G <= currentNode.G) {
							node.parent = currentNode.tile;
							//
						}
						
					} else
						openList.add(new PathNode(neighbor, currentNode.tile, 
								currentNode.G + G_WEIGHT, heuristic(neighbor, end)));
					
				}
				
			}
			
		}
		
		return true;
	}
	
	private int heuristic(Tile current, Tile destination) {
		return 0;
	}
	
}
