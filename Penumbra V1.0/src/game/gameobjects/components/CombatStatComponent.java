package game.gameobjects.components;

public class CombatStatComponent {

	private int damage;		// Contact Damage
	private boolean invulnerable;
	
	private int maxHP;
	private int curHP;
	
	public CombatStatComponent(int maxHitPoints, int curHitPoints, int damage){
		this(maxHitPoints, curHitPoints, damage, false);
	}
	
	public CombatStatComponent(int maxHitPoints, int curHitPoints, int damage, boolean invulnerable){
		this.maxHP = maxHitPoints;
		this.curHP = curHitPoints;
		this.damage = damage;
		this.invulnerable = invulnerable;
	}
	
	public void setDamage(int damage){ this.damage = damage; }
	public void setMaxHP(int maxHitPoints){ this.maxHP = maxHitPoints; }
	public void setCurrentHP(int curHitPoints){ this.curHP = curHitPoints; }
	public void setInvulnerable(boolean b){ this.invulnerable = b; }
	
	public int getDamage(){ return damage; }
	public int getMaxHP(){ return maxHP; }
	public int getCurrentHP(){ return curHP; }
	public boolean isInvulnerable(){ return invulnerable; }
	
	public void decreaseHP(int amount){
		curHP -= amount;
		if(curHP < 0){
			curHP = 0;
		}
	}
	
	public void increaseHP(int amount){
		curHP += amount;
		if(curHP > maxHP){
			curHP = maxHP;
		}
	}
	
	
}
