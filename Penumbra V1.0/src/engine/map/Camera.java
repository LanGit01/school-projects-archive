package engine.map;

import java.awt.geom.Rectangle2D;

/**
 * A simple camera.
 * 
 * <p>It has a rectangular area called a viewbox. 
 * A camera's viewbox determines what should be drawn on the screen</p>
 * 
 * <p>The viewbox's position is bounded and can be changed by calling the method <code>setCameraBounds</code> 
 * and giving it the desired bounds. No part of the viewbox can be out of bounds.</p>
 * 
 * @author ace
 *
 */
public class Camera {

	// Camera position
	private double x;
	private double y;
	
	// Camera Dimensions
	private int width;
	private int height;
	
	// Bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	public Camera(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Sets camera position. This does not allow setting the camera view (or part of the view) outside
	 * the bounds. It instead sets the camera to the position inside the bounds closest to the argument position.
	 * 
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
		
		fixBounds();
	}
	
	/**
	 * Sets the maximum and minimum values of 
	 * @param xmin
	 * @param ymin
	 * @param xmax
	 * @param ymax
	 */
	public void setCameraBounds(int xmin, int ymin, int xmax, int ymax){
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}
	
	private void fixBounds(){
		if(x < xmin)		 	x = xmin;
		if(x + width > xmax) 	x = xmax - width;
		if(y < ymin)		 	y = ymin;
		if(y + height > ymax)	y = ymax - height;
	}
	
	/**
	 * @return a double precision rectangle of the camera's viewbox
	 */
	public Rectangle2D getViewBox(){
		return new Rectangle2D.Double(x, y, width, height);
	}
	
	public double getX(){ return x;	}
	public double getY(){ return y; }
	public int getWidth(){ return width; };
	public int getHeight(){ return height; };
}
