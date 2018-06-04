package game.gameobjects.components;

import java.awt.Rectangle;

import engine.entity.OrderedPair;
import engine.map.Tile;
import engine.map.TileMap;
import game.gameobjects.props.Arrow;

public class ArrowPhysics {
	
	private double gravity;
	private double terminalVelocity;
	
	private double penetrationPower;
	
	private Arrow am;
	
	private TileMap tm;
	
	public ArrowPhysics(Arrow am, TileMap tm){
		this.am = am;
		this.tm = tm;
		penetrationPower = 0;
	}
	
	public void setGravity(double gravity){ this.gravity = gravity; }
	public void setTerminalVelocity(double tv){ this.terminalVelocity = tv; }
	
	public double getGravity(){ return gravity; }
	public double getTerminalVelocity(){ return terminalVelocity; }
	
	private void applyGravity(){
		double dy = am.getVector().y + gravity;
		if(dy > terminalVelocity){
			dy = terminalVelocity;
		}
		
		
		am.setVector(am.getVector().x, dy);
	}
	
	private void resolveTileCollisions(){
		Rectangle cbox = am.getBoundingBox();
		
		int ts = tm.getTileSize();
		int startrow = cbox.y / ts;
		int startcol = cbox.x / ts;
		int endrow = (cbox.y + cbox.height - 1) / ts;
		int endcol = (cbox.x + cbox.width - 1) /ts;
		
		
		OrderedPair position = am.getPosition();
		OrderedPair vector = am.getVector();

		for(int row = startrow; row <= endrow; row++){
			for(int col = startcol; col <= endcol; col++){
				
				if(tm.getTileType(row, col) == Tile.Type.BLOCKED){
					//if(tm.getTileType((int)(position.x + vector.x) / ts, (int)(position.y + vector.y) / ts) == Tile.Type.BLOCKED){
						am.setNextPosition(position.x + (vector.x * penetrationPower), position.y + (vector.y * penetrationPower));
						am.setVector(0, 0);
						am.setLive(false);
						
						break;
					//}
				}
			}
		}
		
		OrderedPair nextPosition = am.getNextPosition();
		am.setPosition(nextPosition.x, nextPosition.y);
	}
	
	public void update(){
		if(am.isLive()){
			applyGravity();
			
			OrderedPair position = am.getPosition();
			OrderedPair vector = am.getVector();
			am.setNextPosition(position.x + vector.x, position.y + vector.y);
			am.setAngle(Math.atan2(vector.y, vector.x));
			
			resolveTileCollisions();
		}
	}
}
