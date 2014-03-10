package com.devTalk.devMaze.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.devTalk.devMaze.maze.DevMaze;

public class Syringe implements Item {

	private SpriteBatch batch;

	private static Texture mapTexture = new Texture(Gdx.files.internal("syringe_map.png"));
	private static Texture packTexture = new Texture(Gdx.files.internal("syringe_pack.png"));
	
	private Rectangle mapRectangle, packRectangle;
	private static final int ITEM_ID = 3;
	
	public Syringe(float x, float y, DevMaze g){
		batch = g.batch;
		x -= mapTexture.getWidth() / 2;
		y -= mapTexture.getHeight() / 2;
		mapRectangle = new Rectangle(x, y, mapTexture.getHeight(), mapTexture.getWidth());
		packRectangle = new Rectangle(0, 1, packTexture.getHeight(), packTexture.getWidth());
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
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
