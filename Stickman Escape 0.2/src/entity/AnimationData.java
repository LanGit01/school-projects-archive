package entity;

import java.awt.image.BufferedImage;

public class AnimationData {
	private BufferedImage[] sprites;
	
	private int frameWidth;
	private int frameHeight;
	private int frameDelay;
	
	public AnimationData(BufferedImage[] sprites, int frameWidth, int frameHeight, int frameDelay){
		this.sprites = sprites;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.frameDelay = frameDelay;
	}
	
	public BufferedImage[] getFrames(){
		return sprites;
	}
	
	public int getFrameWidth(){ return frameWidth; }
	public int getFrameHeight(){ return frameHeight; }
	public int getFrameDelay(){ return frameDelay; }
}
