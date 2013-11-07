package com.devtalk.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Item {

	private static final int FRAME_COLS = 4;
	private static final int FRAME_ROWS = 4;
	private static final int radius = 2;
	
	private Maze maze;
	private Player player;
	
	Texture texture; 
	Rectangle rect;
	
	public static enum ItemType {
		HEALTH,
		PILL;
	};
	
	public Item(float xPos, float yPos, ItemType type, DevMaze g) {
		this.player = g.player;
		this.maze = g.maze;
		
		switch (type) {
		case HEALTH:
			texture = new Texture(Gdx.files.internal("health1.png"));
			break;
		case PILL:
			texture = new Texture(Gdx.files.internal("pill.png"));
			break;
		}
		
		this.rect = new Rectangle(xPos, yPos, texture.getHeight(), texture.getWidth());
	}
	
	public Rectangle getHitRectangle() {
		return new Rectangle(rect.x - radius, rect.y - radius, 
				rect.height + (2 * radius), rect.width + (2 * radius));
	}
	
	public void dispose() {
		texture.dispose();
	}
};

/*class HealthPowerup extends Item {
//TODO idea: regenerate health when run over by player
	public HealthPowerup(float xPos, float yPos, DevMaze g, String image) {
		super(xPos, yPos, g);
		this.texture = new Texture(Gdx.files.internal("health1.png"));
	}
};

class PillPowerup extends Item {
//TODO idea: make user invincible for n seconds
	public PillPowerup(float xPos, float yPos, DevMaze g) {
		super(xPos, yPos, g);
		this.texture = new Texture(Gdx.files.internal("pill.png"));
	}
};*/

