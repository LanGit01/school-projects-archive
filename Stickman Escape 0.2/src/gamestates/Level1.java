package gamestates;

import handlers.Keys;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import main.GamePanel;
import tileset.*;
import entity.*;
import hud.HUD;

public class Level1 extends GameState {

	private Background bg;
	private TileMap tm;
	private Player player;
	private Lava lava;
	private HUD hud;
	
	public Level1(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		bg = new Background("/Backgrounds/cavebg3.jpg", -0.2, -0.5);
		bg.setPosition(0, 0);
		bg.setVector(0, 0);
		
		tm = new TileMap(40);
		tm.loadTiles("/Tilesets/brick2.png");
		tm.loadMap("/Maps/world1.map");
		tm.setCameraPosition(0, 0);
		
		player = new Player(tm);
		player.setPosition(tm.getTileSize() * 2, tm.getTileSize() * 36);
		
		lava = new Lava(tm);
		lava.setHeight(0);
		lava.setLavaSpeed(0.1);
		
		hud = new HUD(player);
		hud.setVisible(true);
	}
	
	public void update() {
		if(!player.isDead()){
			handleInput();
		}
		
		//tm.setCameraPosition((int)(player.getx() - GamePanel.WIDTH / 2), (int)(player.gety() - GamePanel.HEIGHT / 2));
		bg.update();
		player.update();
		lava.update();
		
		tm.setCameraPosition((int)(player.getx() - GamePanel.WIDTH / 2), (int)(player.gety() - GamePanel.HEIGHT / 2));
		tm.update();
		
		bg.setPosition(tm.getCameraX(), tm.getCameraY());
		
		Rectangle r = player.getCollisionBox();
		if(lava.getMapY() < r.y + r.height){
			player.die();
		}
		
	}
	
	public void draw(Graphics2D g) {
		bg.draw(g);
		tm.draw(g);
		player.draw(g);
		lava.draw(g);
		hud.draw(g);
		
		if(player.isDead()){
			g.setFont(new Font("Verdana", Font.BOLD, 50));
			g.drawString("GAME OVER", GamePanel.WIDTH / 2 - 180, GamePanel.HEIGHT / 2);
		}
	}
	
	public void handleInput() {
		player.setLeft(Keys.keyStates[Keys.LEFT]);
		player.setRight(Keys.keyStates[Keys.RIGHT]);
		player.setJumping(Keys.keyStates[Keys.UP]);
	}

}
