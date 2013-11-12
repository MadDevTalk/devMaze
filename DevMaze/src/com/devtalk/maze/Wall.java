package com.devtalk.maze;

public class Wall {

	public static final int N = 0b1000;
	public static final int E = 0b0100;
	public static final int S = 0b0010;
	public static final int W = 0b0001;
	public static final int NE = 0b1100;
	public static final int SE = 0b0110;
	public static final int SW = 0b0011;
	public static final int NW = 0b1001;
	
	public int row;
	public int col;

	public int rowOffset;
	public int colOffset;
	
	public int side = 0b0000;

	public Wall(int originRow, int originCol, int wallRow, int wallCol) {
		this.row = wallRow;
		this.col = wallCol;
		this.rowOffset = wallRow - originRow;
		this.colOffset = wallCol - originCol;
	}

}
