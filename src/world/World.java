package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import entities.Axe;
import entities.Bullet;
import entities.Enemy;
import entities.Entity;
import entities.LifePack;
import entities.Tree;
import entities.Wapon;
import graficos.Spritsheet;
import main.Game;

public class World {
	
	static public Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static int TILE_SIZE = 16;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int [] pixels = new int [map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels,0,map.getWidth());
			
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
			
					int pixelAtual = pixels[xx + (yy*map.getWidth())];
					
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
					
					if(pixelAtual == 0xFF000000) {
						
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
						
					}else if(pixelAtual == 0xFFFFFFFF) { //Parede
						
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
						
					}else if(pixelAtual == 0xFF0000FF) { //Player
						
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						
					}else if(pixelAtual == 0xFFFF0000) { //Inimigo
						
						Enemy en = new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						
					}else if(pixelAtual == 0xFFFF6A00) { //Arma
						
						Game.entities.add(new Wapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN));
						
					}else if(pixelAtual == 0xFF00FF00) { //Cura
						
						LifePack lifePack = new LifePack(xx*16, yy*16, 16, 16, Entity.LIFE_PACK_EN);
						Game.entities.add(lifePack);
		
					}else if(pixelAtual == 0xFFFFFF00) { //Munição
						
						Game.entities.add(new Bullet(xx*16, yy*16, 16, 16, Entity.BULLET_EN));
		
					}else if (pixelAtual == 0xFFB200FF){ //Portas
						tiles[xx + (yy*WIDTH)] = new DoorTile(xx*16, yy*16, Tile.TILE_DOOR);
						
					}else if(pixelAtual == 0xFFFF00DC) { // Arvore
						Game.entities.add(new Tree(xx*16, yy*16, 16, 16, Entity.TREE_EN));
					}
					else if(pixelAtual == 0xFF7F3300) { // Machado
						Game.entities.add(new Axe(xx*16, yy*16, 16, 16, Entity.AXE_EN));
					}
					else if(pixelAtual == 0xFF0094FF) { // Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*16, yy*16, Tile.TILE_WATER);
					}
					else if(pixelAtual == 0xFF4C1E00) { // Terra
						tiles[xx + (yy*WIDTH)] = new EarthTile(xx*16, yy*16, Tile.TILE_EARTH);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext, int zPlayer) {
		
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE -1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE -1) / TILE_SIZE;
		
		if (!((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile)) &&
				!((tiles[x1 + (y1*World.WIDTH)] instanceof WaterTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WaterTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WaterTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WaterTile)) ){
			return true;
		}
		
//		if(zPlayer > 0) { // pular por cima das paredes
//			return true;
//		}
		
		return false;
				
	}
	
	public static void restarGame(String level) {
		
		Game.entities =  new ArrayList<Entity>();
		Game.enemies =  new ArrayList<Enemy>();
		Game.spritesheet =  new Spritsheet("/spritesheet.png");
		//Game.player  = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.player.life = 100;
		Game.entities.add(Game.player);
		//Game.world =  new World("/map.png");
		Game.world = new World("/"+ level);
		return;
	}
	
	public void render(Graphics g) {
		
		int xStart = Camera.x >> 4;
		int yStart = Camera.y >> 4;
		
		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);
		
		for(int xx = xStart; xx <= xFinal; xx++) {
			for(int yy = yStart; yy <= yFinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) 
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];				
				tile.render(g);
			}
		}
		
		
		
	}
}
