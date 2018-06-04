package gamestates;

public abstract class GameState {

	protected GameStateManager gsm;
	
	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void handleInput();
}
