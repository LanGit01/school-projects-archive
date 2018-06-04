package engine.entity;

import engine.entity.components.PhysicsComponent;
import engine.map.TileMap;

public abstract class MovingEntity extends Entity implements Movable {

	private double dx;
	private double dy;
	
	private double nextx;
	private double nexty;
	
	private TileMap tm;
	private PhysicsComponent physics;
	
	protected MovingEntity(TileMap tm) {
		this.tm = tm;
	}
	
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setNextPosition(double nextx, double nexty){
		this.nextx = nextx;
		this.nexty = nexty;
	}
	
	public OrderedPair getVector(){
		return new OrderedPair(dx, dy);
	}
	
	public OrderedPair getNextPosition(){
		return new OrderedPair(nextx, nexty);
	}
	
	public TileMap getTileMap(){
		return tm;
	}
	
	public void setPhysicsComponent(PhysicsComponent physicsComponent){
		this.physics = physicsComponent;
	}
	
	public PhysicsComponent getPhysicsComponent(){ 
		return physics; 
	}
}
