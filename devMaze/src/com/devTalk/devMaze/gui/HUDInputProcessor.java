package com.devTalk.devMaze.gui;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.devTalk.devMaze.maze.DevMaze;

public class HUDInputProcessor implements InputProcessor {

	private OrthographicCamera camera;
	private HUD hud;

	private Vector3 touch_down;

	public HUDInputProcessor(DevMaze g, HUD hud) {
		this.camera = g.camera;
		this.hud = hud;
		this.touch_down = new Vector3();
	}

	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (hud.actionedAt(screenX, screenY)) {
			//touch_down.set(screenX, tempY, 0);
			return false;
		} else
			return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		int x = (int) (touch_down.x + camera.position.x - (camera.viewportWidth / 2));
		int y = (int) (camera.position.y + camera.viewportHeight / 2 - touch_down.y);
		hud.stopAction(x, y);
		return false;
	}

}
