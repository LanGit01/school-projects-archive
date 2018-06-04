package engine.entity.components;

/**
 * Handles the body of the entity
 * 
 * @author ace
 *
 */
public interface PhysicsComponent {
	
	
	public void setGravity(double gravity);
	public void setTerminalVelocity(double tv);
	public double getTerminalVelocity();
	
	public void setFalling(boolean b);
	public boolean isFalling();
	
	public void update();
}
