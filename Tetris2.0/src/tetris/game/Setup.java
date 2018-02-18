package tetris.game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import tetris.tetromino.Block;

public class Setup {

//======================================================================
//
//--------------------------------Fields--------------------------------
//
//======================================================================
			
	public static final int BUFFER_RIGHT = Block.BLOCK_SIZE * 5;
	public static final int BUFFER_UP = Block.BLOCK_SIZE * 0;
	public static final int BUFFER_LEFT = Block.BLOCK_SIZE * 5;
	public static final int BUFFER_DOWN = Block.BLOCK_SIZE * 0;
	
	public static final int WIDTH_IN_BLOCKS = 10;
	public static final int HEIGHT_IN_BLOCKS = 15;
	
	public static final int WIDTH_IN_PIXELS = Block.BLOCK_SIZE * WIDTH_IN_BLOCKS + BUFFER_LEFT + BUFFER_RIGHT;
	public static final int HEIGHT_IN_PIXELS = Block.BLOCK_SIZE * HEIGHT_IN_BLOCKS + BUFFER_UP + BUFFER_DOWN;
	
	
	private static final boolean fullscreen = false;
	private static final int updatesPerSecond = 60;
	private static final boolean alwaysRender = false;
	
//======================================================================
//
//-------------------------------Methods--------------------------------
//
//======================================================================
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new Tetris("Tetris"));
		game.setDisplayMode(WIDTH_IN_PIXELS, HEIGHT_IN_PIXELS, fullscreen);
		game.setMaximumLogicUpdateInterval(updatesPerSecond);
		game.setAlwaysRender(alwaysRender);
		
		game.start();
	}
	
}
