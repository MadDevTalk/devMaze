package com.devtalk.maze;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector3;
import com.devtalk.maze.Player;

public class MainMenuScreenInputProcessor implements InputProcessor {

	Vector3 touch_down;
	Player player;
	
	public MainMenuScreenInputProcessor(Player player) {
		this.player = player;
		touch_down = new Vector3();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT)
			touch_down = new Vector3(screenX, screenY, 0);
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// The current location of the Vector
		Vector3 current_position = new Vector3(screenX, screenY, 0);
		float x, y;
		
		// Current position starts at initial position
		current_position.sub(touch_down);
		x = current_position.x; y = current_position.y;
		
		// Move player by dominant direction, need to invert x
		if(x > y) {
			player.updatePos((int) -current_position.x, 0);
		}
		else {
			player.updatePos((int) 0, (int) current_position.y);
		}
		
		// Update initial position
		touch_down.add(current_position);

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

	
}
