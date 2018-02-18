package tetris.game;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TetrisGameOverState extends BasicGameState{
	private String gameOver, gameOver2;
	private TrueTypeFont awtFont;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		gameOver = "Game Over";
		gameOver2 = "Press (ESC) to return to the menu.";
		awtFont = new TrueTypeFont( new Font("Times New Roman", Font.BOLD, 25), true);
	}
	
	@Override
	public void update(GameContainer c, StateBasedGame sbg, int delta) throws SlickException {
				
		if(c.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			c.getInput().clearKeyPressedRecord();
			sbg.enterState(Tetris.MENU_STATE);
		}
	}

	@Override
	public void render(GameContainer c, StateBasedGame arg1, Graphics g) throws SlickException {
		awtFont.drawString(c.getWidth()/2 - gameOver.length() * 7, c.getHeight()/2 - 50, gameOver);
		awtFont.drawString(c.getWidth()/2 - gameOver.length() * 20, c.getHeight()/2, gameOver2);
	}
	
	@Override
	public int getID() {
		return Tetris.GAME_OVER_STATE;
	}

}