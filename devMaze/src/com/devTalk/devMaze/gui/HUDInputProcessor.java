package com.devTalk.devMaze.gui;

import com.badlogic.gdx.InputProcessor;

public class HUDInputProcessor implements InputProcessor {

	private HUD hud;

	public HUDInputProcessor(HUD hud) {
		this.hud = hud;
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
		return hud.actionedAt(screenX, screenY);
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return hud.draggedAt(screenX, screenY);
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		hud.stopAction(screenX, screenY);
		return false;
	}

}
