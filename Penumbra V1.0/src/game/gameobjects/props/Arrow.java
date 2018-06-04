package game.gameobjects.props;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.entity.MovingEntity;
import engine.entity.OrderedPair;
import engine.map.TileMap;

import game.gameobjects.Combatant;
import game.gameobjects.Projectile;
import game.gameobjects.components.ArrowPhysics;
import game.gameobjects.components.ArrowRenderer;
import game.gameobjects.components.CombatStatComponent;

/**
 * Dilemma: when to compute the angle (optimization)
 * @author ace
 *
 */
public class Arrow extends MovingEntity implements Projectile, Combatant {
	
	public enum Direction { LEFT, RIGHT }
	
	private double angle;
	//private double penetrationPower;
	private boolean live;
	private boolean remove;
	private int removeDelay;
	
	private double arrowTipDistance;
	
	private Rectangle collisionRect;
	
	//private TileMap tm;
	
	private CombatStatComponent combatComponent;
	private ArrowPhysics physicsComponent;
	private ArrowRenderer renderingComponent;
	
	public Arrow(TileMap tm, double power, Direction d){
		super(tm);
		
		arrowTipDistance = 10;
		
		setBoundingBoxDimensions(3, 3);
		
		setVector((d == Direction.LEFT ? -power : power), 0);
		//penetrationPower = 0.1;
		
		createCollisionRect();
		live = true;
		
		
		removeDelay = 150;
		
		
		combatComponent = new CombatStatComponent(0, 0, 1, true);
		
		physicsComponent = new ArrowPhysics(this, tm);
		physicsComponent.setGravity(0.14);
		physicsComponent.setTerminalVelocity(5);
		
		renderingComponent = new ArrowRenderer(this, tm.getCamera());
		
	}
	
	public void setLive(boolean b){ this.live = b; }
	public void setAngle(double angle){ this.angle = angle; }
	
	public boolean isLive(){ return live; }
	public double getAngle(){ return angle; }
	
	private void createCollisionRect(){
		if(live){
			OrderedPair position = getPosition();
			OrderedPair vector = getVector();
			angle = Math.atan2(vector.y, vector.x);
			
			int px = (int)(arrowTipDistance * Math.cos(angle)); // soh
			int py = (int)(arrowTipDistance * Math.sin(angle));// cah
			
			collisionRect = new Rectangle((int)position.x + px - (getCollisionWidth() / 2), (int)position.y + py - (getCollisionHeight() / 2), getCollisionWidth(), getCollisionHeight());
		}
	}
	
	@Override
	public Rectangle getBoundingBox(){
		createCollisionRect();
		return collisionRect;
	}
	
	public void update(){
		physicsComponent.update();
		
		renderingComponent.update();
		
		if(!live){
			removeDelay--;
			if(removeDelay < 0){
				remove = true;
			}
		}
	}
	
	public boolean toRemove(){
		return remove;
	}
	
	public void render(Graphics2D g){
		//int xoffset = (int)tm.getCamera().getX();
		//int yoffset = (int)tm.getCamera().getY();
		//g.drawImage(arrowImg, (int)(getX() - xoffset - arrowImg.getWidth() / 2), (int)(getY() - yoffset - arrowImg.getHeight() / 2), null);
		
		renderingComponent.render(g);
		
		/*
		Color c = g.getColor();
		g.setColor(Color.RED);
		Rectangle r = new Rectangle(getBoundingBox());
		r.x = (int)(r.x - xoffset);
		r.y = (int)(r.y - yoffset);
		g.fill(r);
		g.setColor(c);*/
	}

	@Override
	public void hit(Combatant other) {
		return;		
	}

	@Override
	public CombatStatComponent getCombatStatComponent() {
		return combatComponent;
	}

	/*
	@Override
	public void hit(Interactable other) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getContactDamage() {
		return damage;
	}*/

	
	

	
}
