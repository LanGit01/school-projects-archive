package game;

import java.awt.image.BufferedImage;
import engine.misc.SpriteLoader;

public class ResourceLoader {

	public static BufferedImage[][] Arrow = SpriteLoader.load("/Sprites/Others/arrow.gif", 26, 8);
	public static BufferedImage[][] FieryArrow = SpriteLoader.load("/Sprites/Others/fieryarrow2.gif", 50, 15);
	
}
