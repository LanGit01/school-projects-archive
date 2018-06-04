package game.gameobjects;

import engine.entity.MovingEntity;
import engine.map.TileMap;

public class TriggerTrap extends MovingEntity {

	private boolean active;
	private boolean triggered;
	private boolean remove;
	private int removeCtr;
	private int removeDelay;
	
	protected TriggerTrap(TileMap tm) {
		super(tm);
		
		active = triggered = remove = false;
		removeCtr = 0;
		removeDelay = 150;
	}
	
	public void trigger(){
		triggered = active = true;
	}
	
	public void deactivate(){
		active = false;
	}
	
	public boolean isTriggered(){
		return triggered;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public boolean toRemove(){
		return (remove && removeCtr == removeDelay);
	}
	
	public void setDisappearDelay(int delay){
		this.removeDelay = delay;
	}
	
	public void hit(Combatant c){
		
	}
	
	public void remove(){
		remove = true;
	}
	
	protected void updateRemoveDelay(){
		removeCtr++;
	}
	
	public void update(){
		
	}

}
