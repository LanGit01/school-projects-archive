package game.gameobjects;

import java.awt.Graphics2D;

import engine.entity.MovingEntity;
import engine.entity.OrderedPair;
import engine.map.TileMap;
import game.gameobjects.components.CombatStatComponent;
import game.gameobjects.components.BasePhysics;

public abstract class Character extends MovingEntity {

	private boolean movingLeft;
	private boolean movingRight;
	private boolean jumping;
	
	
	private double mAccel;
	private double mDecel;
	private double startJumpSpeed;
	
	private double mMaxMoveSpeed;
	
	private boolean facingRight;
	
	
	//private HealthComponent healthComponent;
	private CombatStatComponent combatComponent;
	private BasePhysics physics;
	
	protected Character(TileMap tm){
		super(tm);
		
		setPhysicsComponent(new BasePhysics(this, tm));
		physics = (BasePhysics)getPhysicsComponent();
	}
	
	public void setMoveAcceleration(double accel){ this.mAccel = accel; }
	public void setMoveDeceleration(double decel){ this.mDecel = decel; }
	public void setStartingJumpSpeed(double js){ this.startJumpSpeed = js; }
	public void setMaxMoveSpeed(double ms){ this.mMaxMoveSpeed = ms; }
	public void setTerminalVelocity(double tv){ physics.setTerminalVelocity(tv); }
	
	public double getMoveAcceleration(){ return mAccel; }
	public double getMoveDecceleration(){ return mDecel; }
	public double getStartingJumpSpeed(){ return startJumpSpeed; }
	public double getMaxMoveSpeed(){ return mMaxMoveSpeed; }
	public double getTerminalVelocity(){ return physics.getTerminalVelocity(); }
	
	
	public void setMovingLeft(boolean b){  this.movingLeft = b; }
	public void setMovingRight(boolean b){ this.movingRight = b; }
	public void setJumping(boolean b){ this.jumping = b; }
	public void setFalling(boolean b){ physics.setFalling(b); }
	
	
	public boolean isMovingLeft(){ return movingLeft; }
	public boolean isMovingRight(){ return movingRight; }
	public boolean isJumping(){ return jumping; }
	public boolean isFalling(){ return physics.isFalling(); }
			
	
	public void setFacingRight(boolean b){ this.facingRight = b; }
	public boolean isFacingRight(){ return facingRight; }
	
	/*
	public void setGravity(double gravity){
		//super.setGravity(gravity);
		physicsComponent.setGravity(gravity);
	}*/
	
	/*						Components 						*/
	public void setCombatStatComponent(CombatStatComponent csc){ this.combatComponent = csc; }
	public CombatStatComponent getCombatStatComponent(){ return combatComponent; }
	
	
	private void updateVectors(){
	
		OrderedPair vector = getVector();
		double dx = vector.x;
		double dy = vector.y;
		
		if(movingLeft){
			dx -= mAccel;
			if(dx < -mMaxMoveSpeed){
				dx = -mMaxMoveSpeed;
			}
		}else
		if(movingRight){
			dx += getMoveAcceleration();
			if(dx > mMaxMoveSpeed){
				dx = mMaxMoveSpeed;
			}
		}else{
			if(dx > 0){
				dx -= mDecel;
				if(dx < 0){
					dx = 0;
				}
			}else
			if(dx < 0){
				dx += mDecel;
				if(dx > 0){
					dx = 0;
				}
			}
		}
		
		if(jumping && !isFalling()){
			dy = startJumpSpeed;
			setFalling(true);
		}
		
		setVector(dx, dy);
	}
	
	public void update(){
		// Input Processing
		// Physics
		updateVectors();
		physics.update();
		setPosition(getNextPosition().x, getNextPosition().y);
	}
	
	public abstract void render(Graphics2D g);
	
}
