package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Item {
	public Rectangle mapRectangle();
	public Rectangle packRectangle();
	public Texture mapTexture();
	public Texture packTexture();
	public void render();
	public void action();
	public void dispose();
};

class HealthPowerup implements Item {
//TODO idea: regenerate health when run over by player
	private Player player;
	private SpriteBatch batch;
	
	private static Texture mapTexture = new Texture(Gdx.files.internal("health1.png"));
	private static Texture packTexture = new Texture(Gdx.files.internal("health2.png"));;
	private Rectangle mapRectangle;
	private Rectangle packRectangle;
	
	public HealthPowerup(float xPos, float yPos, DevMaze g) {
		this.batch = g.batch;
		this.player = g.player;
		this.mapRectangle = new Rectangle(xPos, yPos, mapTexture.getHeight(), mapTexture.getWidth());
		this.packRectangle = new Rectangle(0, 0, packTexture.getHeight(), packTexture.getWidth());
	}
	
	public Rectangle mapRectangle() {
		return mapRectangle;
	}
	
	public Rectangle packRectangle() {
		return packRectangle;
	}
	
	public Texture mapTexture() {
		return mapTexture;
	}
	
	public Texture packTexture() {
		return packTexture;
	}

	public void render() {
		batch.draw(this.packTexture(), this.packRectangle().x, this.packRectangle().y);
	}
	
	public void action() {
		player.resetHealth();
	}
	
	public void dispose() {
		mapTexture.dispose();
		packTexture.dispose();
	}

};

//class PillPowerup extends Item {
////TODO idea: make user invincible for n seconds
//	public PillPowerup(float xPos, float yPos, DevMaze g) {
//		super(xPos, yPos, g);
//		this.texture = new Texture(Gdx.files.internal("pill.png"));
//	}
//};

