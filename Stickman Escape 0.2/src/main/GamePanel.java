package main;

import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.event.*;
import gamestates.*;
import handlers.Keys;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{

	// Screen Attributes
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	// Game thread
	private Thread thread;
	private int FPS = 60;
	private long frameWait = 1000 / FPS;
	private boolean running;
	
	// Rendering
	private BufferedImage image;
	private Graphics2D g;
	
	// Game Level
	private GameStateManager gsm;
	
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		thread = null;
	}
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	public void run(){
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running){
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			wait = frameWait - elapsed;
			
			if(wait < 1){
				wait = 5;
			}
			
			try{
				Thread.sleep(wait);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	private void init(){
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();
		gsm = new GameStateManager();
		running = true;
	}
	
	private void update(){
		gsm.update();
		Keys.update();
	}
	
	private void draw(){
		gsm.draw(g);
	}
	
	private void drawToScreen(){
		if(this.getGraphics() != null) {
			Graphics g2 = getGraphics();
			g2.drawImage(image, 0, 0, null);
			g2.dispose();
		}		
	}
	
	public void keyTyped(KeyEvent k){}
	
	public void keyPressed(KeyEvent k){
		Keys.setKey(k.getKeyCode(), true);
	}
	
	public void keyReleased(KeyEvent k){
		Keys.setKey(k.getKeyCode(), false);
	}
}	
