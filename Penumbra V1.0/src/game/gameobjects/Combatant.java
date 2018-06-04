package game.gameobjects;

import game.gameobjects.components.CombatStatComponent;

public interface Combatant extends Interactable {

	public void hit(Combatant other);
	public CombatStatComponent getCombatStatComponent();
	public boolean toRemove();
	
}
