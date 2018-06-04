package tileset;

import javax.imageio.ImageIO;

import main.GamePanel;

import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Graphics2D;

// 16 x 12
public class TileMap {
	
	
	// Pixel position
	private double x;
	private double y;
	
	// Map position
	//private int mapX;
	//private int mapY;
	
	// Bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	// Map specifications
	private int[][] map;
	private int mapRows;
	private int mapCols;
	private int width;	// pixels
	private int height;
	private int tileSize;
	
	private Tile[][] tiles;
	private int numTilesAcross;
	
	// Drawing
	private int rowsToDraw;
	private int colsToDraw;
	private int rowOffset;
	private int colOffset;
	
	public TileMap(int tileSize){
		this.tileSize = tileSize;
		rowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		colsToDraw = GamePanel.WIDTH / tileSize + 2;
	}
	
	public void loadMap(String s){
		
		try {
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			mapRows = Integer.parseInt(br.readLine());
			mapCols = Integer.parseInt(br.readLine());
			
			// set dimension
			width = mapCols * tileSize;
			height = mapRows * tileSize;
			
			// set bounds
			xmin = ymin = 0;
			xmax = width - GamePanel.WIDTH;
			ymax = height - GamePanel.HEIGHT;
			
			map = new int[mapRows][mapCols];
			
			String delims = "\\s+";
			for(int row = 0; row < mapRows; row++){
				String line = br.readLine();
				String[] tokens = line.split(delims);
				
				for(int col = 0; col < mapCols; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void loadTiles(String s){
		try {
			BufferedImage tileset = ImageIO.read(getClass().getResourceAsStream(s));
			// specifications
			
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[2][numTilesAcross];
			
			BufferedImage subimg;
			for(int col = 0; col < numTilesAcross; col++){
				subimg = tileset.getSubimage(col * tileSize, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimg, Tile.NORMAL);
				subimg = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
				tiles[1][col] = new Tile(subimg, Tile.BLOCKED);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(){
		
	}
	
	public void draw(Graphics2D g){
		for(int row = rowOffset; row < rowOffset + rowsToDraw; row++){
			
			if(row >= mapRows) break;
			for(int col = colOffset; col < colOffset + colsToDraw; col++){

				g.drawRect(col * tileSize - (int)x, row * tileSize - (int)y, tileSize, tileSize);
				if(col >= mapCols) break;
				
				int val = map[row][col];
				if(val == 0) continue;
				
				int r = val / numTilesAcross;
				int c = val % numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(), (col * tileSize) - (int)x, (row * tileSize) - (int)y, null);
			}
		}
	}
	
	public void setCameraPosition(double x, double y){
		this.x = x;
		this.y = y;
		
		fixBounds();
		
		colOffset = (int)this.x / tileSize;
		rowOffset = (int)this.y / tileSize;
	}
	
	public void setBounds(int xmin, int ymin, int xmax, int ymax){
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}
	
	public void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public int getTileType(int r, int c){
		if(r >= mapRows || c >= mapCols) return -1;
		
		int val = map[r][c];
		if(val == 0) return 0;
		int row = val / numTilesAcross;
		int col = val % numTilesAcross;
		
		return tiles[row][col].getType();
	}
	
	public double getCameraX(){	return x;	}
	public double getCameraY(){	return y;	}
	public int getMapWidth(){ return width; }
	public int getMapHeight(){ return height; }
	public int getNumRows(){ return mapRows; }
	public int getNumCols(){ return mapCols; }
	public int getTileSize(){ return tileSize; }
	
	
}
