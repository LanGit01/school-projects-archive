package engine.gamestates;

import java.awt.Graphics2D;

public abstract class GameState {
	
	private GameStateManager gsm;
	
	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
	}
	
	public GameStateManager getGameStateManager(){
		return gsm;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render(Graphics2D g);
	public abstract void handleInput();
}
