package com.devTalk.devMaze.gui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.devTalk.devMaze.actors.Player;
import com.devTalk.devMaze.maze.DevMaze;

public class MazeInputProcessor implements InputProcessor {
	
	private Player player;

	public MazeInputProcessor(DevMaze game) {
		player = game.player;
	}

	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
			if (!player.isMoving())
			player.start(0, DevMaze.KEY_VEL_PxPer60S);
			break;
		case Keys.RIGHT:
			player.start(DevMaze.KEY_VEL_PxPer60S, 0);
			break;
		case Keys.DOWN:
			player.start(0, -DevMaze.KEY_VEL_PxPer60S);
			break;
		case Keys.LEFT:
			player.start(-DevMaze.KEY_VEL_PxPer60S, 0);
			break;
		default:
			break;
		}
		
		return false;
	}

	public boolean keyUp(int keycode) {		
		switch (keycode) {
		case Keys.UP:
			player.stop(0, DevMaze.KEY_VEL_PxPer60S);
			break;
		case Keys.RIGHT:
			player.stop(DevMaze.KEY_VEL_PxPer60S, 0);
			break;
		case Keys.DOWN:
			player.stop(0, -DevMaze.KEY_VEL_PxPer60S);
			break;
		case Keys.LEFT:
			player.stop(-DevMaze.KEY_VEL_PxPer60S, 0);
			break;
		default:
			break;
		}
		
		return false;
	}

	public boolean keyTyped(char character) {
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	public boolean scrolled(int amount) {
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

}
