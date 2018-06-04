package game.gameobjects;

import engine.entity.OrderedPair;

public interface CharacterMotion {
	
	public double getMoveAcceleration();
	public double getMoveDecceleration();
	public double getStartingJumpSpeed();
	public double getMaxMoveSpeed();
	

	public void setVector(double dx, double dy);
	public OrderedPair getVector();
	
	public boolean isMovingLeft();
	public boolean isMovingRight();
	public boolean isJumping();
	public boolean isFalling();
	
	public void setJumping(boolean b);
	public void setFalling(boolean b);
}
