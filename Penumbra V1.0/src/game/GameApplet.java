package game;

import engine.handlers.KeyHandler;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JApplet;


/**
 * The container for the game
 * 
 * @author ace
 *
 */
@SuppressWarnings("serial")
public class GameApplet extends JApplet implements KeyListener{
	
	private GameCanvas canvas;
	
	// Called automatically to initialize the applet
	public void init(){
		try {
			new KeyRepeatSuppressor().install();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		addKeyListener(this);
		setSize(GameCanvas.WIDTH, GameCanvas.HEIGHT);
		canvas = new GameCanvas();
		getContentPane().add(canvas);
		setFocusable(true);
		requestFocus();
	}
	
	public void destroy(){
		canvas.stopGame();
	}
	
	public void keyTyped(KeyEvent e){}
	
	/**
	 * Sets the corresponding key in KeyHandler as true (pressed)
	 */
	public void keyPressed(KeyEvent e){
		KeyHandler.setKey(e.getKeyCode(), true);
	}
	
	/**
	 * Sets the corresponding key in KeyHandler as false (released/not pressed)
	 */
	public void keyReleased(KeyEvent e){
		KeyHandler.setKey(e.getKeyCode(), false);
	}
	
	
	
}
