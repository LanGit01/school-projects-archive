package gamestates;

public class GameStateManager {
	
	private static final int NUM_STATES = 2;
	
	// GameStates
	public static final int MENU_STATE = 0;
	public static final int LEVEL1 = 1;
	
	private GameState[] gameStates;	
	private int currentState;
	
	public GameStateManager(){
		gameStates = new GameState[NUM_STATES];
		currentState = LEVEL1;
		loadState(currentState);
	}
	
	public void loadState(int state){
		switch(state){
			case MENU_STATE: gameStates[state] = null; break;
			case LEVEL1: gameStates[state] = new Level1(this); break;
		}
	}
	
	public void unloadState(){
		gameStates[currentState] = null;
	}
	
	public void setState(int state){
		unloadState();
		currentState = state;
		loadState(currentState);
	}
	
	public void changeState(){
		
	}
	
	// ================== Game Loop Functions ================== //
	public void update(){
		gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g){
		gameStates[currentState].draw(g);
	}
}
