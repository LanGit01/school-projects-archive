package game.levels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import engine.gamestates.*;
import engine.handlers.KeyHandler;
import engine.map.*;
import game.GameCanvas;
import game.gameobjects.characters.Enemy;
import game.gameobjects.characters.Ghoul;
import game.gameobjects.characters.Player;
import game.gameobjects.props.HUD;

public class PlayerTest extends GameState {
	
	private SolidBackground bg;
	private Camera camera;
	private TileMap tilemap;
	
	private Player player;
	
	private ArrayList<Enemy> enemies;
	
	private HUD hud;
	
	private World entities;
	
	public PlayerTest(GameStateManager gsm){
		super(gsm);
	}
	
	public void init(){
		bg = new SolidBackground(Color.BLACK, GameCanvas.WIDTH, GameCanvas.HEIGHT);
		
		// Setup the tilemap
		tilemap = new TileMap(40, new TileSet("/size40.gif", 40));
		tilemap.loadMap("/world.map");
		//tilemap.setDebug(true);
		
		// Setup the camera
		camera = new Camera(GameCanvas.WIDTH, GameCanvas.HEIGHT);
		camera.setCameraBounds(0, 0, tilemap.getWidth(), tilemap.getHeight());
		camera.setPosition(0, 0);
		tilemap.setCamera(camera);
		
		entities = new World();
		
		//Entity.setGravity(0.14);
		player = new Player(tilemap, entities);
		player.setPosition(180, 300);
		
		enemies = new ArrayList<Enemy>();
		Ghoul ghoul = new Ghoul(tilemap);
		ghoul.setPosition(500,  300);
		enemies.add(ghoul);
		
		hud = new HUD(player, ghoul);
		
		
		entities.addPlayerUnit(player);
		entities.addEnemyUnit(ghoul);
	}
	
	public void update(){
		handleInput();
		
		// Update units
		player.update();
		
		for(Enemy e : enemies){
			e.update();
		}
		
		// Resolve collision between units
		entities.resolveCollisions();
		
		
		// Remove enemies that should be removed
		Iterator<Enemy> eItr = enemies.iterator();
		while(eItr.hasNext()){
			if(eItr.next().toRemove()){
				eItr.remove();
			}
		}
	
		Camera c = tilemap.getCamera();
		c.setPosition(player.getX() - (c.getWidth() / 2), player.getY() - (c.getHeight() / 2));
		
	}
	
	public void render(Graphics2D g){
		bg.render(g);
		tilemap.render(g);
		player.render(g);
		
		for(Enemy e: enemies){
			e.render(g);
		}
		
		hud.render(g);
	}
	
	public void handleInput(){
		player.setMovingLeft(KeyHandler.keyStates[KeyHandler.LEFT]);
		player.setMovingRight(KeyHandler.keyStates[KeyHandler.RIGHT]);
		player.setJumping(KeyHandler.keyStates[KeyHandler.UP]);
		player.nockArrow(KeyHandler.keyStates[KeyHandler.X]);
	}
}
