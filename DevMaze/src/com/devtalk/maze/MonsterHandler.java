package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.devtalk.maze.Monster.MonsterType;

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
			switch (monster.walkState) {
			case FOLLOWING_PLAYER:
				break;
			case FINDING_DESTINATION:
				break;
			case AT_DESTINATION:
				//monster.updatePos();
				break;
			default:
				break;
			}
	}
	
}
