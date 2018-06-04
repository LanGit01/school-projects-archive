package engine.misc;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteLoader {
	
	public static BufferedImage[][] load(String s, int spritewidth, int spriteheight){
		try{
			
			BufferedImage spritesheet = ImageIO.read(SpriteLoader.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / spritewidth;
			int height = spritesheet.getHeight() / spriteheight;
			
			BufferedImage[][] sprites = new BufferedImage[height][width];
			for(int r = 0; r < height; r++){
				for(int c = 0; c < width; c++){
					sprites[r][c] = spritesheet.getSubimage(c * spritewidth, r * spritewidth, spritewidth, spriteheight);
				}
			}
			return sprites;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<BufferedImage[]> load(String s, int[] numFrames, int[] frameWidths, int[] frameHeight){
		try{
			BufferedImage textureAtlas = ImageIO.read(SpriteLoader.class.getResourceAsStream(s));
			
			ArrayList<BufferedImage[]> spritebatchlist = new ArrayList<BufferedImage[]>();
			
			// Get sprites
			int cumulativeHeight = 0;;
			
			for(int i = 0; i < numFrames.length; i++){
				BufferedImage[] batch = new BufferedImage[numFrames[i]];
				
				// Get each sprite, by row
				int width = frameWidths[i];
				
				for(int j = 0; j < numFrames[i]; j++){
					batch[j] = textureAtlas.getSubimage(j * width, cumulativeHeight, width, frameHeight[i]);
				}
				
				spritebatchlist.add(batch);
				cumulativeHeight += frameHeight[i];
			}
			
			return spritebatchlist;
			
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
}
