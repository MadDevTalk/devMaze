package com.devtalk.maze;

public class Wall {

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public Wall(int y1, int x1, int y2, int x2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public int getx1() { return this.x1; }
	public int gety1() { return this.y1; }
	public int getx2() { return this.x2; }
	public int gety2() { return this.y2; }
	
}
