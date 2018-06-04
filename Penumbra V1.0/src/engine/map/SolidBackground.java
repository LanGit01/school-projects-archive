package engine.map;

import java.awt.Color;
import java.awt.Graphics2D;

public class SolidBackground {

	private Color color;
	private int width;
	private int height;
	
	public SolidBackground(Color color, int width, int height){
		this.color = color;
		this.width = width;
		this.height = height;
	}
	
	public void render(Graphics2D g){
		Color prev = g.getColor();
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.setColor(prev);
	}
}
