package game.levels;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.gamestates.GameState;
import engine.gamestates.GameStateManager;
import engine.handlers.KeyHandler;
import engine.map.Camera;
import engine.map.SolidBackground;
import engine.map.TileMap;
import engine.map.TileSet;
import game.GameCanvas;
import game.gameobjects.FallingHazard;
import game.gameobjects.FallingTrap;
import game.gameobjects.TriggerZone;
import game.gameobjects.characters.Player;

public class FallingHazardTest extends GameState {

	private SolidBackground bg;
	private TileMap tm;
	private Camera camera;
	
	private Player player;
	private FallingTrap trap;
	private TriggerZone trigger;
	
	private World collisionHandler;
	
	public FallingHazardTest(GameStateManager gsm) {
		super(gsm);
		
		bg = new SolidBackground(Color.BLACK, GameCanvas.WIDTH, GameCanvas.HEIGHT);
		
		// Setup tilemap
		tm = new TileMap(30, new TileSet("/size30.gif", 30));
		tm.loadMap("/hazardmap.map");
		
		camera = new Camera(GameCanvas.WIDTH, GameCanvas.HEIGHT);
		camera.setCameraBounds(0, 0, tm.getWidth(), tm.getHeight());
		camera.setPosition(0, 0);
		tm.setCamera(camera);
		
		collisionHandler = new World();
		
		player = new Player(tm, collisionHandler);
		player.setPosition(100, 265);
		collisionHandler.addPlayerUnit(player);
	
		trap = new FallingTrap(tm);
		trap.setPosition(500, 200);
		
		trigger = new TriggerZone(trap, camera, 510, 475, 80, 10);
		collisionHandler.addZone(trigger);
	}
	

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		handleInput();
		
		player.update();
		
		trap.update();
		
		collisionHandler.resolveCollisions();
		
		Camera c = tm.getCamera();
		c.setPosition(player.getX() - (c.getWidth() / 2), player.getY() - (c.getHeight() / 2));
		
	}

	@Override
	public void render(Graphics2D g) {
		bg.render(g);
		tm.render(g);
		player.render(g);
		
		trigger.render(g);
		
		trap.render(g);
		
	}

	@Override
	public void handleInput(){
		player.setMovingLeft(KeyHandler.keyStates[KeyHandler.LEFT]);
		player.setMovingRight(KeyHandler.keyStates[KeyHandler.RIGHT]);
		player.setJumping(KeyHandler.keyStates[KeyHandler.UP]);
		player.nockArrow(KeyHandler.keyStates[KeyHandler.X]);
	}

}
