package entity;

import java.awt.Color;

import main.GamePanel;
import tileset.TileMap;

public class Lava {
	private double height;
	private double speed;
	
	private TileMap tm;
	
	public Lava(TileMap tm){
		this.tm = tm;
		height = speed = 0;
	}
	
	public void setHeight(double height){
		this.height = height;
	}
	
	public void setLavaSpeed(double speed){
		this.speed = speed;
	}
	
	public void update(){
		height += speed;
	}
	
	public double getHeight(){
		return height;
	}
	
	public int getMapY(){
		return (int)(tm.getMapHeight() - height);
	}
	
	public void draw(java.awt.Graphics2D g){
		int y = (int)(tm.getMapHeight() - height - tm.getCameraY());
		int lavaDrawHeight = (int)tm.getCameraY() + GamePanel.HEIGHT - y;
		
		Color prev = g.getColor();
		
		if(lavaDrawHeight > 200){
			for(int i = 0; i < 20; i++){
				g.setColor(new Color(255, i, 0, 200));
				g.fillRect(0, y + (i * 2), GamePanel.WIDTH, 2);
			}
			for(int i = 0; i < 160; i++){
				g.setColor(new Color(255, i + 40, 0, 200));
				g.fillRect(0, y + 40 + i, GamePanel.WIDTH, 1);
			}

			
			g.fillRect(0, y + 200, GamePanel.WIDTH, lavaDrawHeight - 200);
		}else{
			g.setColor(new Color(255, 50, 0, 200));
			g.fillRect(0, y, GamePanel.WIDTH, lavaDrawHeight);
		}
		
		g.setColor(prev);
	}
}
