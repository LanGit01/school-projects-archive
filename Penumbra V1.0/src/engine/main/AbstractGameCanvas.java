package engine.main;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

/**
 * An abstract class that handles the game loop. Extend this class and set the size of canvas, and
 * override the methods <code>init, update, render</code>
 * 
 * @author ace
 * @see main.AbstractGameCanvas#init init
 * @see main.AbstractGameCanvas#update update
 * @see main.AbstractGameCanvas#render cebrender
 */
@SuppressWarnings("serial")
public abstract class AbstractGameCanvas extends Canvas implements Runnable{

	// Screen Dimensions
	
	
	// Game Thread
	private Thread thread;
	private int FPS = 60;
	private int targetTime = 1000 / FPS;
	private boolean running;
	
	// Rendering
	private BufferStrategy bs;
	private Graphics2D bsgraphics;
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	/**
	 * Main game loop
	 */
	public void run(){
		preInit();
		init();
		
		// Timer
		long start;
		long elapsed;
		long wait;
		
		// Frame Counter timers
		long fc = System.nanoTime() / 1000000;
		int frames = 0;
		
		// Main Game Loop
		while(running){
			start = System.nanoTime();
			
			try{
				
				// Main Game Loop processes
				update();
				
				do{
					bsgraphics = (Graphics2D)bs.getDrawGraphics();
					render(bsgraphics);
					renderToScreen();
				}while(bs.contentsLost());
				
				Toolkit.getDefaultToolkit().sync();
				
			}finally{
				bsgraphics.dispose();	
			}
			
			elapsed = (System.nanoTime() - start) / 1000000;	// convert to milliseconds
			wait = targetTime - elapsed;
			
			if(wait < 1){
				wait = 1;
			}
			
			// Frame Counter
			frames++;
			if((System.nanoTime() / 1000000) > fc + 1000){
				System.out.println("FPS: " + frames);
				frames = 0;
				fc = System.nanoTime() / 1000000;
			}
			
			// Syncing
			try{
				Thread.sleep(wait);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Called before running the game loop. This method is automatically called in the main loop.
	 * There is no need to call, just override it.
	 */
	protected abstract void init();
	/**
	 * For updating game logic. This method is automatically called in the main loop.
	 * There is no need to call, just override it.
	 */
	protected abstract void update();
	/**
	 * Render to a back buffer. This method is automatically called in the main loop.
	 * There is no need to call, just override it.
	 * 
	 * @param g the Graphics2D object of the back buffer
	 */
	protected abstract void render(Graphics2D g);
	
	public void renderToScreen(){
		if(running){
			bs.show();
		}
	}
	
	private void preInit(){
		createBufferStrategy(2);
		bs = getBufferStrategy();
		running = true;
	}
	
	public void setTargetFPS(int FPS){
		this.FPS = FPS;
		targetTime = 1000 / FPS;
	}
	
	public void stopGame(){
		running = false;
	}
	
}
