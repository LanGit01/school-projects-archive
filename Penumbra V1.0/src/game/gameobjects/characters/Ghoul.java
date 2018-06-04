package game.gameobjects.characters;

import java.awt.Graphics2D;

import engine.map.TileMap;
import engine.misc.SpriteLoader;
import game.gameobjects.Combatant;
import game.gameobjects.components.CombatStatComponent;
import game.gameobjects.components.DumbEnemyAI;
import game.gameobjects.components.GameCharacterRenderer;

public class Ghoul extends Enemy {

	private boolean flinching;
	private int flinchcount;
	
	private boolean remove;
	
	private GameCharacterRenderer renderer;
	
	public Ghoul(TileMap tm) {
		super(tm);
		
		setBoundingBoxDimensions(30, 30);
		
		setMoveAcceleration(0.02);
		setMoveDeceleration(2.1);
		setMaxMoveSpeed(2.1);
		
		getPhysicsComponent().setGravity(0.14);
		setTerminalVelocity(4);
		
		flinchcount = 0;
		flinching = false;
		
		remove = false;
		
		// Setup Components
		setInputComponent(new DumbEnemyAI(this));
		setCombatStatComponent(new CombatStatComponent(2, 2, 1));
		renderer = new GameCharacterRenderer(this, SpriteLoader.load("/goomba.gif", new int[]{2}, new int[]{32}, new int[]{32}), new int[]{20}, tm.getCamera());
	}
	
	public boolean toRemove(){
		return remove;
	}

	@Override
	public void hit(Combatant other) {
		if(flinching) return;
		int oDamage = other.getCombatStatComponent().getDamage();
		System.out.println(oDamage);
		if(oDamage > 0){
			getCombatStatComponent().decreaseHP(oDamage);
			System.out.println("FLINCH");
			flinching = true;
			if(getCombatStatComponent().getCurrentHP() == 0){
				remove = true;
			}
		}
		
	}
	
	public void update(){
		getInputComponent().update();
		super.update();
		renderer.update();
		
		if(flinching){
			flinchcount++;
			if(flinchcount > 50){
				flinching = false;
				flinchcount = 0;
			}
		}
		
	}

	@Override
	public void render(Graphics2D g){
		//if(!remove){
		//	renderer.render(g);
		//}
		
		if(!flinching || flinchcount % 20 > 10){
			renderer.render(g);
		}
	}
	
}
