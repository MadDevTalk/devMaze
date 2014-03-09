package com.devTalk.devMaze.maze;

import com.devTalk.devMaze.actors.Actor.ActorType;


public class Level {

	public enum LEVEL {
		LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5, LEVEL_6,
	}

	int mazeWidth, mazeHeight, numMonsters, numItems;
	ActorType monsterDifficulty;

	public Level(LEVEL level) {
		switch (level) {
		case LEVEL_1:
			mazeWidth = 15;
			mazeHeight = 11;
			numMonsters = 1;
			numItems = 1;
			monsterDifficulty = ActorType.EASY;
			break;
		case LEVEL_2:
			mazeWidth = 21;
			mazeHeight = 17;
			numMonsters = 2;
			numItems = 4;
			monsterDifficulty = ActorType.EASY;
			break;
		case LEVEL_3:
			mazeWidth = 31;
			mazeHeight = 25;
			numMonsters = 4;
			numItems = 7;
			monsterDifficulty = ActorType.EASY;
			break;
		case LEVEL_4:
			mazeWidth = 35;
			mazeHeight = 29;
			numMonsters = 6;
			numItems = 9;
			monsterDifficulty = ActorType.MEDIUM;
			break;
		case LEVEL_5:
			mazeWidth = 35;
			mazeHeight = 29;
			numMonsters = 8;
			numItems = 10;
			monsterDifficulty = ActorType.HARD;
			break;
		case LEVEL_6:
			mazeWidth = 35;
			mazeHeight = 29;
			numMonsters = 10;
			numItems = 15;
			monsterDifficulty = ActorType.HARD;
			break;
		}
	}
	
}
