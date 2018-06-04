package entity;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] frames;
	private int currentFrame;
	private int numFrames;
	
	private int count;
	private int delay;
	
	private int timesPlayed;
	
	
	
	public Animation(){
		timesPlayed = 0;
	}
	
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		currentFrame = 0;
		count = 0;
		timesPlayed = 0;
		numFrames = frames.length;
	}
	
	public void setDelay(int delay){
		this.delay = delay;
	}
	
	public void setFrame(int frame){
		currentFrame = frame;
	}
	
	public void setNumFrames(int i){
		numFrames = i;
	}
	
	public void update(){
		if(delay == -1) return;
		
		// Per frame
		count++;
		if(count == delay){
			currentFrame++;
			count = 0;
		}
		if(currentFrame == numFrames){
			currentFrame = 0;
			timesPlayed++;
		}
	}
	
	public int getFrame() { return currentFrame; }
	public int getCount() { return count; }
	public BufferedImage getImage() { return frames[currentFrame]; }
	public boolean hasPlayedOnce() { return timesPlayed > 0; }
	public boolean hasPlayed(int i) { return timesPlayed == i; }
}
