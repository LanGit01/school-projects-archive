package ui.simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawingCanvas extends JPanel implements Runnable {

	public static int SCREEN_WIDTH = 600;
	public static int SCREEN_HEIGHT = 400;
	
	private int FPS = 60;
	private int targetTime = 1000/FPS;
	
	// Position
	private double x;
	
	// Timing
	private double timeStep;
	
	private Thread thread;
	private boolean running;
	private Graphics2D g;
	private BufferedImage buffer;
	
	// Specifics
	private int levelHeight;
	private int levelLabelWidth;
	
	private LinkedList<ProcessTimeBox> pTimeBoxes;
	
	private Font labelFont;
	
	private int levels;
	private int maxTime;
	
	public DrawingCanvas(){
		init();
	}
	
	private void init(){
		labelFont = new Font("Verdana", Font.BOLD, 12);
		
		pTimeBoxes = new LinkedList<ProcessTimeBox>();
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setMinimumSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setBackground(new Color(50, 50, 200, 20));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		levelHeight = 50;
		
		levelLabelWidth = 70;
		
		timeStep = 0.5 / FPS;
		
		
	}
	
	public void addProcessTimeBox(int level, int timeStart, int duration, Color color){
		// widthpertick = width / duration
		Insets insets = getBorder().getBorderInsets(this);
		ProcessTimeBox newPTimeBox = new ProcessTimeBox(color, (int)(1.0 / timeStep) * duration, timeStep);
		newPTimeBox.setPosition(insets.left + levelLabelWidth + (timeStart * ProcessTimeBox.BOX_DIMENSIONS.width), insets.top + (level * levelHeight));
		pTimeBoxes.add(newPTimeBox);
	}
	
	public void setNumLevels(int numLevels){
		this.levels = numLevels;
	}
	
	public void setMaxTime(int time){
		this.maxTime = time;
		}
	
	public void addNotify(){
		super.addNotify();
		startAnimation();
	}
	
	public void startAnimation(){
		setPreferredSize(new Dimension(40 + levelLabelWidth + (maxTime * ProcessTimeBox.BOX_DIMENSIONS.width), SCREEN_HEIGHT));
		revalidate();
		repaint();
		thread = new Thread(this);
		thread.start();
	}
	
	public void stopAnimation(){
		running = false;
		reset();
	}
	
	public void reset(){
		pTimeBoxes = new LinkedList<ProcessTimeBox>();
	}
	
	public void animationInit(){
		buffer = new BufferedImage(getPreferredSize().width, getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)buffer.getGraphics();
		x = levelLabelWidth + getBorder().getBorderInsets(this).left;
	}
	
	public void update(){
		x += (timeStep * ProcessTimeBox.BOX_DIMENSIONS.width);
		
		for(ProcessTimeBox ptb: pTimeBoxes){
			if(ptb.getX() < x){
				ptb.update();
			}
		}
	}
	
	public void render(Graphics2D g){
		Color prev = g.getColor();
		g.setColor(new Color(200, 200, 200));
		Dimension preferredSize = new Dimension(40 + levelLabelWidth + (maxTime * ProcessTimeBox.BOX_DIMENSIONS.width), SCREEN_HEIGHT);
		g.fillRect(0, 0, preferredSize.width, preferredSize.height);
		
		
		g.setColor(Color.BLACK);
		Insets insets = getBorder().getBorderInsets(this);
		
		// Draw level Labels
		FontMetrics metrics = g.getFontMetrics(labelFont);
		g.setFont(labelFont);		
		
		for(int i = 0; i < levels; i++){
			g.drawString("Level " + (i + 1), insets.left, insets.top + (i * levelHeight)  + ((levelHeight / 2 + metrics.getHeight())/ 2));
		}
		
		// Draw Time
		int timeY = insets.top + (levels * levelHeight) + levelHeight;
			
		for(int i = 0; i < maxTime; i++){
			g.drawString(Integer.toString(i), insets.left + levelLabelWidth + (i * ProcessTimeBox.BOX_DIMENSIONS.width) + 14, timeY);
		}
		
		g.setColor(prev);
		
		for(ProcessTimeBox ptb: pTimeBoxes){
			if(ptb.getX() < x){
				ptb.render(g);
			}
		}
	}
	
	public void renderToScreen(){
		Graphics g = getGraphics();
		g.drawImage(buffer, 0, 0, null);
	}
	
	public void run(){
		if(pTimeBoxes == null) return;
		animationInit();
		running = true;
		
		long start;
		long elapsed;
		long waittime;
		
		while(running){
			start = System.nanoTime();
			
			update();
			render(g);
			renderToScreen();
			
			elapsed = (System.nanoTime() - start) / 1000000;
			waittime = targetTime - elapsed;
			
			if(waittime < 5){
				waittime = 5;
			}
			
			
			try {
				Thread.sleep(waittime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
