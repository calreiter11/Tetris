package tetris.tetromino;

import java.security.SecureRandom;

import org.newdawn.slick.Color;

public class Tetromino {

//======================================================================
//
//--------------------------------Fields--------------------------------
//
//======================================================================
	
	private Block center, block1, block2, block3;
	private Block[] blocks;
	
	private int rotation;
	
	private static SecureRandom random = new SecureRandom(); 
	
	public static final Tetromino BLANK = Tetromino.blankTetromino(0, 0);
	
//======================================================================
//
//-----------------------------Constructors-----------------------------
//
//======================================================================
	
	public Tetromino(Block center, Block block1, Block block2, Block block3, int rotation) {
		this.center = center;
		this.block1 = block1;
		this.block2 = block2;
		this.block3 = block3;
		
		blocks = new Block[4];
		blocks[0] = center;
		blocks[1] = block1;
		blocks[2] = block2;
		blocks[3] = block3;
		
		this.rotation = rotation;
	}
	
	public Tetromino(Block center, Block block1, Block block2, Block block3) {
		this.center = center;
		this.block1 = block1;
		this.block2 = block2;
		this.block3 = block3;
		
		blocks = new Block[4];
		blocks[0] = center;
		blocks[1] = block1;
		blocks[2] = block2;
		blocks[3] = block3;
		
		rotation = 0;
	}
	
//======================================================================
//
//-------------------------------Methods--------------------------------
//
//======================================================================
	
	public void rotate(int rotation) {
		this.rotation = rotation;
		for (Block block : blocks) {	
			switch(rotation) {
				case 0:
					block.setColRel(block.getDefaultColRel());
					block.setRowRel(block.getDefaultRowRel());
					break;
				case 1:
					block.setColRel(block.getDefaultRowRel());
					block.setRowRel(block.getDefaultColRel() * -1);
					break;
				case 2:
					block.setColRel(block.getDefaultColRel() * -1);
					block.setRowRel(block.getDefaultRowRel() * -1);
					break;
				case 3:
					block.setColRel(block.getDefaultRowRel() * -1);
					block.setRowRel(block.getDefaultColRel());
					break;
			}
		}
		
		updateBlocks();
	}
	
	public void rotateLeft() {
		rotate(((rotation + 1) + 4) % 4);
	}
	
	public void rotateRight() {
		rotate(((rotation - 1) + 4) % 4);
	}
	
	public void moveRight() {
		center.moveRight();
		updateBlocks();
	}
	
	public void moveUp() {
		center.moveUp();
		updateBlocks();
	}
	
	public void moveLeft() {
		center.moveLeft();
		updateBlocks();
	}
	
	public void moveDown() {
		center.moveDown();
		updateBlocks();
	}
	
	public void updateBlocks() {
		for (Block block : blocks) {
			block.setRow(center.getRow() + block.getRowRel());
			block.setCol(center.getCol() + block.getColRel());
		}
	}
	
	public Block farthestBlockRight() {
		Block farthestBlockRight = center;
		int maxCol = Integer.MIN_VALUE;
		
		for (Block block : blocks) {
			if (block.getCol() > maxCol) {
				maxCol = block.getCol();
				farthestBlockRight = block;
			}
		}
		
		return farthestBlockRight;
	}
	
	public Block farthestBlockUp() {
		Block farthestBlockUp = center;
		int leastRow = Integer.MAX_VALUE;
		
		for (Block block : blocks) {
			if (block.getRow() < leastRow) {
				leastRow = block.getRow();
				farthestBlockUp = block;
			}
		}
		
		return farthestBlockUp; 
	}
	
	public Block farthestBlockLeft() {
		Block farthestBlockLeft = center;
		int leastCol = Integer.MAX_VALUE;
		
		for (Block block : blocks) {
			if (block.getCol() < leastCol) {
				leastCol = block.getCol();
				farthestBlockLeft = block;
			}
		}
		
		return farthestBlockLeft;
	}
	
	public Block farthestBlockDown() {
		Block farthestBlockDown = center;
		int maxRow = Integer.MIN_VALUE;
		
		for (Block block : blocks) {
			if (block.getRow() > maxRow) {
				maxRow = block.getRow();
				farthestBlockDown = block;
			}
		}
		
		return farthestBlockDown;
	}
	
	public boolean inBounds() {
		for (Block block : blocks)
			if (!block.inBounds())
				return false;
		
		return true;
	}
	
	public static Tetromino randomTetromino(int centerRow, int centerCol) {
		switch(random.nextInt(7)) {
			case 0: default:
				return randomLineTetromino(centerRow, centerCol);
			case 1:
				return randomLTetromino(centerRow, centerCol);
			case 2:
				return randomJTetromino(centerRow, centerCol);
			case 3:
				return randomSquareTetromino(centerRow, centerCol);
			case 4:
				return randomTTetromino(centerRow, centerCol);
			case 5:
				return randomZTetromino(centerRow, centerCol);
			case 6:
				return randomSTetromino(centerRow, centerCol);
		}
	}
	
	public static Tetromino randomLineTetromino(int centerRow, int centerCol) {
		int color = random.nextInt(0xffffff);
		Block center = new Block(centerRow, centerCol, 0, 0, color);
		Block block1 = new Block(centerRow, centerCol, 0, -2, color);
		Block block2 = new Block(centerRow, centerCol, 0, -1, color);
		Block block3 = new Block(centerRow, centerCol, 0, 1, color);
		return new Tetromino(center, block1, block2, block3);
	}
	
	public static Tetromino randomLTetromino(int centerRow, int centerCol) {
		int color = random.nextInt(0xffffff);
		Block center = new Block(centerRow, centerCol, 0, 0, color);
		Block block1 = new Block(centerRow, centerCol, 0, -1, color);
		Block block2 = new Block(centerRow, centerCol, 1, -1, color);
		Block block3 = new Block(centerRow, centerCol, 0, 1, color);
		return new Tetromino(center, block1, block2, block3);
	}
	
	public static Tetromino randomJTetromino(int centerRow, int centerCol) {
		int color = random.nextInt(0xffffff);
		Block center = new Block(centerRow, centerCol, 0, 0, color);
		Block block1 = new Block(centerRow, centerCol, 0, -1, color);
		Block block2 = new Block(centerRow, centerCol, 0, 1, color);
		Block block3 = new Block(centerRow, centerCol, 1, 1, color);
		return new Tetromino(center, block1, block2, block3);
	}
	
	public static Tetromino randomSquareTetromino(int centerRow, int centerCol) {
		int color = random.nextInt(0xffffff);
		Block center = new Block(centerRow, centerCol, 0, 0, color);
		Block block1 = new Block(centerRow, centerCol, -1, 0, color);
		Block block2 = new Block(centerRow, centerCol, -1, 1, color);
		Block block3 = new Block(centerRow, centerCol, 0, 1, color);
		return new Tetromino(center, block1, block2, block3);
	}
	
	public static Tetromino randomTTetromino(int centerRow, int centerCol) {
		int color = random.nextInt(0xffffff);
		Block center = new Block(centerRow, centerCol, 0, 0, color);
		Block block1 = new Block(centerRow, centerCol, 0, -1, color);
		Block block2 = new Block(centerRow, centerCol, 1, 0, color);
		Block block3 = new Block(centerRow, centerCol, 0, 1, color);
		return new Tetromino(center, block1, block2, block3);
	}
	
	public static Tetromino randomZTetromino(int centerRow, int centerCol) {
		int color = random.nextInt(0xffffff);
		Block center = new Block(centerRow, centerCol, 0, 0, color);
		Block block1 = new Block(centerRow, centerCol, -1, -1, color);
		Block block2 = new Block(centerRow, centerCol, -1, 0, color);
		Block block3 = new Block(centerRow, centerCol, 0, 1, color);
		return new Tetromino(center, block1, block2, block3);
	}
	
	public static Tetromino randomSTetromino(int centerRow, int centerCol) {
		int color = random.nextInt(0xffffff);
		Block center = new Block(centerRow, centerCol, 0, 0, color);
		Block block1 = new Block(centerRow, centerCol, 0, -1, color);
		Block block2 = new Block(centerRow, centerCol, -1, 0, color);
		Block block3 = new Block(centerRow, centerCol, -1, 1, color);
		return new Tetromino(center, block1, block2, block3);
	}
	
	public static Tetromino blankTetromino(int centerRow, int centerCol) {
		Color color = Color.transparent;
		Block center = new Block(centerRow, centerCol, 0, 0, color);
		Block block1 = new Block(centerRow, centerCol, -1, 0, color);
		Block block2 = new Block(centerRow, centerCol, -1, 1, color);
		Block block3 = new Block(centerRow, centerCol, 0, 1, color);
		return new Tetromino(center, block1, block2, block3);
	}
	
//======================================================================
//
//---------------------------Getters/Setters----------------------------
//
//======================================================================

	public Block getCenter() {
		return center;
	}

	public void setCenter(Block center) {
		this.center = center;
	}

	public Block getBlock1() {
		return block1;
	}

	public void setBlock1(Block block1) {
		this.block1 = block1;
	}

	public Block getBlock2() {
		return block2;
	}

	public void setBlock2(Block block2) {
		this.block2 = block2;
	}

	public Block getBlock3() {
		return block3;
	}

	public void setBlock3(Block block3) {
		this.block3 = block3;
	}

	public Block[] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[] blocks) {
		this.blocks = blocks;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	

	
}
