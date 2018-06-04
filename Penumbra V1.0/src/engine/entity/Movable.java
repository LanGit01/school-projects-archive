package engine.entity;

import java.awt.Rectangle;

public interface Movable {
	public void setPosition(double x, double y);
	public void setVector(double dx, double dy);
	public void setNextPosition(double nextx, double nexty);
	
	public OrderedPair getPosition();
	public OrderedPair getVector();
	public OrderedPair getNextPosition();
	
	public Rectangle getBoundingBox();
}
