package com.devtalk.maze;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Path {
	
	public static Texture texture = new Texture(Gdx.files.internal("IN_MAZE.png"));
	
	private Vector2 position;
	private Vector2 center;
	
	private List<Path> neighbors;
	private List<Wall> walls;
	
	public void Path() {
		this.position = new Vector2();
		this.center = new Vector2();
		
		
	}
}
