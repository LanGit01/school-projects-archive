package engine.gamestates;

import java.util.HashMap;
import java.awt.Graphics2D;

/**
 * This class is created for the purpose of separation of the engine and the game itself.
 * 
 * @author ace
 *
 */
public class GameStateManager {

	private HashMap<Integer, GameState> gamestates;
	private GameState currentState;
	
	public GameStateManager(){
		gamestates = new HashMap<Integer, GameState>();
		currentState = null;
	}
	
	/**
	 * Checks if currently there is a state with the argument <code>id</code>
	 * 
	 * @param id the id to check
	 * @return <b>true</b> if there is a state with the specified id, <b>false</b> otherwise
	 */
	public boolean hasState(int id){
		return gamestates.containsKey(id);
	}
	
	/**
	 * Adds a state to the GameStateManager, with the specified <code>id</code>
	 * 
	 * @param id
	 * @param gamestate
	 */
	public void addState(int id, GameState gamestate){
		//if(hasState(id)) return;
		
		gamestates.put(id, gamestate);
	}
	
	/**
	 * Removes the state specified by <code>id</code>
	 * 
	 * @param id the id of the state to remove
	 */
	public void removeState(int id){
		if(gamestates.containsKey(id)){
			gamestates.remove(id);
		}
	}
	
	/**
	 * Changes the current state to the state with the ID <code>id</code>
	 * @param id the state to change to
	 */
	public void changeState(int id){
		if(gamestates.containsKey(id)){
			loadState(id);
		}
	}
	
	/**
	 * Changes the current state without reinitializing.
	 * @param id the state to return to
	 */
	public void returnToState(int id){
		if(gamestates.containsKey(id)){
			currentState = gamestates.get(id);
		}
	}
	
	private void loadState(int id){
		currentState = gamestates.get(id);
		currentState.init();
	}
	
	/**
	 * Reloads the current states
	 */
	public void reloadState(){
		if(currentState != null){
			currentState.init();
		}
	}
	
	public void update(){
		if(currentState != null){
			currentState.update();
		}
	}
	
	public void render(Graphics2D g){
		if(currentState != null){
			currentState.render(g);
		}
	}
}
