package game.gameobjects.props;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.gameobjects.characters.Enemy;
import game.gameobjects.characters.Player;

public class HUD {

	private Player player;
	private Enemy enemy;
	
	public HUD(Player player, Enemy enemy){
		this.player = player;
		this.enemy = enemy;
	}
	
	public void render(Graphics2D g){
		Color c = g.getColor();
		g.setColor(new Color(255, 255, 255, 150));
		
		g.fillRect(0, 0, 180, 100);
		
		g.setColor(Color.BLUE);
		g.drawRect(0, 0, 180, 100);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		g.drawString("PLAYER", 6, 16);
		g.drawString("Max: " + player.getCombatStatComponent().getMaxHP(), 80, 16);
		g.drawString("Current: " + player.getCombatStatComponent().getCurrentHP(), 80, 32);
		g.drawString("ENEMY", 6, 48);
		g.drawString("Max: " + enemy.getCombatStatComponent().getMaxHP(), 80, 48);
		g.drawString("Current: " + enemy.getCombatStatComponent().getCurrentHP(), 80, 64);
		
		
		
		g.setColor(c);
	}
}
