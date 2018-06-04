package ui.simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class ProcessTimeBox {

	public static final Dimension BOX_DIMENSIONS = new Dimension(40, 40);
	
	private Color color;
	
	private int x;
	private int y;
	
	private double width;
	private double widthPerUpdate;
	private int duration;
	
	public ProcessTimeBox(Color color, int duration, double timeStep){
		this.color = color;
		this.duration = duration;
		this.widthPerUpdate = timeStep * BOX_DIMENSIONS.width;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public void update(){
		if(duration > 0){
			width += widthPerUpdate;
			duration--;
			//System.out.println(width);
		}
	}
	
	public boolean hasFinished(){
		return (duration == 0);
	}
	
	public void render(Graphics2D g){
		Color prev = g.getColor();
		g.setColor(color);
		g.fillRect(x, y, (int)width, BOX_DIMENSIONS.height);
		g.setColor(prev);
	}
	
}
