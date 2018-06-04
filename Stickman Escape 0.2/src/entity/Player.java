package entity;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;

import java.awt.image.BufferedImage;
import java.io.IOException;


import tileset.TileMap;

public class Player extends Entity {
	
	// Player-specific variables
	//private int health;
	private double jumpSpeed;
	private boolean jumping;
	private boolean dead;
	
	private ArrayList<AnimationData> spriteData;
	private int numSpriteActions;
	private final int[] NUMFRAMES = {1, 7};
	private final int[] FRAMEWIDTHS = {44, 44};
	private final int[] FRAMEHEIGHTS = {80, 80};
	private final int[] SPRITEDELAYS = {-1, 6};
	
	// Animation Actions
	private int currentAction;
	private static final int IDLE = 0;
	private static final int RUNNING = 1;
	
	public Player(TileMap tm){
		super(tm);
		dead = false;
		facingRight = true;
		width = 44;
		height = 80;
		
		cwidth = 34;
		cheight = 70;
		hcwidth = cwidth / 2;
		hcheight = cheight / 2;
		
		moveSpeed = 1.6;
		maxMoveSpeed = 1.6;
		jumpSpeed = -5.8;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		
		numSpriteActions = NUMFRAMES.length;
		spriteData = new ArrayList<AnimationData>();
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/RunningMan2.gif"));
			
			int markerheight = 0;
			for(int i = 0; i < numSpriteActions; i++){
				BufferedImage[] spriteAction = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0; j < NUMFRAMES[i]; j++){
					spriteAction[j] = spritesheet.getSubimage(j * FRAMEWIDTHS[i], markerheight, FRAMEWIDTHS[i], FRAMEHEIGHTS[i]);
				}
				spriteData.add(new AnimationData(spriteAction, FRAMEWIDTHS[i], FRAMEHEIGHTS[i], SPRITEDELAYS[i]));
				markerheight += FRAMEHEIGHTS[i];
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jumping = false;
		falling = false;
		setAnimation(IDLE);
	}
	
	public void getNextPosition(){
		if(left){
			dx -= moveSpeed;
			if(dx < -maxMoveSpeed){
				dx = -maxMoveSpeed;
			}
		}else
		if(right){
			dx += moveSpeed;
			if(dx > maxMoveSpeed){
				dx = maxMoveSpeed;
			}
		}else{
			dx = 0;
		}
		
		if(jumping && !falling){
			System.out.println(y + cheight / 2);
			dy = jumpSpeed;
			falling = true;
		}

		nextx = x + dx;
		nexty = y + dy;
		
		if(falling){
			dy += fallSpeed;
			if(dy > maxFallSpeed){
				dy = maxFallSpeed;
			}
		}
		
		
	}
	
	public void update(){
		getNextPosition();
		checkTileCollisions();
		setPosition(nextx, nexty);
		
		
		if(left || right){
			if(currentAction != RUNNING){
				setAnimation(RUNNING);
			}
		}else{
			if(currentAction != IDLE){
				setAnimation(IDLE);
			}
		}
		
		animation.update();
		
		if(left){
			facingRight = false;
		}else
		if(right){
			facingRight = true;
		}
		
		
		
	}
	
	public void draw(java.awt.Graphics2D g){
		setMapPosition();
		
		if(facingRight){
			//g.fillRect((int)(x - xmap - (width / 2)), (int)(y - ymap - (height / 2)), cwidth, cheight);
			//g.drawImage(animation.getImage(), (int)(x - xmap) + (width / 2) , (int)(y - xmap) - (height / 2), -width, height, null);
			g.drawImage(animation.getImage(), (int)(x - xmap + (width / 2)), (int)(y - ymap - (height/ 2)), -width, height, null);
		}else{
			//g.fillRect((int)(x - xmap - (width / 2)), (int)(y - ymap - (height / 2)), cwidth, cheight);
			//g.drawImage(animation.getImage(), (int)(x - xmap) - (width / 2) , (int)(y - xmap) - (height / 2), null);
			g.drawImage(animation.getImage(), (int)(x - xmap) - (width / 2) , (int)(y - ymap - (height / 2)), null);
		}
	}
	
	private void setAnimation(int a){
		currentAction = a;
		animation.setFrames(spriteData.get(a).getFrames());
		animation.setDelay(spriteData.get(a).getFrameDelay());
		width = spriteData.get(a).getFrameWidth();
		height = spriteData.get(a).getFrameHeight();
	}
	
	public void die(){
		dead = true;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public void setLeft(boolean b) { left = b;}
	public void setRight(boolean b) { right = b;}
	public void setJumping(boolean b){ jumping = b; }
	
}
