package com.devTalk.devMaze.gui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.devTalk.devMaze.actors.Player;
import com.devTalk.devMaze.maze.DevMaze;

public class DirectionalModule implements HUDModule {

	public static int DPAD_SIZE_PX = 192;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	
	private Player player;
	
	private boolean active;
	private Vector2 midPoint, dragPoint;
	private int radius;

	public DirectionalModule(DevMaze g) {
		camera = g.camera;
		shapeRenderer = g.shapeRenderer;
		player = g.player;
		active = false;
		midPoint = new Vector2();
		dragPoint = new Vector2();
	}

	public boolean actionedAt(int x, int y) {
		midPoint.set(x, y);
		return active = true;
	}

	public void dispose() {
	}

	public void render() {
		if (active) {
			shapeRenderer.begin(ShapeType.Line);
			float x = (camera.position.x - camera.viewportWidth / 2) + midPoint.x;  
			float y = (camera.position.y + camera.viewportHeight / 2) - midPoint.y;
			shapeRenderer.setColor(254, 1, 1, 1);
			shapeRenderer.circle(x, y, radius);
			shapeRenderer.line(x, y, x + dragPoint.x, y - dragPoint.y);
			shapeRenderer.end();
		}
	}

	public void stopAction(int x, int y) {
		active = false;
		player.velocity.set(0,0,0);
	}

	public boolean draggedAt(int x, int y) {
		if (active) {
			int dX = (int) (x - midPoint.x);
			int dY = (int) (y - midPoint.y);
			dragPoint.set(dX, dY);
			radius = (int) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
			
			int velocity = (int) (3.5 * Math.log10(radius));
			player.velocity.set(dX > 0 ? velocity : -velocity, 
								dY > 0 ? -velocity : velocity, 0);
			return true;
		}
		
		return false;
	}

}