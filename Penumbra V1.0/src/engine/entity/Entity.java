package engine.entity;

import java.awt.Rectangle;


public abstract class Entity {
	
	//private double gravity;
	
	private double x;
	private double y;
	
	private int cwidth;
	private int cheight;
	
	
	/*
	public void setGravity(double gravity){
		this.gravity = gravity;
	}
	
	public double getGravity(){
		return gravity;
	}*/
	
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX(){ return x; }
	public double getY(){ return y; }
	
	public OrderedPair getPosition(){
		return new OrderedPair(x, y);
	}
	
	public int getCollisionWidth(){ return cwidth; }
	public int getCollisionHeight(){ return cheight; }
	
	public Rectangle getBoundingBox(){
		return new Rectangle((int)x - (cwidth / 2), (int)y - (cheight / 2), cwidth, cheight);
	}
	
	public void setBoundingBoxDimensions(int width, int height){
		this.cwidth = width;
		this.cheight = height;
	}
	
	
	
}
