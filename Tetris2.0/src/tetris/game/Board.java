package tetris.game;

import org.newdawn.slick.Color;

import tetris.tetromino.Block;

public class Board {

//======================================================================
//
//--------------------------------Fields--------------------------------
//
//======================================================================
	
	public static final int MOVE_RIGHT = 0;
	public static final int MOVE_UP = 1;
	public static final int MOVE_LEFT = 2;
	public static final int MOVE_DOWN = 3;
	
	public static final Color NEUTRAL_COLOR = Color.transparent;
	public static final int BUFFER= 2;
	
	private int rows, cols; 
	private Block[][] blocks;
		
//======================================================================
//
//-----------------------------Constructors-----------------------------
//
//======================================================================
	
	public Board(int rows, int cols) {
		this.rows = rows + BUFFER;
		this.cols = cols;
		blocks = new Block[this.rows][this.cols];
		
		initialize();
	}
	
//======================================================================
//
//-------------------------------Methods--------------------------------
//
//======================================================================
	
	public void initialize() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				blocks[row][col] = new Block(row, col, 0, 0, NEUTRAL_COLOR);
			}
		}
	}
	
	public boolean isRowFilled(int row) {
		for (int col = 0; col < cols; col++) {
			if (blocks[row][col].getColor() == NEUTRAL_COLOR)
				return false;
		}
		
		return true;
	}
	
	public void shiftBoardDown(int startRow) {
		for (int row = startRow; row >= 0; row--) {
			shiftRowDown(row);
		}
		
		clearRow(0);
	}
	
	public void shiftRowDown(int row) {
		for (int col = 0; col < cols; col++) {
			moveBlockDown(row, col);
		}
	}
	
	public void clearRow(int row) {
		for (int col = 0; col < cols; col++) {
			blocks[row][col].setColor(NEUTRAL_COLOR);
		}
	}
	
	public void moveBlock(int row, int col, int move) {
		switch (move) {
			case MOVE_RIGHT:
				moveBlockRight(row, col);
				break;
			case MOVE_UP:
				moveBlockUp(row, col);
				break;
			case MOVE_LEFT:
				moveBlockLeft(row, col);
				break;
			case MOVE_DOWN:
				moveBlockDown(row, col);
				break;
		}
	}
	
	public void moveBlockRight(int row, int col) {
		blocks[row][col + 1] = blocks[row][col];
	}
	
	public void moveBlockUp(int row, int col) {
		blocks[row - 1][col] = blocks[row][col];
	}
	
	public void moveBlockLeft(int row, int col) {
		blocks[row][col - 1] = blocks[row][col];
	}
	
	public void moveBlockDown(int row, int col) {
		blocks[row + 1][col] = blocks[row][col];
	}
	
	public Block blockAt(int row, int col) {
		return blocks[row][col];
	}
	
	public void setBlockAt(int row, int col, Block block) {
		blocks[row][col] = block;
	}
	
//======================================================================
//
//---------------------------Getters/Setters----------------------------
//
//======================================================================
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}
	
}
