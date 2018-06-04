package main;


import javax.swing.JFrame;
import java.io.*;
	import java.awt.*;
	import java.util.*;
	import java.applet.*;
	import javax.swing.*;
	import java.awt.event.*; 
	import javax.swing.event.*;

public class Game extends JApplet{
	public void init()
		    {
		                 System.out.println("Applet initializing");
		                 getContentPane().add(new GamePanel());
		        
		        }
	
	public void start()
		        {
		                System.out.println("Applet starting");
		        }
		        //stop
		        public void stop()
		        {
		                System.out.println("Applet stopping");
		        }
		        //destroy
		        public void destroy()
		        {
		                System.out.println("Applet destroyed");
		        }
		        
		        public void paint(Graphics g) {
		        	   resize(GamePanel.WIDTH, GamePanel.HEIGHT);
		        }
	
	public static void main(String[] args){
		/*JFrame window = new JFrame("Stickman Escape v0.2");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		Game game = new Game();
		game.init();
		*/
	}
}

