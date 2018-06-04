package game.gameobjects.components;

import java.awt.Rectangle;

import engine.entity.Movable;
import engine.entity.OrderedPair;
import engine.entity.components.PhysicsComponent;
import engine.map.Tile;
import engine.map.TileMap;

public class BasePhysics implements PhysicsComponent {
	
	private Movable mObj;
	private TileMap tm;
	
	private int cwidth;
	private int cheight;
	private int hcwidth;
	private int hcheight;
	
	// Tile Collision
	private boolean tc_North;
	private boolean tc_South;
	private boolean tc_West;
	private boolean tc_East;
	
	// Gravity
	private boolean falling;
	private double gravity;
	private double terminalFallVelocity;
	
	public BasePhysics(Movable obj, TileMap tm){
		this.mObj = obj;
		this.tm = tm;
	}
	
	public void setFalling(boolean b){ falling = b;}
	public void setGravity(double g){ this.gravity = g; }
	public void setTerminalVelocity(double fallspeed){ this.terminalFallVelocity = fallspeed; }
	
	public boolean isFalling(){ return falling; }
	public double getGravity(){ return gravity; }
	public double getTerminalVelocity(){ return terminalFallVelocity; }
	
	private void checkHorizontalTileCollision(Rectangle cbox){
		int tileSize = tm.getTileSize();
		int start = cbox.y / tileSize;
		int end = (cbox.y + cbox.height - 1) / tileSize;
		int west_side = cbox.x / tileSize;
		int east_side = (cbox.x + cbox.width - 1) / tileSize;
		
		for(int r = start; r <= end; r++){
			if(tm.getTileType(r, west_side) == Tile.Type.BLOCKED){
				tc_West = true;
			}
			if(tm.getTileType(r, east_side) == Tile.Type.BLOCKED){
				tc_East = true;
			}
		}
	}
	
	
	private void checkVerticalTileCollision(Rectangle cbox){
		int tileSize = tm.getTileSize();
		int start = cbox.x / tileSize;
		int end = (cbox.x + cbox.width - 1) / tileSize;
		int topSide = cbox.y / tileSize;
		int bottomSide = (cbox.y + cbox.height - 1) / tileSize;
		
		for(int c = start; c <= end; c++){
			if(tm.getTileType(topSide, c) == Tile.Type.BLOCKED){
				tc_North = true;
			}
			if(tm.getTileType(bottomSide, c) == Tile.Type.BLOCKED){
				tc_South = true;
			}
		}
	}
	
	private void calculateRectObjTileCollisions(){
		Rectangle cbox = mObj.getBoundingBox();
		
		cwidth = cbox.width;
		cheight = cbox.height;
		hcwidth = cwidth / 2;
		hcheight = cheight / 2;
		// Reset collision to false
		tc_North = tc_South = tc_East = tc_West = false;
		
		// Get collision box and save current position
		int xtemp = cbox.x;
		int ytemp = cbox.y;
		
		OrderedPair position = mObj.getPosition();
		OrderedPair vector = mObj.getVector();
		
		// Check collision relative to dx
		cbox.x = (int)(position.x - hcwidth + vector.x);
		cbox.y = ytemp;		
		checkHorizontalTileCollision(cbox);
		
		// Check collision relative to dy
		cbox.x = xtemp;
		cbox.y = (int)(position.y - hcheight + vector.y);
		checkVerticalTileCollision(cbox);
	}
	
	private void handleTileCollisions(){
		int ts = tm.getTileSize();
		calculateRectObjTileCollisions();
		
		OrderedPair vector = mObj.getVector();
		double dx = vector.x;
		double dy = vector.y;
		
		OrderedPair nextPos = mObj.getNextPosition();
		double nextx = nextPos.x;
		double nexty = nextPos.y;
		
		
		// East and West tile collisions
		if(dx < 0 && tc_West){
			dx = 0;
			nextx = ((((int)nextx - (cwidth / 2)) / ts ) + 1) * ts + (cwidth / 2);
		}else
		if(dx > 0 && tc_East){
			dx = 0;
			nextx = ((((int)nextx + (cwidth / 2) - 1) / ts) * ts ) - (cwidth / 2);
		}
		
		// North and South tile collisions
		if(dy < 0 && tc_North){
			dy = 0;
			nexty = ((((int)nexty - (cheight / 2)) / ts) + 1) * ts + (cheight / 2);
		}else
		if(dy > 0 && tc_South){
			dy = 0;
			nexty = ((((int)nexty + (cheight / 2) - 1) / ts) * ts ) - (cheight / 2);
			falling = false; //moving downwards but is blocked by a tile
		}
		
		
		// Check if there is no ledge to step on below (added 10/15/14)
		if(!falling){
			Rectangle r = mObj.getBoundingBox();
			r.y = (int)(mObj.getPosition().y - hcheight + dy + 1);
			checkVerticalTileCollision(r);
			if(!tc_South){
				falling = true;
			}
		}
		
		mObj.setVector(dx, dy);
		mObj.setNextPosition(nextx, nexty);
	}
	
	private void applyGravity(){
		if(falling){
			double dy = mObj.getVector().y + gravity;
			if(dy > terminalFallVelocity){
				dy = terminalFallVelocity;
			}
			
			mObj.setVector(mObj.getVector().x, dy);
		}
	}
	
	public void update(){
		
		applyGravity();
		
		OrderedPair position = mObj.getPosition();
		OrderedPair vector = mObj.getVector();
		mObj.setNextPosition(position.x + vector.x, position.y + vector.y);
		
		
		calculateRectObjTileCollisions();
		handleTileCollisions();
	}
	
	public boolean hasNorthCollision(){ return tc_North; }
	public boolean hasSouthCollision(){ return tc_South; }
	public boolean hasWestCollision(){ return tc_West; }
	public boolean hasEastCollision(){ return tc_East; }
	
	
	
}
