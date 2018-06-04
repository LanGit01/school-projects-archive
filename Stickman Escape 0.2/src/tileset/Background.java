package tileset;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	// Position
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	// Dimensions
	private int width;
	private int height;
	
	// Image
	private BufferedImage bg;
	private double xscale;
	private double yscale;
	
	
	public Background(String s, double xscale, double yscale){
		this.xscale = xscale;
		this.yscale = yscale;
		try {
			bg = ImageIO.read(getClass().getResourceAsStream(s));
			width = bg.getWidth();
			height = bg.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y){
		this.x = (x * xscale) % width;
		this.y = (y * yscale) % height;
	}
	
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public double getx(){ return x; }
	public double gety(){ return y; }
	
	public void update(){
		x += dx;
		y += dy;
		
		if(x < -width){
			x += width;
		}else
		if(x > width){
			x -= width;
		}
		
		if(y < -height){
			y += height;
		}else
		if(y > height){
			y -= height;
		}
	}
	
	public void draw(java.awt.Graphics2D g){
		g.drawImage(bg, (int)x, (int)y, null);
		
		// Draw multiple copies for scrolling backgrounds
		if(x < 0){
			g.drawImage(bg, (int)x + width, (int)y, null);
		}else
		if(x > 0){
			g.drawImage(bg, (int)x - width, (int)y, null);
		}
		
		if(y < 0){
			g.drawImage(bg, (int)x, (int)y + height, null);
		}else
		if(y > 0){
			g.drawImage(bg, (int)x, (int)y - height, null);
		}
	}
}
