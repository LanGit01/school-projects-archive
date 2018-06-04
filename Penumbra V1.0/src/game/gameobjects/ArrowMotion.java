package game.gameobjects;

import engine.entity.OrderedPair;

public interface ArrowMotion {

	public OrderedPair getVector();
	
	public void setAngle(double angle);
}
