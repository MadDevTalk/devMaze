package com.devtalk.maze;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class HUDInputProcessor implements InputProcessor {
	
	private DevMaze game;
	private OrthographicCamera camera;
	private HUD hud;
	
	private Vector3 touch_down;
	
	public HUDInputProcessor(DevMaze g, HUD hud) {
		this.game = g;
		this.camera = g.camera;
		this.hud = hud;
		this.touch_down = new Vector3(-1, -1, 0);
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

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX += camera.position.x - camera.viewportWidth / 2;
		screenY = (int) (camera.position.y + camera.viewportHeight / 2 - screenY); 
		
		return hud.actionedAt(screenX, screenY);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
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

}
