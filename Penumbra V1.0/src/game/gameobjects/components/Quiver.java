package game.gameobjects.components;

public class Quiver {
	
	private int maxArrows;
	private int curArrows;
	
	public Quiver(int maxArrows){
		this(maxArrows, maxArrows);
	}
	
	public Quiver(int maxArrows, int curArrows){
		this.maxArrows = maxArrows;
		this.curArrows = curArrows;
	}
	
	public int getMaxNumArrows(){ return maxArrows; }
	public int getCurrentNumArrows(){ return curArrows; }
	
	public boolean hasArrowsLeft(){ return (curArrows == 0); }
	
	public void useArrow(){
		if(curArrows > 0){
			curArrows--;
		}
	}
	
	public void addArrow(int amount){
		if(amount < 0) return;
		curArrows += amount;
		if(curArrows > maxArrows){
			curArrows = maxArrows;
		}
	}
}
