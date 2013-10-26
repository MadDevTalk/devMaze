package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.devtalk.maze.Monster.MonsterType;
import com.devtalk.maze.Monster.WalkState;

public class MonsterHandler {
	
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
	
}
