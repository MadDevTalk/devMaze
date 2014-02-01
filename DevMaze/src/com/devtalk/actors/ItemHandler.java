package com.devtalk.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.devtalk.maze.DevMaze;
import com.devtalk.maze.Maze;

public class ItemHandler {

	private Maze maze;
	private DevMaze game;
	private Player player;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	public List<Item> items;

	public ItemHandler(DevMaze g) {
		this.game = g;
		this.maze = g.maze;
		this.batch = g.batch;
		this.player = g.player;
		this.camera = g.camera;
		
		items = new ArrayList<Item>();
	}

	public void dispose() {
		for (Item item : this.items)
		{
			item.dispose();
		}
	}

	public void render() {
		for (Item item : this.items) 
		{
			Vector3 center = new Vector3(item.mapRectangle().x, item.mapRectangle().y, 0);
			if (camera.frustum.sphereInFrustum(center, item.mapRectangle().getWidth()))
			{
				batch.draw(item.mapTexture(), item.mapRectangle().x,
						item.mapRectangle().y);
	
				if (DevMaze.DEBUG) 
				{
					game.font.draw(batch, "this is an item.",
							item.mapRectangle().x, item.mapRectangle().y);
				}
			}
		}
	}

	public void runOverItem(Rectangle playerArea) {
		List<Item> usedItems = new ArrayList<Item>();
		for (Item item : this.items) 
		{
			if (playerArea.overlaps(item.mapRectangle())) {
				player.pack.add(item);
				usedItems.add(item);
			}
		}
		this.items.removeAll(usedItems);
	}

	public void set() {
		items.clear();
		//TODO 
	}
	
	public Item chooseItem(int item_id) {
		return null;
	}

	public void updateItems() {
		this.runOverItem(player.rectangle);
	}
}