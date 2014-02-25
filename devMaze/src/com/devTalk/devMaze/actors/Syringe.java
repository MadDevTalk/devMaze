package com.devTalk.devMaze.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.devTalk.devMaze.maze.DevMaze;

public class Syringe implements Item {


	private Player player;
	private SpriteBatch batch;

	private static Texture mapTexture = new Texture(Gdx.files.internal("pill.png"));
	private static Texture packTexture = new Texture(Gdx.files.internal("pill.png"));
	
	private Rectangle mapRectangle;
	private Rectangle packRectangle;

	private static final int ITEM_ID = 3;
	
	public Syringe(float x, float y, DevMaze g){
		batch = g.batch;
		player = g.player;
		mapRectangle = new Rectangle(x, y, mapTexture.getHeight(), mapTexture.getWidth());
		packRectangle = new Rectangle(0, 1, packTexture.getHeight(), packTexture.getWidth());
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		mapTexture.dispose();
		packTexture.dispose();
	}

	@Override
	public boolean equals(Item item) {
		return item.getID() == ITEM_ID;
	}

	@Override
	public int getID() {
		return ITEM_ID;
	}

	@Override
	public Rectangle mapRectangle() {
		// TODO Auto-generated method stub
		return mapRectangle;
	}

	@Override
	public Texture mapTexture() {
		// TODO Auto-generated method stub
		return mapTexture;
	}

	@Override
	public Rectangle packRectangle() {
		// TODO Auto-generated method stub
		return packRectangle;
	}

	@Override
	public Texture packTexture() {
		return packTexture;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

}
