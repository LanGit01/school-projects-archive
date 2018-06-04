package game.gameobjects.components;

public class HealthComponent {
	
	private int maxHealth;
	private int curHealth;
	
	public HealthComponent(int maxHealth, int curHealth){
		this.maxHealth = maxHealth;
		this.curHealth = curHealth;
	}
	
	public HealthComponent(int maxHealth){
		this(maxHealth, 0);
	}
	
	public HealthComponent(){
		this(0, 0);
	}
	
	public void setMaxHealth(int maxHealth){ this.maxHealth = maxHealth; }
	public void setCurrentHealth(int curHealth){ this.curHealth = curHealth; }
	
	public int getMaxHealth(){ return maxHealth; }
	public int getCurrentHealth(){ return curHealth; }
	
	public void increaseHealth(int amount){
		curHealth += amount;
		if(curHealth > maxHealth){
			curHealth = maxHealth;
		}
	}
	
	public void decreaseHealth(int amount){
		increaseHealth(-amount);
	}
}
