package com.devtalk.maze;

public class Wall {
	
	public int row;
	public int col;
	
	public int rowOffset;
	public int colOffset;
	
	public Wall(int originRow, int originCol, int wallRow, int wallCol) {
		this.row = wallRow;
		this.col = wallCol;
		this.rowOffset = wallRow - originRow;
		this.colOffset = wallCol - originCol;
	}
	
}
