package com.devTalk.devMaze.gui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.devTalk.devMaze.actors.Player;
import com.devTalk.devMaze.maze.DevMaze;

public class MazeInputProcessor implements InputProcessor {

	public static final int DRAG_THRESHOLD_PX = 20;
	
	private Player player;
	private Vector2 touch_down, touch_up;

	public MazeInputProcessor(DevMaze game) {
		player = game.player;
		touch_down = new Vector2();
		touch_up = new Vector2();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
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

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
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

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touch_down.set(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touch_up.set(screenX, screenY);
		player.velocity.set(0,0,0);

		int x = (int) (touch_up.x - touch_down.x);
		int y = (int) (touch_up.y - touch_down.y);
		
		int mag_x = Math.abs(x);
		int mag_y = Math.abs(y);
		
		// If we are just tapping, stop
		if (mag_x <= DRAG_THRESHOLD_PX && mag_y <= DRAG_THRESHOLD_PX)
			return false;
		
		// Otherwise find and move in correct cardinal direction
		if(mag_x > mag_y) {
			
			if(x > 0)
				player.start(-DevMaze.KEY_VEL_PxPer60S, 0);
			else
				player.start(DevMaze.KEY_VEL_PxPer60S, 0);
			
		} else {
			
			if(y > 0)
				player.start(0, DevMaze.KEY_VEL_PxPer60S);
			else
				player.start(0, -DevMaze.KEY_VEL_PxPer60S);

		}
		
		return true;
	}

}
