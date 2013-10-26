package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

import com.devtalk.maze.Monster.MonsterType;

public class MonsterHandler {
	
	public List<Monster> monsters;
	
	public MonsterHandler(int monsterCount, MonsterType difficulty) {
		monsters = new ArrayList<Monster>();
		
		
		for (int i = 0; i < monsterCount; i++)
		{
			monsters.add(new Monster(64*i, 128, difficulty));
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
				monster.updatePos();
				break;
			default:
				break;
			}
	}
	
}
