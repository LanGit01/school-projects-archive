package game.gameobjects.components;


import engine.entity.components.InputComponent;
import game.gameobjects.characters.Enemy;

public class DumbEnemyAI extends InputComponent {

	private Enemy e;
	private BasePhysics physicsComponent; 
	
	
	public DumbEnemyAI(Enemy e){
		this.e = e;
		this.physicsComponent = (BasePhysics)e.getPhysicsComponent();
	}
	
	public void update() {
		if(physicsComponent.hasWestCollision()){
			e.setMovingRight(true);
			e.setMovingLeft(false);
		}else
		if(physicsComponent.hasEastCollision()){
			e.setMovingLeft(true);
			e.setMovingRight(false);
		}else{
			if(!e.isMovingLeft() && !e.isMovingRight()){
				if(Math.random() > 0.5){
					e.setMovingRight(true);
				}else{
					e.setMovingLeft(true);
				}
			}
		}
		
		
	}

}
