package tetris.game;

import java.security.SecureRandom;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import tetris.tetromino.Block;
import tetris.tetromino.Tetromino;

public class TetrisGameState extends BasicGameState {

//======================================================================
//
//--------------------------------Fields--------------------------------
//
//======================================================================
	
	private static final int START_HEIGHT = 1;

	private static final int NEXT_TETROMINO_ROTATION = 1;
	private static final int NEXT_TETROMINO_CENTER_ROW = Setup.HEIGHT_IN_BLOCKS / 2;
	private static final int NEXT_TETROMINO_CENTER_COL = Setup.WIDTH_IN_BLOCKS + 2;
	
	private static final int HOLD_TETROMINO_ROTATION = 1;
	private static final int HOLD_TETROMINO_CENTER_ROW = Setup.HEIGHT_IN_BLOCKS / 2 - 1;
	private static final int HOLD_TETROMINO_CENTER_COL = -3;
	
	private Board board;
	private Tetromino currentTetromino;
	private Tetromino nextTetromino;
	private Tetromino holdTetromino;
	private boolean canHold;
	
	private double dropTimer, dropInterval;
	
	private int score, totalLinesCleared;
	
	
	private static SecureRandom random = new SecureRandom();
	
//======================================================================
//
//-------------------------------Methods--------------------------------
//
//======================================================================
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		resetGame();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		System.out.println(currentTetromino.getCenter().getRow());
		
		if (!gameOver()) {
			playerMove(container.getInput());
			playerRotate(container.getInput());
			drop(delta);
		
			currentTetromino.updateBlocks();
		} else {
			resetGame();
			container.getInput().clearKeyPressedRecord();
			game.enterState(Tetris.GAME_OVER_STATE);
		}
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		for (int row = Board.BUFFER; row < board.getRows(); row++) {
			for (int col = 0; col < board.getCols(); col++) {
				g.setColor(board.blockAt(row, col).getColor());
				g.fillRect(Setup.BUFFER_LEFT + col * Block.BLOCK_SIZE, Setup.BUFFER_UP + (row - Board.BUFFER) * Block.BLOCK_SIZE, 
						Block.BLOCK_SIZE, Block.BLOCK_SIZE);
			}
		}
		
		g.setColor(Color.gray);
		for (int row = Board.BUFFER; row < board.getRows(); row++) {
			for (int col = 0; col < board.getCols(); col++) {
				g.drawRect(Setup.BUFFER_LEFT + col * Block.BLOCK_SIZE, Setup.BUFFER_UP + (row - Board.BUFFER) * Block.BLOCK_SIZE, 
						Block.BLOCK_SIZE, Block.BLOCK_SIZE);
			}
		}
		
		for (Block block : currentTetromino.getBlocks()) {
			if (block.inView()) {
				g.setColor(block.getColor());
				g.fillRect(Setup.BUFFER_LEFT + block.getCol() * Block.BLOCK_SIZE, 
						Setup.BUFFER_UP + (block.getRow() - Board.BUFFER) * Block.BLOCK_SIZE, 
						Block.BLOCK_SIZE, Block.BLOCK_SIZE);
			}
		}
		
		for (Block block : nextTetromino.getBlocks()) {
			g.setColor(block.getColor());
			g.fillRect(Setup.BUFFER_LEFT + block.getCol() * Block.BLOCK_SIZE, 
					Setup.BUFFER_UP + (block.getRow() - Board.BUFFER) * Block.BLOCK_SIZE, 
					Block.BLOCK_SIZE, Block.BLOCK_SIZE);
		}
		
		for (Block block : holdTetromino.getBlocks()) {
			g.setColor(block.getColor());
			g.fillRect(Setup.BUFFER_LEFT + block.getCol() * Block.BLOCK_SIZE, 
					Setup.BUFFER_UP + (block.getRow() - Board.BUFFER) * Block.BLOCK_SIZE, 
					Block.BLOCK_SIZE, Block.BLOCK_SIZE);
		}
		
		g.setColor(Color.pink);
		g.drawString("Next Piece", Setup.BUFFER_LEFT + NEXT_TETROMINO_CENTER_COL * Block.BLOCK_SIZE - 1 * Block.BLOCK_SIZE,
						Setup.BUFFER_UP + (NEXT_TETROMINO_CENTER_ROW - 4) * Block.BLOCK_SIZE);
		g.drawString("Score:\n" + score, 1 * Block.BLOCK_SIZE, (Setup.HEIGHT_IN_BLOCKS - 5) * Block.BLOCK_SIZE); 
		g.drawString("Lines:\n" + totalLinesCleared, 1 * Block.BLOCK_SIZE, (Setup.HEIGHT_IN_BLOCKS - 3) * Block.BLOCK_SIZE);
		g.drawString("Move: Left/Right\nSoft Drop: Down\nHard Drop: Up\nRotate: Z/X\nHold: Space", 
						Setup.BUFFER_LEFT + NEXT_TETROMINO_CENTER_COL * Block.BLOCK_SIZE - 2 * Block.BLOCK_SIZE + 5,
						Setup.BUFFER_UP + (NEXT_TETROMINO_CENTER_ROW + 4) * Block.BLOCK_SIZE);
		g.drawString("Hold:", Setup.BUFFER_LEFT + HOLD_TETROMINO_CENTER_COL * Block.BLOCK_SIZE - 1 * Block.BLOCK_SIZE,
						Setup.BUFFER_UP + (HOLD_TETROMINO_CENTER_ROW - 4) * Block.BLOCK_SIZE);
		
	}
	
	public void updateBoard() {
		for (Block block : currentTetromino.getBlocks()) {
			board.setBlockAt(block.getRow(), block.getCol(), block);
		}
	}
	
	public void drop(int delta) {
						
		dropTimer += (double) delta / 1000;
		
		System.out.println(dropInterval);

		if (dropTimer > dropInterval) {
			dropTimer = 0;
			dropInterval *= 0.999;
			if (!canMoveDown()) {
				nextPiece();
			} else
				moveDown();
		}
	}
	
	public void playerRotate(Input input) {
				
		if (input.isKeyPressed(Input.KEY_Z))
			currentTetromino.rotateLeft();
		
		if (input.isKeyPressed(Input.KEY_X))
			currentTetromino.rotateRight();
		
		if (!currentTetromino.farthestBlockRight().inBoundsRight()) {
			while (!currentTetromino.inBounds()) {
				moveLeft();
			}
		}
		
		if (!currentTetromino.farthestBlockLeft().inBoundsLeft()) {
			while (!currentTetromino.inBounds()) {
				moveRight();
			}
		}
		
		if (!currentTetromino.farthestBlockDown().inBoundsDown()) {
			while (!currentTetromino.inBounds()) {
				moveUp();
			}
		}
		
	}
	
	public void playerMove(Input input) {
		if (input.isKeyPressed(Input.KEY_RIGHT) && canMoveRight())
			moveRight();
			
		if (input.isKeyPressed(Input.KEY_UP))
			hardDrop();
			
		if (input.isKeyPressed(Input.KEY_LEFT) && canMoveLeft())
			moveLeft();
			
		if (input.isKeyPressed(Input.KEY_DOWN) && canMoveDown())
			moveDown();
		
		if (input.isKeyPressed(Input.KEY_SPACE))
			hold();
	}
	
	public void moveRight() {
		currentTetromino.moveRight();
	}
	
	public void moveUp() {
		currentTetromino.moveUp();
	}
	
	public void moveLeft() {
		currentTetromino.moveLeft();
	}
	
	public void moveDown() {
		currentTetromino.moveDown();
	}
	
	public void hardDrop() {
		while(canMoveDown()) {
			moveDown();
		}
		
		score += 15;
		nextPiece();
	}
	
	public void hold() {
		if (canHold) {
			if (holdTetromino == Tetromino.BLANK) {
				holdTetromino = currentTetromino;
				currentTetromino = nextTetromino;
				initializeNextTetromino();
			} else {
				Tetromino temp = holdTetromino;
				holdTetromino = currentTetromino;
				currentTetromino = temp;
			}

			currentTetromino.getCenter().setRow(START_HEIGHT);
			currentTetromino.getCenter().setCol(random.nextInt(Setup.WIDTH_IN_BLOCKS - 4) + 2);
			initializeHoldTetromino();
			canHold = false;
		}
	}
	
	public void initializeHoldTetromino() {
		holdTetromino.rotate(HOLD_TETROMINO_ROTATION);
		holdTetromino.getCenter().setRow(HOLD_TETROMINO_CENTER_ROW);
		holdTetromino.getCenter().setCol(HOLD_TETROMINO_CENTER_COL);
		holdTetromino.updateBlocks();
	}
	
	public boolean canMoveRight() {
		if (currentTetromino.farthestBlockRight().getCol() >= board.getCols() - 1)
			return false;
		
		for (Block block : currentTetromino.getBlocks()) {
			Block blockRight = board.blockAt(block.getRow(), block.getCol() + 1);
			if (blockRight.getColor() != block.getColor() && blockRight.getColor() != Board.NEUTRAL_COLOR)
				return false;
		}
		
		return true;
	}
	
	public boolean canMoveUp() {
		if (currentTetromino.farthestBlockUp().getRow() <= 0)
			return false;
		
		for (Block block : currentTetromino.getBlocks()) {
			Block blockUp = board.blockAt(block.getRow() - 1, block.getCol());
			if (blockUp.getColor() != block.getColor() && blockUp.getColor() != Board.NEUTRAL_COLOR)
				return false;
		}
		
		return true;
	}
	
	public boolean canMoveLeft() {
		if (currentTetromino.farthestBlockLeft().getCol() <= 0)
			return false;
		
		for (Block block : currentTetromino.getBlocks()) {
			Block blockLeft = board.blockAt(block.getRow(), block.getCol() - 1);
			if (blockLeft.getColor() != block.getColor() && blockLeft.getColor() != Board.NEUTRAL_COLOR)
				return false;
		}
		
		return true;
	}
	
	public boolean canMoveDown() {
			
		if (currentTetromino.farthestBlockDown().getRow() >= board.getRows() - 1)
			return false;
		
		for (Block block : currentTetromino.getBlocks()) {
			Block blockDown = board.blockAt(block.getRow() + 1, block.getCol());
			if (blockDown.getColor() != block.getColor() && blockDown.getColor() != Board.NEUTRAL_COLOR)
				return false;
		}
		
		return true;
	}

	public void clearLines() {
		
		int lines = 0;
		
		for (int row = 0; row < board.getRows(); row++) {
			if (board.isRowFilled(row)) {
				board.clearRow(row);
				board.shiftBoardDown(row - 1);
				score += 100;
				totalLinesCleared++;
				lines++;
			}
		}
		
		if (lines == 4)
			score += 1000;
	}
		
	public void finalizeCurrentTetromino() {
		for (Block block : currentTetromino.getBlocks()) {
			board.setBlockAt(block.getRow(), block.getCol(), block);
		}
	}
	
	public void nextPiece() {
		finalizeCurrentTetromino();
		clearLines();
		currentTetromino = nextTetromino;
		currentTetromino.getCenter().setRow(START_HEIGHT);
		currentTetromino.getCenter().setCol(random.nextInt(Setup.WIDTH_IN_BLOCKS - 4) + 2);
		initializeNextTetromino();
		canHold = true;
	}
	
	public void initializeNextTetromino() {
		nextTetromino = generateNewTetromino();
		nextTetromino.rotate(NEXT_TETROMINO_ROTATION);
		nextTetromino.getCenter().setRow(NEXT_TETROMINO_CENTER_ROW);
		nextTetromino.getCenter().setCol(NEXT_TETROMINO_CENTER_COL);
		nextTetromino.updateBlocks();
	}
	
	public Tetromino generateNewTetromino() {
		return Tetromino.randomTetromino(START_HEIGHT, random.nextInt(Setup.WIDTH_IN_BLOCKS - 4) + 2);
	}
	
	public boolean gameOver() {
		for (int col = 0; col < board.getCols(); col++) {
			if (board.blockAt(Board.BUFFER, col).getColor() != Board.NEUTRAL_COLOR) {
				return true;
			}
		}
		
		return false;
	}
	
	public void resetGame() {
		board = new Board(Setup.HEIGHT_IN_BLOCKS, Setup.WIDTH_IN_BLOCKS);
		currentTetromino = generateNewTetromino();
		initializeNextTetromino();
		dropTimer = 0;
		dropInterval = 1;
		score = 0;
		totalLinesCleared = 0;
		holdTetromino = Tetromino.BLANK;
		canHold = true;
	}
	
	@Override
	public int getID() {
		return Tetris.GAME_STATE;
	}

}
