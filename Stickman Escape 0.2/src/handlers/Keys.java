package handlers;

import java.awt.event.KeyEvent;

public class Keys {
	
	private static final int NUM_KEYS = 4;
	
	// Keys
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	
	public static boolean[] keyStates = new boolean[NUM_KEYS];
	public static boolean[] prevKeyStates = new boolean[NUM_KEYS];
	
	public static void setKey(int k, boolean b){
		switch(k){
			case KeyEvent.VK_W: keyStates[UP] = b; break;
			case KeyEvent.VK_A: keyStates[LEFT] = b;break;
			case KeyEvent.VK_S: keyStates[DOWN] = b; break;
			case KeyEvent.VK_D: keyStates[RIGHT] = b; break;
			default: break;
		}
	}
	
	public static void update(){
		for(int i = 0; i < NUM_KEYS; i++){
			prevKeyStates[i] = keyStates[i];
		}
	}
	
	public static boolean isPressed(int k){
		return keyStates[k] && !prevKeyStates[k];
	}
}
