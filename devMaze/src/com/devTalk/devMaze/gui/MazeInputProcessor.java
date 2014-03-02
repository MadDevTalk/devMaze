package com.devTalk.devMaze.gui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.devTalk.devMaze.actors.Player;
import com.devTalk.devMaze.maze.DevMaze;

public class MazeInputProcessor implements InputProcessor {
	
	private Player player;
	private Vector2 touch_down, touch_dragged;

	public MazeInputProcessor(DevMaze game) {
		player = game.player;
		touch_down = new Vector2();
		touch_dragged = new Vector2();
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

	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
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

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touch_down.set(screenX, screenY);
		
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		touch_dragged.set(screenX, screenY);
		player.velocity.set(0,0,0);

		int x = (int) (touch_dragged.x - touch_down.x);
		int y = (int) (touch_dragged.y - touch_down.y);
		
		player.start(x > 0 ? DevMaze.KEY_VEL_PxPer60S : -DevMaze.KEY_VEL_PxPer60S, 0);
		player.start(0, y > 0 ? -DevMaze.KEY_VEL_PxPer60S : DevMaze.KEY_VEL_PxPer60S);

		return false;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		player.velocity.set(0,0,0);
		return true;
	}

}
