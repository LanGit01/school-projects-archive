package game.gameobjects.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import engine.entity.Animation;
import engine.entity.OrderedPair;
import engine.entity.RenderableCharacter;
import engine.entity.components.RenderingComponent;
import engine.map.Camera;

public class GameCharacterRenderer implements RenderingComponent 	{

	private ArrayList<BufferedImage[]> sprites;
	private int[] delays;
	private Animation animation;
	private int currentAnimation;
	
	private boolean playOnce;
	
	private boolean paused;
	
	private RenderableCharacter renderable;
	private Camera camera;
	
	public GameCharacterRenderer(RenderableCharacter renderable, ArrayList<BufferedImage[]> sprites, int[] delays, Camera camera){
		this.renderable = renderable;
		this.sprites = sprites;
		this.delays = delays;
		this.camera = camera;
		animation = new Animation();
		setAnimation(0);
		
		paused = false;
	}
	
	public void setCamera(Camera camera){ this.camera = camera; }
	public Camera getCamera(){ return camera; }
	
	public void setAnimation(int a){
		currentAnimation = a;
		animation.setFrames(sprites.get(currentAnimation));
		animation.setDelay(delays[currentAnimation]);
		paused = false;
		playOnce = false;
	}
	
	public void setAnimation(int a, boolean playOnce){
		setAnimation(a);
		this.playOnce = playOnce;
	}
	
	public boolean animationPlayedOnce(){ return animation.hasPlayedOnce(); }
	public void pauseAnimation(){ paused = true; }
	public void unpauseAnimation(){ paused = false; }
	
	public int getCurrentFrame(){ return animation.getCurrentFrame(); }
	
	public int getCurrentAnimation(){ return currentAnimation; }
	
	public void update(){
		if(paused) return;
		animation.update();
		if(playOnce && animation.hasPlayedOnce()){
			animation.setFrame(animation.getNumFrames() - 1);
			paused = true;
		}
	}
	
	public void render(Graphics2D g){
		OrderedPair position = renderable.getPosition();
		int xoffset = (int)camera.getX();
		int yoffset = (int)camera.getY();
		
		BufferedImage img = animation.getImage();
		
		if(renderable.isFacingRight()){
			g.drawImage(img, (int)(position.x - (img.getWidth() / 2) - xoffset), 
					(int)(position.y - (img.getHeight() / 2) - yoffset), null);
		}else{
			g.drawImage(img, (int)(position.x + (img.getWidth() / 2) - xoffset),
					(int)(position.y - (img.getHeight() / 2) - yoffset), -img.getWidth(), img.getHeight(), null);
		}
		
		
		
	}
	
	
}
