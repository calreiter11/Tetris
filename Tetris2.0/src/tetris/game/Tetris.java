package tetris.game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Tetris extends StateBasedGame {
			
//======================================================================
//
//--------------------------------Fields--------------------------------
//
//======================================================================
	
	BasicGameState tetrisGameState;
	BasicGameState tetrisMenuState;
	BasicGameState tetrisGameOverState;
	
	public static final int MENU_STATE = 0;
	public static final int GAME_STATE = 1;
	public static final int GAME_OVER_STATE = 2;
	
//======================================================================
//
//-----------------------------Constructors-----------------------------
//
//======================================================================
	
	public Tetris(String name) throws SlickException {
		super(name);
		tetrisGameState = new TetrisGameState();
		tetrisMenuState = new TetrisMenuState();
		tetrisGameOverState = new TetrisGameOverState();
		
		//Music music = new Music("C:\\Users\\calreiter16\\Desktop\\AHA\\AHA 15-16\\AP Computer Science\\Tetris2.0\\Slamtris.wav");
		//music.loop();
	}
	
//======================================================================
//
//-------------------------------Methods--------------------------------
//
//======================================================================

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(tetrisMenuState);
		addState(tetrisGameState);
		addState(tetrisGameOverState);
	}

}
