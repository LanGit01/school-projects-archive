package game.gameobjects;

import java.awt.Graphics2D;

import engine.entity.OrderedPair;
import engine.entity.Renderable;
import engine.entity.components.PhysicsComponent;
import engine.map.TileMap;
import engine.misc.SpriteLoader;
import game.gameobjects.components.BasePhysics;
import game.gameobjects.components.SimpleRenderer;

public class FallingTrap extends TriggerTrap implements Renderable, TriggerEvent {

	private boolean hasBeenTriggered;
	private SimpleRenderer renderer;
	
	public FallingTrap(TileMap tm) {
		super(tm);
		
		setBoundingBoxDimensions(50, 50);
		
		BasePhysics bp = new BasePhysics(this, tm);
		bp.setGravity(0.14);
		bp.setTerminalVelocity(5);
		setPhysicsComponent(bp);
		
		hasBeenTriggered = false;
		
		renderer = new SimpleRenderer(this, SpriteLoader.load("/metalcrate.gif", new int[]{1}, new int[]{50}, new int[]{50}), new int[]{0}, tm.getCamera());
	}
	
	public void update(){
		PhysicsComponent physics = getPhysicsComponent();
		if(isTriggered() && !hasBeenTriggered){
			physics.setFalling(true);
			hasBeenTriggered = true;
		}
		
		
		if(!physics.isFalling()){
			deactivate();
			remove();
		}else{
			physics.update();
			OrderedPair next = getNextPosition();
			setPosition(next.x, next.y);
		}
		
		if(toRemove()){
			updateRemoveDelay();
		}
		
		renderer.update();
	}
	
	public void render(Graphics2D g){
		renderer.render(g);
	}
	
}
