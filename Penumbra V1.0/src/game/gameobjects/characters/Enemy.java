package game.gameobjects.characters;

import engine.entity.RenderableCharacter;
import engine.entity.components.InputComponent;
import engine.map.TileMap;
import game.gameobjects.Character;
import game.gameobjects.Combatant;

public abstract class Enemy extends Character implements RenderableCharacter, Combatant {

	
	
	
	private InputComponent inputComponent;
	
	protected Enemy(TileMap tm) {
		super(tm);
	}
	
	public void setInputComponent(InputComponent ic){
		this.inputComponent = ic;
	}
	
	public InputComponent getInputComponent(){ return inputComponent; }
	
	/*
	public void hit(Interactable other) {
		if(other.getContactDamage() > 0){
			getHealthComponent().decreaseHealth(other.getContactDamage());
		}
	}*/

}
