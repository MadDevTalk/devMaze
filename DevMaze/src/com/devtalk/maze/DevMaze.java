package com.devtalk.maze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DevMaze extends Game {
	
	SpriteBatch batch;
	BitmapFont font;

	public void create() {
		batch = new SpriteBatch();
		// Arial font (LibGDX's default)
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}
	
	public void render() {
		super.render();
	}
	
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}