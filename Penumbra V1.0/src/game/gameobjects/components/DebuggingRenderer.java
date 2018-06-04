package game.gameobjects.components;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.entity.MovingEntity;
import engine.entity.Renderable;
import engine.entity.components.RenderingComponent;
import engine.map.Camera;

public class DebuggingRenderer implements RenderingComponent {
	
	private Renderable renderable;
	private Camera camera;
	
	public DebuggingRenderer(Renderable renderable, Camera camera){
		this.renderable = renderable;
		this.camera = camera;
	}
	
	public void render(Graphics2D g){
		Rectangle r = ((MovingEntity)renderable).getBoundingBox();
		r.x = r.x - (int)camera.getX();
		r.y = r.y - (int)camera.getY();
		g.fill(r);
	}
}
