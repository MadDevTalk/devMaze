package com.devtalk.maze;

import com.devtalk.maze.Monster.MonsterType;

public class Level {
	
	int mazeWidth, mazeHeight;
	int numMonsters;
	int numItems;
	MonsterType monsterDifficulty;
	
	public enum LEVEL {
		LEVEL_1,
		LEVEL_2,
		LEVEL_3,
		LEVEL_4,
		LEVEL_5,
		LEVEL_6,
	}
	
	public Level(LEVEL level) {
		switch (level) {
		case LEVEL_1:
			this.mazeWidth = 15;
			this.mazeHeight = 11;
			this.numMonsters = 0;
			this.numItems = 0;
			this.monsterDifficulty = MonsterType.EASY;
			break;
		case LEVEL_2:
			this.mazeWidth = 21;
			this.mazeHeight = 17;
			this.numMonsters = 2;
			this.numItems = 1;
			this.monsterDifficulty = MonsterType.EASY;
			break;
		case LEVEL_3:
			this.mazeWidth = 31;
			this.mazeHeight = 25;
			this.numMonsters = 10;
			this.numItems = 3;
			this.monsterDifficulty = MonsterType.EASY;
			break;
		case LEVEL_4:
			this.mazeWidth = 35;
			this.mazeHeight = 29;
			this.numMonsters = 15;
			this.numItems = 5;
			this.monsterDifficulty = MonsterType.MEDIUM;
			break;
		case LEVEL_5:
			this.mazeWidth = 35;
			this.mazeHeight = 29;
			this.numMonsters = 15;
			this.numItems = 8;
			this.monsterDifficulty = MonsterType.HARD;
			break;
		case LEVEL_6:
			this.mazeWidth = 35;
			this.mazeHeight = 29;
			this.numMonsters = 30;
			this.numItems = 12;
			this.monsterDifficulty = MonsterType.HARD;
			break;
		}
	}
}
