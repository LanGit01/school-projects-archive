package engine.handlers;

import java.awt.event.KeyEvent;

public class KeyHandler {
	
	// Num of keys
	private static final int NUM_KEYS = 5;
	
	// Key Macros
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int X = 4;
	
	public static boolean[] keyStates = new boolean[NUM_KEYS];
	public static boolean[] prevKeyStates = new boolean[NUM_KEYS];
	
	/**
	 * Sets the corresponding keystate if pressed or not
	 * 
	 * @param k keycode of the key
	 * @param b boolean value, pressed if true, otherwise false
	 */
	public static void setKey(int k, boolean b){
		switch(k){
			case KeyEvent.VK_UP: keyStates[UP] = b; break;
			case KeyEvent.VK_LEFT: keyStates[LEFT] = b; break;
			case KeyEvent.VK_DOWN: keyStates[DOWN] = b; break;
			case KeyEvent.VK_RIGHT: keyStates[RIGHT] = b; break;
			case KeyEvent.VK_X: keyStates[X] = b; break;
			default: break;
		}
	}
	
	/**
	 * Updates the keystates
	 */
	public static void update(){
		for(int i = 0; i < NUM_KEYS; i++){
			prevKeyStates[i] = keyStates[i];
		}
	}
	
	/**
	 * 
	 * @param k
	 * @return
	 */
	public static boolean isPressed(int k){
		return keyStates[k] && !prevKeyStates[k];
	}
	
	public static boolean isReleased(int k){
		return !keyStates[k] && prevKeyStates[k];
	}
}
