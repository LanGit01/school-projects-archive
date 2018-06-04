package hud;

import entity.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;

public class HUD {
	

	private Player player;
	private Font font;
	
	private boolean visible;
	
	public HUD(Player player){
		this.player = player;
		font = new Font("Verdana", Font.BOLD, 12);
		init();
	}
	
	public void init(){
		visible = false;
	}
	
	public void setVisible(boolean b){
		visible = b;
	}
	
	public void draw(java.awt.Graphics2D g){
		if(visible){
			Rectangle collisionplayer = player.getCollisionBox();
			g.setFont(font);
			Color prev = g.getColor();
			g.fillRect(0, 0, 200, 80);
			g.setColor(Color.BLACK);
			g.drawString("x: " + Integer.toString((int)player.getx()), 8, 18);
			g.drawString("y: " + Integer.toString((int)player.gety()), 8, 33);
			g.drawString("left: " + Integer.toString((int)player.getx() - (collisionplayer.width / 2)), 80, 18);
			g.drawString("right: " + Integer.toString((int)player.getx() + (collisionplayer.width / 2) - 1), 80, 33);
			g.drawString("top: " + Integer.toString((int)player.gety() - (collisionplayer.height / 2)), 80, 48);
			g.drawString("bottom: " + Integer.toString((int)player.gety() + (collisionplayer.height / 2) - 1), 80, 63);
			g.setColor(Color.RED);
			g.drawRect(0, 0, 200, 80);
			g.setColor(prev);
		}
		
	}
	
}
