package tetris.tetromino;

import org.newdawn.slick.Color;

import tetris.game.Board;
import tetris.game.Setup;

public class Block {

//======================================================================
//
//--------------------------------Fields--------------------------------
//
//======================================================================
	
	public static final int BLOCK_SIZE = 32;
	
	private int row, col;
	private int rowRel, colRel;
	private int defaultRowRel, defaultColRel;
	private Color color;
	
//======================================================================
//
//-----------------------------Constructors-----------------------------
//
//======================================================================
	
	public Block(int row, int col, int rowRel, int colRel, Color color) {
		this.row = row;
		this.col = col;
		defaultRowRel = this.rowRel = rowRel;
		defaultColRel = this.colRel = colRel;
		this.color = color;
	}
	
	public Block(int row, int col, int rowRel, int colRel, int color) {
		this.row = row;
		this.col = col;
		defaultRowRel = this.rowRel = rowRel;
		defaultColRel = this.colRel = colRel;
		this.color = new Color(color);
	}
	
//======================================================================
//
//-------------------------------Methods--------------------------------
//
//======================================================================
	
	public void moveRight() {
		col++;
	}
	
	public void moveUp() {
		row--;
	}
	
	public void moveLeft() {
		col--;
	}
	
	public void moveDown() {
		row++;
	}
	
	public boolean inView() {
		return (inBoundsRight() && row >= Board.BUFFER && inBoundsLeft() && inBoundsDown());
	}
	
	public boolean inBounds() {
		return (inBoundsRight() && inBoundsUp() && inBoundsLeft() && inBoundsDown());
	}
	
	public boolean inBoundsRight() {
		return col <= Setup.WIDTH_IN_BLOCKS - 1;
	}

	
	public boolean inBoundsUp() {
		return row >= 0;
	}
	
	public boolean inBoundsLeft() {
		return col >= 0;
	}
	
	public boolean inBoundsDown() {
		return row <= Setup.HEIGHT_IN_BLOCKS + Board.BUFFER - 1;
	}

//=====================================================================
//
//---------------------------Getters/Setters---------------------------
//
//=====================================================================
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public int getRowRel() {
		return rowRel;
	}

	public void setRowRel(int rowRel) {
		this.rowRel = rowRel;
	}

	public int getColRel() {
		return colRel;
	}

	public void setColRel(int colRel) {
		this.colRel = colRel;
	}

	public int getDefaultRowRel() {
		return defaultRowRel;
	}

	public void setDefaultRowRel(int defaultRowRel) {
		this.defaultRowRel = defaultRowRel;
	}

	public int getDefaultColRel() {
		return defaultColRel;
	}

	public void setDefaultColRel(int defaultColRel) {
		this.defaultColRel = defaultColRel;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
