package engine.map;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.IOException;

/**
 * A set of tiles, duh.
 * 
 * <p>The tiles are represented by using only one number, and not the (row, column) representation. Thus, the tile atlas
 * provided will be divided like:</p>
 * [ 0, 1, 2, 3 ]<br>
 * [ 4, 5, 6, 7 ]<br>
 * @author ace
 *
 */
public class TileSet {

	private Tile[][] tileset;
	private int tilesPerRow;
	
	public TileSet(String s, int tilesize){
		loadTiles(s, tilesize);
	}
	
	private void loadTiles(String s, int tilesize){
		try{
			BufferedImage tileAtlas = ImageIO.read(getClass().getResourceAsStream(s));
			tilesPerRow = tileAtlas.getWidth() / tilesize;
			
			tileset = new Tile[2][tilesPerRow];
			for(int i = 0; i < tilesPerRow; i++){
				tileset[0][i] = new Tile(tileAtlas.getSubimage(i * tilesize, 0, tilesize, tilesize), Tile.Type.PASSABLE);
				tileset[1][i] = new Tile(tileAtlas.getSubimage(i * tilesize, tilesize, tilesize, tilesize), Tile.Type.BLOCKED);
			}
		}catch(IOException e){
			e.printStackTrace();
			System.err.println("Error in loading tileset graphics");
		}
	}
	
	public Tile getTile(int n){
		if(n > tilesPerRow * 2 - 1) return null;
		
		return tileset[n / tilesPerRow][n % tilesPerRow];
	}
	
}
