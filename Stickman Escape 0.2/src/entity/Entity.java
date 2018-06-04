package entity;

import java.awt.Color;
import java.awt.Rectangle;
import tileset.*;

public class Entity {
	
	// Position
	protected double x;
	protected double y;
	
	// Dimensions
	protected int width;
	protected int height;
	
	// Collision box
	protected int cwidth;
	protected int cheight;
	protected int hcwidth;
	protected int hcheight;
	
	// Vectors
	protected double dx;
	protected double dy;
	
	// Movement
	protected boolean left;
	protected boolean right;
	//protected boolean jumping;
	//protected boolean falling;
	protected double nextx;
	protected double nexty;
	
	// Movement Attributes
	protected double moveSpeed;
	protected double maxMoveSpeed;
	//protected double jumpSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	
	// Tilemap
	protected TileMap tilemap;
	protected double xmap;
	protected double ymap;
	
	protected boolean facingRight;
	protected boolean falling;
	
	// Animation
	protected Animation animation;
	
	// Collision
	protected boolean leftCollision;
	protected boolean rightCollision;
	protected boolean topCollision;
	protected boolean bottomCollision;
	
	
	protected Entity(TileMap tm){
		this.tilemap = tm;
		animation = new Animation();
	}
	
		
	public Rectangle getCollisionBox(){
		return new Rectangle((int)(x - hcwidth), (int)(y - hcheight), cwidth, cheight);
	}
		
	public boolean checkHorizontalTileCollisions(Rectangle cbox, int x){
		// Tiles to check
		int tileSize = tilemap.getTileSize();				// box slightly smaller (1px)
		int start = (cbox.y + 1 ) / tileSize;				// without + 1, some error in sliding
		int end = (cbox.y + cbox.height - 2) / tileSize;	// with only -1, some error in sliding
		int tileCol = x / tileSize;
			
		// Loop through the tiles
		for(int row = start; row <= end; row++){
			// Get Tile rectangle
			Rectangle tileRect = new Rectangle(tileCol * tileSize, row * tileSize, tileSize, tileSize);
			if(tilemap.getTileType(row, tileCol) == Tile.BLOCKED && checkRectangleCollision(cbox, tileRect)){
				return true;
			}
		}
			
		return false;
	}
		
	public boolean checkVerticalTileCollisions(Rectangle cbox, int y){
		// Tiles to check
		int tileSize = tilemap.getTileSize();
		int start = (cbox.x + 1) / tileSize;
		int end = (cbox.x + cbox.width - 2) / tileSize;
		int tileRow = y / tileSize;
			
			// Loop through the tiles
		for(int col = start; col <= end; col++){
			// Get Tile rectangle
			Rectangle tileRect = new Rectangle(col * tileSize, tileRow * tileSize, tileSize, tileSize);
			if(tilemap.getTileType(tileRow, col) == Tile.BLOCKED && checkRectangleCollision(cbox, tileRect)){
				return true;
			}
		}
			
		return false;
	}
		
	public void handleTileCollisions(){
		// if x is 0, width is 40
		// x + width will fail because it will return 40 -> the x of the next tile
		// the right side x is really x + width - 1
		int tileSize = tilemap.getTileSize();
		Rectangle cbox = getCollisionBox();
		cbox.x = (int)(cbox.x + dx);
		cbox.y = (int)(cbox.y + dy);
			
		// Check Horizontally
		if(dx < 0){
			if(checkHorizontalTileCollisions(cbox, cbox.x)){
				dx = 0;
				nextx = (int)(((x - hcwidth) / tileSize) * tileSize) + hcwidth;
			}
		}else
		if(dx > 0){
			if(checkHorizontalTileCollisions(cbox, cbox.x + cbox.width)){
				dx = 0;
				nextx = (int)(((x + hcwidth) / tileSize) * tileSize) - hcwidth;
				//System.out.println("X-COLLISON");
			}
		}
			
		// Check Vertically
		if(dy < 0){
			if(checkVerticalTileCollisions(cbox, cbox.y)){
				dy = 0;
				nexty = (int)(((y - hcheight) / tileSize) * tileSize) + hcheight;
			}
		}else
		if(dy > 0){
			if(checkVerticalTileCollisions(cbox, cbox.y + cbox.height)){
				dy = 0;
				falling = false;
				nexty = (int)(((y + hcheight) / tileSize) * tileSize) - hcheight;
				//System.out.println("Y-COLLISON");
			}
		}
		
		
		//Check if falling
		if(!falling && !checkVerticalTileCollisions(cbox, cbox.y + cbox.height + 1)){
			falling = true;
		}
			
	}
	
	public boolean checkRectangleCollision(Rectangle cbox1, Rectangle cbox2){
		
		return !(cbox2.x > cbox1.x + cbox1.width - 1 || cbox2.y > cbox1.y + cbox1.height - 1 || 
				 cbox1.x > cbox2.x + cbox2.width - 1 || cbox1.y > cbox2.y + cbox2.height - 1);
		
		//return true;
	}
	
	public void checkHorizontalTileCollisions(Rectangle cbox){
		int tileSize = tilemap.getTileSize();
		int start = cbox.y / tileSize;
		int end = (cbox.y + cbox.height - 1) / tileSize;
		int leftSide = cbox.x / tileSize;
		int rightSide = (cbox.x + cbox.width - 1) / tileSize;
		
		for(int r = start; r <= end; r++){
			if(tilemap.getTileType(r, leftSide) == Tile.BLOCKED){
				leftCollision = true;
			}
			if(tilemap.getTileType(r, rightSide) == Tile.BLOCKED){
				rightCollision = true;
			}
			
		}
	}
	
	public void checkVerticalTileCollisions(Rectangle cbox){
		int tileSize = tilemap.getTileSize();
		int start = cbox.x / tileSize;
		int end = (cbox.x + cbox.width - 1) / tileSize;
		int topSide = cbox.y / tileSize;
		int bottomSide = (cbox.y + cbox.height - 1) / tileSize;
		
		for(int c = start; c <= end; c++){
			if(tilemap.getTileType(topSide, c) == Tile.BLOCKED){
				topCollision = true;
			}
			if(tilemap.getTileType(bottomSide, c) == Tile.BLOCKED){
				bottomCollision = true;
			}
		}
	}
	
	
	/**
	 * checkTileCollisions v1.1
	 */
	public void checkTileCollisions(){
		falling = true;
		leftCollision = rightCollision = topCollision = bottomCollision = false;
		int tileSize = tilemap.getTileSize();
		Rectangle cbox = getCollisionBox();
		
		
		int xtemp = cbox.x;
		int ytemp = cbox.y;
		
		cbox.x = (int)(x - hcwidth + dx);
		cbox.y = ytemp;
		checkHorizontalTileCollisions(cbox);
		if(dx < 0 && leftCollision){
			dx = 0;
			nextx = ((cbox.x / tileSize) + 1) * tileSize + hcwidth;
		}
		if(dx > 0 && rightCollision){
			dx = 0;
			nextx = (((cbox.x + cbox.width - 1) / tileSize) * tileSize) - hcwidth;
	
		}
		
		cbox.x = xtemp;
		cbox.y = (int)(y - hcheight + dy);
		checkVerticalTileCollisions(cbox);
		
		if(dy < 0 && topCollision){
			dy = 0;
			nexty = (((cbox.y / tileSize) + 1 ) * tileSize) + hcheight;
		}
		if(dy > 0 && bottomCollision){
			dy = 0;
			nexty = (((cbox.y + cbox.height - 1) / tileSize) * tileSize) - hcheight;
			falling = false;
		}
		
		
		
		/*if(!falling){
			cbox.y += 0;
			bottomCollision = false;
			checkVerticalTileCollisions(cbox);
			if(!bottomCollision){
				falling = true;
			}
		}*/
	}
		
	public void updateVectors(){
		// Movement vectors
		
	}
	
		
	public void update(){
		
	}
	
	public void draw(java.awt.Graphics2D g){
		setMapPosition();
		if(facingRight){
			g.setColor(Color.white);
			g.fillRect((int)(x - xmap - (width / 2)), (int)(y - ymap - (height / 2)), width, height);
		}
	}
		
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setMapPosition(){
		xmap = tilemap.getCameraX();
		ymap = tilemap.getCameraY();
	}
	
	public double getx(){ return x; }
	public double gety(){ return y; }
	
}
