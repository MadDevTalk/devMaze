package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.devtalk.maze.Item.ItemType;

public class ItemHandler {

	private DevMaze game;
	private SpriteBatch batch;
	private Maze maze;
	private Player player;
	
	public List<Item> items;
	
	public ItemHandler(DevMaze g) {
		this.game = g;
		this.batch = g.batch;
		this.maze = g.maze;
		this.player = g.player;
		
		items = new ArrayList<Item>();
	}
	
	public void set(int itemCount) {
		items.clear();
		Random r = new Random();
		for (int i = 0; i < itemCount; i++) {
			Tile openTile = maze.openTiles.get(r.nextInt(maze.openTiles.size()));
			//TODO add a random item type
			items.add(new Item( (float) ((openTile.getPosition().x * DevMaze.EDGE_SIZE_PX) + (DevMaze.EDGE_SIZE_PX / 4)),
					(float) ((openTile.getPosition().y * DevMaze.EDGE_SIZE_PX) + (DevMaze.EDGE_SIZE_PX / 4)), 
					ItemType.HEALTH, this.game));
		}
	}
	
	public void render() {
		for (Item item : this.items) {
			batch.draw(item.texture, item.rect.x, item.rect.y);
			
			if (DevMaze.DEBUG) {
				game.font.draw(batch, "get healthy bitch!", item.rect.x, item.rect.y);
			}
		}
	}
	
	public void updateItems() {
		this.runOverItem(player.rectangle);
	}
	
	public void runOverItem(Rectangle playerArea) {
		List<Item> usedItems = new ArrayList<Item>();
		for (Item item : this.items) {
			if (playerArea.overlaps(item.getHitRectangle())) {
				player.resetHealth();
				usedItems.add(item);
			}
		}
		this.items.removeAll(usedItems);
	}
	
	public void dispose() {
		for (Item item : this.items) {
			item.dispose();
		}
	}
}
