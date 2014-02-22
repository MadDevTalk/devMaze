package com.devTalk.devMaze.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public interface Item {
	public void action();

	public void dispose();

	public boolean equals(Item item);

	public int getID();

	public Rectangle mapRectangle();

	public Texture mapTexture();

	public Rectangle packRectangle();

	public Texture packTexture();

	public void render();
};

