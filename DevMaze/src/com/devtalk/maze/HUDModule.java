package com.devtalk.maze;

public interface HUDModule {
	
	public void render();
	public boolean actionedAt(int x, int y);
	public void stopAction(int x, int y);
	
}
