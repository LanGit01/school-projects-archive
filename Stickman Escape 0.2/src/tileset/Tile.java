package tileset;

import java.awt.image.BufferedImage;

public class Tile {

	// Types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	private BufferedImage tileImage;
	private int type;
	
	public Tile(BufferedImage image, int type){
		this.tileImage = image;
		this.type = type;
	}
	
	public BufferedImage getImage(){
		return tileImage;
	}
	
	public int getType(){
		return type;
	}
}
