package game.gameobjects.characters;

import engine.entity.OrderedPair;
import engine.entity.RenderableCharacter;

import engine.map.TileMap;
import engine.misc.SpriteLoader;

import game.gameobjects.Character;
import game.gameobjects.Combatant;
import game.gameobjects.components.CombatStatComponent;
import game.gameobjects.components.GameCharacterRenderer;
import game.gameobjects.props.Arrow;
import game.levels.World;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Character implements RenderableCharacter, Combatant {
	
	// Animations
	/*
	private static final int[] NUMFRAMES = { 1, 3, 1 ,1 };
	private static final int[] FRAMEWIDTHS = { 40, 40, 40, 40 };
	private static final int[] FRAMEHEIGHTS = { 64, 64, 64, 64 };
	private static final int[] DELAY = {0, 12, 0, 0};
	*/
	private static final int[] NUMFRAMES = { 1, 7, 1, 0, 9};
	private static final int[] FRAMEWIDTHS = { 64, 64, 64, 64, 64};
	private static final int[] FRAMEHEIGHTS = { 64, 64, 64, 64, 64};
	private static final int[] DELAY = {0, 5, 0, 0, 6};
	private ArrayList<BufferedImage[]> sprites;
	
	// Actions
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int NOCK_ARROW = 4;
	//private static final int RELEASE_ARROW = 4;
	//private static final int NOCK_ARROW = 3;
	//private static final int RELEASE_ARROW = 4;
	
	private boolean flinching;
	private int flinchcount;
	
	// Arrows
	private ArrayList<Arrow> arrows;
	private boolean nocked;
	private boolean released;
	private double arrow_Power;
	private double arrow_PowerStep;
	private double arrow_MaxPower;
	private double arrow_MinPower;
	
	private int contactDamage;
	
	//private InputComponent inputComponent;
	private GameCharacterRenderer renderer;
	
	private World collisionHandler;
	
	public Player(TileMap tm, World wech){
		super(tm);
		
		setBoundingBoxDimensions(16, 56);
		setMoveAcceleration(1.67);
		setMoveDeceleration(1.67);
		setMaxMoveSpeed(1.67);
		setStartingJumpSpeed(-4.8);
		setTerminalVelocity(4);
		
		setFacingRight(true);
		getPhysicsComponent().setGravity(0.14);
		
		contactDamage = 0;
		
		arrows = new ArrayList<Arrow>();
		nocked = false;
		arrow_Power = 0;
		arrow_PowerStep = 0.24;
		arrow_MaxPower = 22;
		arrow_MinPower = 8;
		
		sprites = SpriteLoader.load("/Sprites/Player/mc.png", NUMFRAMES, FRAMEWIDTHS, FRAMEHEIGHTS);
		
		setCombatStatComponent(new CombatStatComponent(10, 10, 0));
		//inputComponent = new PlayerInputComponent(this);
		renderer = new GameCharacterRenderer(this, sprites, DELAY, tm.getCamera());
		
		this.collisionHandler = wech;
	}
	
	
	public void update(){
		//inputComponent.update();
		super.update();
		
		updateAnimations();
		
		if(flinching){
			flinchcount++;
			if(flinchcount > 60){
				flinching = false;
				flinchcount = 0;
			}
		}
		
		if(nocked){
			if(arrow_Power == 0){
				arrow_Power = arrow_MinPower;
				released = false;
			}else{
				arrow_Power += arrow_PowerStep;
				if(arrow_Power > arrow_MaxPower){
					arrow_Power = arrow_MaxPower;
				}
			}
		}else{
			if(arrow_Power > 0){
				System.out.println("LAUNCHED");
			//	Arrow a = new Arrow(getTileMap(), arrow_Power, (isFacingRight() ? Arrow.Direction.RIGHT : Arrow.Direction.LEFT));
				Arrow a = new Arrow(getTileMap(), arrow_Power, (isFacingRight() ? Arrow.Direction.RIGHT : Arrow.Direction.LEFT));
				a.setPosition(getX(), getY());
				arrows.add(a);
				collisionHandler.addPlayerProjectile(a);
				arrow_Power = 0;
				released = true;
			}
		}
		
		for(Arrow i: arrows){
			i.update();
		}
		
		for(int i = arrows.size() - 1; i >= 0; i--){
			if(arrows.get(i).toRemove()){
				collisionHandler.removePlayerProjectile(arrows.get(i));
				arrows.remove(i);
			}
		}
	}
	
	
	
	private void updateAnimations(){
		OrderedPair vector = getVector();
		
		if(nocked){
			if(renderer.getCurrentAnimation() != NOCK_ARROW){
				renderer.setAnimation(NOCK_ARROW, true);
			}
		}else
		if(vector.y > 0 || vector.y < 0){
			if(renderer.getCurrentAnimation() != JUMPING){
				renderer.setAnimation(JUMPING);
			}
		}else
		if(isMovingLeft() || isMovingRight()){
			if(renderer.getCurrentAnimation() != WALKING){
				renderer.setAnimation(WALKING);
			}
		}else{
			if(renderer.getCurrentAnimation() != IDLE){
				renderer.setAnimation(IDLE);
			}
		}
		
		if(vector.x > 0){
			setFacingRight(true);
		}else
		if(vector.x < 0){
			setFacingRight(false);
		}
		
		renderer.update();
	}
	
	
	public void setContactDamage(int damage){ this.contactDamage = damage; }
	public int getContactDamage(){ return contactDamage; }
	
	@Override
	public void render(Graphics2D g){
		renderer.render(g);
		
		for(Arrow i: arrows){
			i.render(g);
		}
	}
	
	public void nockArrow(boolean b){
		nocked = b;
	}


	@Override
	public void hit(Combatant other) {
		if(flinching) return;
		
		int oDamage = other.getCombatStatComponent().getDamage();
		if(oDamage > 0){
			this.getCombatStatComponent().decreaseHP(oDamage);
			flinching = true;
		}

	}


	@Override
	public boolean toRemove() {
		return false;
	}


	
	/*
	@Override
	public void hit(Interactable other) {
		if(flinching) return;
		if(other.getContactDamage() > 0){
			getHealthComponent().decreaseHealth(other.getContactDamage());
			flinching = true;
		}
	}
	*/
	
}
