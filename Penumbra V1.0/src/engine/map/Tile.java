package engine.map;

import java.awt.image.BufferedImage;


/**
 * Tile data storage class
 * 
 * @author ace
 *
 */
public class Tile {

	// Tile Types
	public enum Type{
		PASSABLE, BLOCKED, UNDEFINED;
	}
	
	private BufferedImage image;
	private Type type;
	
	public Tile(BufferedImage tileImage, Type type){
		this.image = tileImage;
		this.type = type;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public Type getType(){
		return type;
	}
	
	
}
