package com.devTalk.devMaze.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.devTalk.devMaze.maze.DevMaze;

public class LightController implements Item {

	private SpriteBatch batch;

	private static Texture mapTexture;
	private static Texture packTexture;
	
	private Rectangle mapRectangle;
	private Rectangle packRectangle;
	
	private static final int ITEM_ID = 4;
	
	public LightController(float x, float y, DevMaze g){
		batch = g.batch;
		
		mapTexture = new Texture(Gdx.files.internal("CONTROLLER.png"));
		packTexture = new Texture(Gdx.files.internal("CONTROLLER.png"));
		
		mapRectangle = new Rectangle(x, y, mapTexture.getHeight(), mapTexture.getWidth());
		packRectangle = new Rectangle(0, 1, packTexture.getHeight(), packTexture.getWidth());
	}
	
	public void action() {
		System.out.println("I was clicked!");
	}

	public void dispose() {
		mapTexture.dispose();
		packTexture.dispose();
	}

	public boolean equals(Item item) {
		return item.getID() == ITEM_ID;
	}

	public int getID() {
		return ITEM_ID;
	}

	public Rectangle mapRectangle() {
		return mapRectangle;
	}

	public Texture mapTexture() {
		return mapTexture;
	}

	public Rectangle packRectangle() {
		return packRectangle;
	}

	public Texture packTexture() {
		return packTexture;
	}

	public void render() {
		batch.draw(packTexture(), packRectangle().x, packRectangle().y);
	}
	
}