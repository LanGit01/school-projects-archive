package game.gameobjects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.entity.Entity;
import engine.map.Camera;

public class TriggerZone extends Entity {
	
	private Camera camera; // test purposes only
	
	private TriggerEvent triggerEvent;
	
	public TriggerZone(TriggerEvent te, Camera camera, int x, int y, int width, int height){
		this.triggerEvent = te;
		this.camera = camera;
		setPosition(x, y);
		setBoundingBoxDimensions(width, height);
	}
	
	public void activateTrigger(){
		triggerEvent.trigger();
	}
	
	public void render(Graphics2D g){
		// Not to be rendered!!! test purposes only
		Rectangle r = getBoundingBox();
		r.x = r.x - (int)camera.getX();
		r.y = r.y - (int)camera.getY();
		g.fill(r);
	}
	
}
