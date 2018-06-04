package engine.entity;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] frames;
	
	private int currentFrame;
	private int timesPlayed;
	
	private int delay;
	private int delayCounter;
	
	public Animation(){
		timesPlayed = 0;
	}
	
	public void update(){
		if(delay == 0) return;
		
		delayCounter++;
		
		if(delayCounter == delay){
			currentFrame++;
			delayCounter = 0;
		}
		
		if(currentFrame == frames.length){
			currentFrame = 0;
			timesPlayed++;
		}
	}
	
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		timesPlayed = 0;
		currentFrame = 0;
	}
	
	public void setDelay(int delay){
		this.delay = delay;
	}
	
	public void setFrame(int frame){
		if(frame < frames.length){
			currentFrame = frame;
		}
	}
	
	public BufferedImage getImage(){ return frames[currentFrame]; }
	public int getCurrentFrame(){ return currentFrame; }
	public int getNumFrames(){ return frames.length; }
	public int getTimesPlayed(){ return timesPlayed; }
	public boolean hasPlayedOnce(){ return timesPlayed > 0; }
	public int getWidth(){ return frames[currentFrame].getWidth(); };
	public int getHeight(){ return frames[currentFrame].getHeight(); };
	
	
}
