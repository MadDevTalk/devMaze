package com.devtalk.gui;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.devtalk.maze.DevMaze;

public class HUDInputProcessor implements InputProcessor {

	private OrthographicCamera camera;
	private HUD hud;
	private Vector3 touch_down;

	public HUDInputProcessor(DevMaze g, HUD hud) {
		this.camera = g.camera;
		this.hud = hud;
		this.touch_down = new Vector3();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
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
		int tempX = screenX;
		int tempY = screenY;

		screenX += camera.position.x - camera.viewportWidth / 2;
		screenY = (int) (camera.position.y + camera.viewportHeight / 2 - screenY);

		if (hud.actionedAt(screenX, screenY)) {
			this.touch_down.set(tempX, tempY, 0);
			return true;
		} else
			return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		screenX += camera.position.x - camera.viewportWidth / 2;
		screenY = (int) (camera.position.y + camera.viewportHeight / 2 - screenY);
		return hud.actionedAt(screenX, screenY);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		int x = (int) (this.touch_down.x + camera.position.x - (camera.viewportWidth / 2));
		int y = (int) (camera.position.y + camera.viewportHeight / 2 - this.touch_down.y);
		hud.stopAction(x, y);
		return false;
	}

}
