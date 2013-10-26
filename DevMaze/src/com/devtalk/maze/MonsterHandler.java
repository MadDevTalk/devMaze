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
		
	}
	
}
