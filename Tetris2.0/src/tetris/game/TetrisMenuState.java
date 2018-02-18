package tetris.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class TetrisMenuState extends BasicGameState{
	
	private Image img;
	private String newGame, quit;
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		//img = new Image("C:\\Users\\calreiter16\\Desktop\\AHA\\AHA 15-16\\AP Computer Science\\Tetris2.0\\jdl.png");
		img = new Image("D:\\Users\\Caleb\\OneDrive - UW-Madison\\eclipse-workspace\\Tetris2.0\\jdl.png");
		newGame = "NEW GAME (ENTER)";
		quit = "QUIT (ESC)";
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
			container.getInput().clearKeyPressedRecord();
			game.enterState(Tetris.GAME_STATE);
		}
		
		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
	}

	@Override
	public void render(GameContainer c, StateBasedGame arg1, Graphics g) throws SlickException {
		img.draw(c.getWidth()/5, c.getHeight()/5, c.getWidth()/5, c.getHeight()/5);
		g.setColor(Color.white);
		//change to be font - text selection capabilites
		g.drawString(newGame, c.getWidth()/2, c.getHeight()/2);
		g.drawString(quit, c.getWidth()/2, c.getHeight()/2+50);
	}
	
	@Override
	public int getID() {
		return Tetris.MENU_STATE;
	}
	
}