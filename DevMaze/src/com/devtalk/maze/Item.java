package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Item {
	public Rectangle getMapRectangle();
	public Rectangle getPackRectangle();
	public Texture getMapTexture();
	public Texture getPackTexture();
	public void render();
	public void action();
	public void dispose();
};

class HealthPowerup implements Item {
//TODO idea: regenerate health when run over by player
	private Player player;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private static Texture mapTexture = new Texture(Gdx.files.internal("health1.png"));
	private static Texture packTexture = new Texture(Gdx.files.internal("health1.png"));;
	private Rectangle mapRectangle;
	private Rectangle packRectangle;
	
	public HealthPowerup(float xPos, float yPos, DevMaze g) {
		this.batch = g.batch;
		this.player = g.player;
		this.camera = g.camera;
		
		this.mapRectangle = new Rectangle(xPos, yPos, mapTexture.getHeight(), mapTexture.getWidth());
		this.packRectangle = new Rectangle();
	}
	
	public Rectangle getMapRectangle() {
		return mapRectangle;
	}
	
	public Rectangle getPackRectangle() {
		return new Rectangle(camera.position.x - camera.viewportWidth / 2 + 10 * packTexture.getHeight(), 
                (camera.position.y + camera.viewportHeight / 2) - 10 * packTexture.getHeight(),
                packTexture.getWidth(), packTexture.getHeight());
	}
	
	public Texture getMapTexture() {
		return mapTexture;
	}
	
	public Texture getPackTexture() {
		return packTexture;
	}

	public void render() {
		batch.draw(this.getPackTexture(), this.getPackRectangle().x, this.getPackRectangle().y);
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

