package com.devTalk.devMaze.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.devTalk.devMaze.maze.DevMaze;

public class Weapon implements Item {

	private SpriteBatch batch;

	private static Texture mapTexture = new Texture(Gdx.files.internal("Knife-icon.png"));
	private static Texture packTexture = new Texture(Gdx.files.internal("Knife-icon.png"));
	
	private Rectangle mapRectangle;
	private Rectangle packRectangle;
	
	private static final int ITEM_ID = 2;
	
	public Weapon(float xPos, float yPos, DevMaze g) {
		this.batch = g.batch;
		this.mapRectangle = new Rectangle(xPos, yPos, mapTexture.getHeight(), mapTexture.getWidth());
		this.packRectangle = new Rectangle(0, 0, packTexture.getHeight(), packTexture.getWidth());
	}
	
	@Override
	public void action() {
		// TODO needs to be fleshed out.
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
		return mapRectangle;
	}

	@Override
	public Texture mapTexture() {
		return mapTexture;
	}

	@Override
	public Rectangle packRectangle() {
		return packRectangle;
	}

	@Override
	public Texture packTexture() {
		return packTexture;
	}

	@Override
	public void render() {
		batch.draw(this.packTexture(), this.packRectangle().x, this.packRectangle().y);
	}

};
