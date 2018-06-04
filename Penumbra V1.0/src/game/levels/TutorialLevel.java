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
import game.gameobjects.characters.Player;

public class TutorialLevel extends GameState {

	// Environment
	private SolidBackground bg;
	private Camera camera;
	private TileMap tilemap;
	
	private Player player;
	
	private World world;
	
	public TutorialLevel(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		// Background
		bg = new SolidBackground(new Color(16, 13, 17), GameCanvas.WIDTH, GameCanvas.HEIGHT);
		
		// Tile Map
		tilemap = new TileMap(40, new TileSet("/Tilesets/cave_tiles_size40.gif", 40));
		tilemap.loadMap("/Maps/tutorial.map");
		
		// Camera
		camera = new Camera(GameCanvas.WIDTH, GameCanvas.HEIGHT);
		camera.setCameraBounds(0, 0, tilemap.getWidth(), tilemap.getHeight());
		camera.setPosition(0, 0);
		tilemap.setCamera(camera);
		
		// World
		world = new World();
		
		// Player
		player = new Player(tilemap, world);
		player.setPosition(100, 100);
		world.addPlayerUnit(player);
		
	}

	@Override
	public void update() {
		handleInput();
		
		player.update();
		
		// Resolve collision between units
		world.resolveCollisions();
		
		Camera c = tilemap.getCamera();
		c.setPosition(player.getX() - (c.getWidth() / 2), player.getY() - (c.getHeight() / 2));
	}

	@Override
	public void render(Graphics2D g) {
		bg.render(g);
		tilemap.render(g);
		player.render(g);
		
	}

	@Override
	public void handleInput() {
		player.setMovingLeft(KeyHandler.keyStates[KeyHandler.LEFT]);
		player.setMovingRight(KeyHandler.keyStates[KeyHandler.RIGHT]);
		player.setJumping(KeyHandler.keyStates[KeyHandler.UP]);
		player.nockArrow(KeyHandler.keyStates[KeyHandler.X]);
	}

}
