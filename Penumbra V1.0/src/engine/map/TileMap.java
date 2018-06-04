package engine.map;

import java.awt.geom.Rectangle2D;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.map.Tile.Type;

/**
 * A class for handling tilemap data and rendering. The methods <code>setCamera</code> and <code>setTileset</code>
 * must be called at least once and supplied a <code>Camera</code> and a <code>TileSet</code>
 * @author ace
 *
 */
public class TileMap {
	
	// Dimensions
	private int width;
	private int height;
	
	// Map specifics
	private int[][] map;
	private int map_rows;
	private int map_cols;
	private int tilesize;
	
	// Drawing
	private TileSet tileset;
	private int numRowsOnScreen;
	private int numColsOnScreen;
	
	// Camera
	private Camera camera;
	
	// FOR DEBUGGING PURPOSES ONLY //
	private boolean debug;
	//=============================//
	
	public TileMap(int tilesize, TileSet tileset){
		this.tilesize = tilesize;
		this.tileset = tileset;
	}
	
	/**
	 * Sets the current camera
	 * 
	 * @param camera
	 */
	public void setCamera(Camera camera){
		this.camera = camera;
		Rectangle2D viewbox = camera.getViewBox();
		numRowsOnScreen = (int)viewbox.getHeight() / tilesize + 2;
		numColsOnScreen = (int)viewbox.getWidth() / tilesize + 2;
	}
	
	public void setTileset(TileSet tileset){
		this.tileset = tileset;
	}
	
	/**
	 * Loads a map from a file. This method should be updated and cleaned.
	 * @param s
	 */
	public void loadMap(String s){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(s)));
			
			// Read map dimensions
			map_rows = Integer.parseInt(br.readLine());
			map_cols = Integer.parseInt(br.readLine());
			
			width = map_cols * tilesize;
			height = map_rows * tilesize;
			
			map = new int[map_rows][map_cols];
			String delims = "\\s+";
			for(int row = 0; row < map_rows; row++){
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < map_cols; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public Type getTileType(int r, int c){
		if(r < 0 || r > map_rows-1 || c < 0 || c > map_cols-1) return Type.UNDEFINED;
		int value = map[r][c];
		return tileset.getTile(value).getType();
	}
	
	public void render(Graphics2D g){
		int rowOffset = (int)camera.getY() / tilesize;
		int colOffset = (int)camera.getX() / tilesize;

		for(int row = rowOffset; row < rowOffset + numRowsOnScreen; row++){
			if(row >= map_rows) break; // if past the number of rows of map
		
			for(int col = colOffset; col < colOffset + numColsOnScreen; col++){
				if(col >= map_cols) break;	// if past the number of cols on the map
				
				// Draw Grid
				g.drawRect(col * tilesize - (int)camera.getX(), row * tilesize - (int)camera.getY(), tilesize, tilesize);
				
				
				// Identify and draw the type of tile, if tileVal == 0, draw nothing
				int tileVal = map[row][col];
				if(tileVal == 0) continue;
	
				
				if(debug){
					// FOR DEBUGGING PURPOSES ONLY
					Color old = g.getColor();
					g.setColor(Color.BLUE);
					g.fillRect((col * tilesize) - (int)camera.getX(), (row * tilesize) - (int)camera.getY(), tilesize, tilesize);
					g.setColor(old);
				}else{
					g.drawImage(tileset.getTile(tileVal - 1).getImage(), (col * tilesize) - (int)camera.getX(), (row * tilesize) - (int)camera.getY(), null);
				}
			}
			
		}
	}
	
	//================ Simple Getters and Setters ==================//
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public int getTileSize(){ return tilesize; }
	
	public Camera getCamera(){ return camera; }

	
	// ==== FOR DEBUGGING PURPOSES ONLY ==== //
	public void setDebug(boolean b){
		debug = b;
	}
}
