package com.devtalk.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.devtalk.maze.DevMaze;

public class HealthPowerup implements Item {

	private Player player;
	private SpriteBatch batch;

	private static Texture mapTexture = new Texture(Gdx.files.internal("health1.png"));
	private static Texture packTexture = new Texture(Gdx.files.internal("health2.png"));
	
	private Rectangle mapRectangle;
	private Rectangle packRectangle;

	private static final int ITEM_ID = 1;

	public HealthPowerup(float xPos, float yPos, DevMaze g) {
		this.batch = g.batch;
		this.player = g.player;
		this.mapRectangle = new Rectangle(xPos, yPos, mapTexture.getHeight(), mapTexture.getWidth());
		this.packRectangle = new Rectangle(0, 0, packTexture.getHeight(), packTexture.getWidth());
	}

	public void action() {
		player.resetHealth();
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
		batch.draw(this.packTexture(), this.packRectangle().x,
				this.packRectangle().y);
	}

};