package game.gameobjects.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import engine.entity.Animation;
import engine.entity.OrderedPair;
import engine.entity.Renderable;
import engine.entity.components.RenderingComponent;
import engine.map.Camera;

public class SimpleRenderer implements RenderingComponent {

	private ArrayList<BufferedImage[]> sprites;
	private int[] delays;
	private Animation animation;
	private int currentAnimation;
	
	private Renderable renderable;
	private Camera camera;
	
	public SimpleRenderer(Renderable renderable, ArrayList<BufferedImage[]> sprites, int[] delays, Camera camera){
		this.renderable = renderable;
		this.sprites = sprites;
		this.delays = delays;
		this.camera = camera;
		animation = new Animation();
		setAnimation(0);
	}
	
	public void setCamera(Camera camera){ this.camera = camera; }
	public Camera getCamera(){ return camera; }
	
	public void setAnimation(int a){
		currentAnimation = a;
		animation.setFrames(sprites.get(currentAnimation));
		animation.setDelay(delays[currentAnimation]);
	}
	
	public int getCurrentAnimation(){ return currentAnimation; }
	
	public void update(){
		animation.update();
	}
	
	public void render(Graphics2D g){
		OrderedPair position = renderable.getPosition();
		int xoffset = (int)camera.getX();
		int yoffset = (int)camera.getY();
		
		BufferedImage img = animation.getImage();
		
		g.drawImage(img, (int)(position.x - (img.getWidth() / 2) - xoffset), (int)(position.y - (img.getHeight() / 2) - yoffset), null);
		
		
		
	}
}
