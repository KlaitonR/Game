package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import entities.Bullet;
import entities.Enemy;
import entities.Entity;
import entities.LifePack;
import entities.Player;
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
		
					}else if (pixelAtual == 0xFFB200FF){ //Rochas
						tiles[xx + (yy*WIDTH)] = new RockTile(xx*16, yy*16, Tile.TILE_ROCK);
						
					}else if(pixelAtual == 0xFFFF00DC) {
						tiles[xx + (yy*WIDTH)] = new BushTile(xx*16, yy*16, Tile.TILE_BUSH);
						
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE -1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE -1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
				
		
	}
	
public static boolean isFreeBush(int xNext, int yNext) {
	
	int x1 = (xNext / TILE_SIZE);
	int y1 = (yNext / TILE_SIZE);
	
	int x2 = ((xNext + TILE_SIZE - 4) / TILE_SIZE);
	int y2 = (yNext / TILE_SIZE);
	
	int x3 = (xNext / TILE_SIZE );
	int y3 = ((yNext + TILE_SIZE - 4) / TILE_SIZE);
	
	int x4 = ((xNext + TILE_SIZE - 4) / TILE_SIZE);
	int y4 = ((yNext + TILE_SIZE - 4) / TILE_SIZE);
		
//		int x1 = xNext / 13;
//		int y1 = yNext / 13;
//		
//		int x2 = (xNext + 13 -1) / 13;
//		int y2 = yNext / 13;
//		
//		int x3 = xNext / 13;
//		int y3 = (yNext + 13 -1) / 13;
//		
//		int x4 = (xNext + 13 - 1) / 13;
//		int y4 = (yNext + 13 -1) / 13;
//		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof BushTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof BushTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof BushTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof BushTile));
	}


	
	public static void restarGame(String level) {
		
		Game.entities =  new ArrayList<Entity>();
		Game.enemies =  new ArrayList<Enemy>();
		Game.spritesheet =  new Spritsheet("/spritesheet.png");
		Game.player  = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
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
