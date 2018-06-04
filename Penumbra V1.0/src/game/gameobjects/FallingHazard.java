package game.gameobjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.entity.MovingEntity;
import engine.map.TileMap;
import game.gameobjects.components.BasePhysics;
import game.gameobjects.components.CombatStatComponent;

public class FallingHazard extends MovingEntity implements Combatant {
	
	private boolean remove;
	private int removeDelay;
	private int removeCtr;
	
	private boolean active;		// can damage player
	private boolean triggered;
	
	private CombatStatComponent combat;
	//private BasicRenderer renderer;
	
	public FallingHazard(TileMap tm) {
		super(tm);
		
		active = remove = triggered = false;
		
		removeDelay = 200;
		removeCtr = 0;
		
		combat = new CombatStatComponent(0, 0, 2, true);
		setPhysicsComponent(new BasePhysics(this, tm));
		
	}
	
	public void trigger(){
		getPhysicsComponent().setFalling(true);
		triggered = active = true;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void update(){
		if(toRemove()) return;
		
		getPhysicsComponent().update();
		
		if(triggered && !getPhysicsComponent().isFalling()){
			remove = true;
		}
		
		if(remove == true){
			removeCtr++;
		}
	}

	@Override
	public void hit(Combatant other) {
		active = false;
	}

	@Override
	public CombatStatComponent getCombatStatComponent() {
		return combat;
	}

	@Override
	public boolean toRemove() {
		return (removeCtr == removeDelay);
	}
	
	public void render(Graphics2D g){
		
		Rectangle r = getBoundingBox();
		r.x = r.x - (int)getTileMap().getCamera().getX();
		r.y = r.y - (int)getTileMap().getCamera().getY();
		g.fill(r);
	}
	
	
	
}
