package game.levels;

import game.gameobjects.Combatant;
import game.gameobjects.Projectile;
import game.gameobjects.TriggerZone;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class World {
	
	private ArrayList<Combatant> playerUnits;
	private ArrayList<Combatant> enemyUnits;
	
	private ArrayList<Projectile> playerProjectiles;
	
	private ArrayList<TriggerZone> zones;
	
	public World(){
		playerUnits = new ArrayList<Combatant>();
		enemyUnits = new ArrayList<Combatant>();
		playerProjectiles = new ArrayList<Projectile>();
		
		zones = new ArrayList<TriggerZone>();
	}
	
	private boolean hasCollision(Rectangle a, Rectangle b){
		return !(a.x > b.x + b.width || b.x > a.x + a.width || a.y > b.y + b.height || b.y > a.y + a.height);
	}
	
	public void addPlayerUnit(Combatant i){
		if(i == null) return;
		playerUnits.add(i);
	}
	
	public void addEnemyUnit(Combatant i){
		if(i == null) return;
		enemyUnits.add(i);
	}
	
	public void addPlayerProjectile(Projectile p){
		if(p == null) return;
		playerProjectiles.add(p);
	}
	
	public void removeEnemyUnit(Combatant i){
		if(i == null) return;
		enemyUnits.remove(i);
	}
	
	public void removePlayerProjectile(Projectile p){
		playerProjectiles.remove(p);
	}
	
	
	
	public void resolveCollisions(){
		
		/*for(Combatant eu: enemyUnits){
			for(Combatant pu: playerUnits){
				if(hasCollision(pu.getBoundingBox(), eu.getBoundingBox())){
					pu.hit(eu);
					eu.hit(pu);
					System.out.println("COLLISION");
					if(pu.toRemove()) removalList.add(pu);
					if(eu.toRemove()) removalList.add(eu);
				}
			}
			
			for(Projectile pp: playerProjectiles){
				if(hasCollision(pp.getBoundingBox(), eu.getBoundingBox())){
					pp.hit(eu);
					eu.hit(pp);
					System.out.println("COLLISION");
					if(eu.toRemove()) removalList.add(eu);
					if(pp.toRemove()) removalList.add(pp);
				}
			}
		}*/
		
		Iterator<Combatant> eu = enemyUnits.iterator();
		while(eu.hasNext()){
			Combatant euc = eu.next();
			
			Iterator<Combatant> pu = playerUnits.iterator();
			Iterator<Projectile> pp = playerProjectiles.iterator();
			
			while(pu.hasNext()){
				Combatant puc = pu.next();
				if(hasCollision(puc.getBoundingBox(), euc.getBoundingBox())){
					puc.hit(euc);
					euc.hit(puc);
					//System.out.println("COLLISION");
					if(puc.toRemove()) pu.remove(); 
				}
			}
			
			while(pp.hasNext()){
				Projectile ppc = pp.next();
				if(ppc.isLive() && hasCollision(ppc.getBoundingBox(), euc.getBoundingBox())){
					ppc.hit(euc);
					euc.hit(ppc);
					//System.out.println("COLLISION");
					
					if(ppc.toRemove()){
						pp.remove();
					}
				}
			}
			
			if(euc.toRemove()) {
				eu.remove();
			}
		}
		
		
		
		for(Combatant pu: playerUnits){
			for(TriggerZone z: zones){
				if(hasCollision(pu.getBoundingBox(), z.getBoundingBox())){
					z.activateTrigger();
				}
			}
		}
		
		
	}
	
	
	
	public void addZone(TriggerZone tz){
		if(tz == null) return;
		zones.add(tz);
	}
}
