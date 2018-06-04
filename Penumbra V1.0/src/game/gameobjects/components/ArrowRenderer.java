package game.gameobjects.components;

import engine.entity.Animation;
import engine.map.Camera;
import game.ResourceLoader;
import game.gameobjects.props.Arrow;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ArrowRenderer {

	private Arrow am;
	
	private Animation animation;
	
	private BufferedImage arrowImg;
	
	private Camera camera;
	
	public ArrowRenderer(Arrow am, Camera camera){
		this.am = am;
		this.camera = camera;
		
		animation = new Animation();
		animation.setFrames(ResourceLoader.FieryArrow[0]);
		animation.setDelay(5);
		//arrowImg = ResourceLoader.FieryArrow[0][0];
	}
	
	public void setCamera(Camera camera){ this.camera = camera; }
	public Camera getCamera(){ return camera; }
	
	public void update(){
		animation.update();
	}
	
	public void render(Graphics2D g){
		int x = (int)(am.getX() - camera.getX());
		int y = (int)(am.getY() - camera.getY());
		
		AffineTransform oldTransform = g.getTransform();
		g.translate(x, y);
		g.rotate(am.getAngle());
		g.translate(-x, -y);
		
		arrowImg = animation.getImage();
		g.drawImage(arrowImg, x - (arrowImg.getWidth() / 2), y - (arrowImg.getHeight() / 2), null);
		
		g.setTransform(oldTransform);
	}
}
