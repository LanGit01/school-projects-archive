package game;

import engine.gamestates.GameStateManager;
import engine.main.AbstractGameCanvas;

import game.levels.*;

import java.awt.Graphics2D;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class GameCanvas extends AbstractGameCanvas {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	
	private GameStateManager gsm;
	
	public GameCanvas(){
		super();
		setPreferredSize(new Dimension(640, 480));
		setTargetFPS(60);
	}
	
	public void init(){
		gsm = new GameStateManager();
		gsm.addState(State.TUTORIAL.id, new TutorialLevel(gsm));
		gsm.changeState(State.TUTORIAL.id);
	}
	
	public void update(){
		gsm.update();
	}
	
	public void render(Graphics2D g){
		gsm.render(g);
	}
	
	public enum State{
		MENU(0), TUTORIAL(1);
		private int id;
		
		private State(int id){
			this.id = id;
		}
		
		public int value(){
			return id;
		}
	}
}
