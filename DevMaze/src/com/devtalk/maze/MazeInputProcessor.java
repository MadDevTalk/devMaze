package com.devtalk.maze;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

// Use gesture listener in order to detect taps, flings, etc.
// http://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/input/GestureDetector.GestureListener.html
public class MazeInputProcessor implements InputProcessor {

	Player player;
	PuppetMaster monsters;
	Vector3 touch_down;

	public MazeInputProcessor(Player player, PuppetMaster monsters) {
		this.player = player;
		this.monsters = monsters;
		this.touch_down = new Vector3();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.UP:
			player.start(0, GameScreen.KEY_VEL_PxPer60S);
			break;
		case Keys.RIGHT:
			player.start(GameScreen.KEY_VEL_PxPer60S, 0);
			break;
		case Keys.DOWN:
			player.start(0, -GameScreen.KEY_VEL_PxPer60S);
			break;
		case Keys.LEFT:
			player.start(-GameScreen.KEY_VEL_PxPer60S, 0);
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.UP:
			player.stop(0, GameScreen.KEY_VEL_PxPer60S);
			break;
		case Keys.RIGHT:
			player.stop(GameScreen.KEY_VEL_PxPer60S, 0);
			break;
		case Keys.DOWN:
			player.stop(0, -GameScreen.KEY_VEL_PxPer60S);
			break;
		case Keys.LEFT:
			player.stop(-GameScreen.KEY_VEL_PxPer60S, 0);
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (button == Buttons.LEFT)
			touch_down = new Vector3(screenX, screenY, 0);

		// Check area of player hit radius for monster hit
		monsters.detectHit(player.getHitRectangle());
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		player.walking = false;
		
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// the current position of the pointer
		Vector3 new_position = new Vector3(x, y, 0);

		// offset of new position from where drag started
		new_position.sub(touch_down);

		// move camera by offset, need to invert x
		player.updatePos((int) -new_position.x, (int) new_position.y);

		// move the drag started position to the current position
		touch_down.add(new_position);

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
