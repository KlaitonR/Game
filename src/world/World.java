package world;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import entities.Axe;
import entities.Bullet;
import entities.BulletShoot;
import entities.Door;
import entities.Enemy;
import entities.Entity;
import entities.FishingRod;
import entities.FishingSpot;
import entities.Hoe;
import entities.LifePack;
import entities.Lighter;
import entities.Oak;
import entities.Particle;
import entities.Pine;
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
					tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
					tiles[xx + (yy*WIDTH)].xTile = xx;
					tiles[xx + (yy*WIDTH)].yTile = yy;
					
					
					if(pixelAtual == 0xFF000000) { //chão
						tiles[xx + (yy*WIDTH)] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].xTile = xx;
						tiles[xx + (yy*WIDTH)].yTile = yy;
						
						
					}else if(pixelAtual == 0xFFFFFFFF) { //Parede
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].xTile = xx;
						tiles[xx + (yy*WIDTH)].yTile = yy;
						
					}else if(pixelAtual == 0xFF0000FF) { //Player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						Game.player.psTiles = xx + (yy*WIDTH);
						
					}else if(pixelAtual == 0xFFFF0000) { //Inimigo
						Enemy en = new Enemy(xx*16, yy*16, 16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						en.psTiles =  xx + (yy*WIDTH);
						en.xTile = xx;
						en.yTile = yy;
						
					}else if(pixelAtual == 0xFFFF6A00) { //Arma
						Wapon wapon = new Wapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN);
						Game.entities.add(wapon);
						wapon.psTiles = xx + (yy*WIDTH);
						
					}else if(pixelAtual == 0xFF00FF00) { //Cura
						LifePack lifePack = new LifePack(xx*16, yy*16, 16, 16, Entity.LIFE_PACK_EN);
						Game.entities.add(lifePack);
						lifePack.psTiles = xx + (yy*WIDTH);
		
					}else if(pixelAtual == 0xFFFFFF00) { //Munição
						Bullet bullet = new Bullet(xx*16, yy*16, 16, 16, Entity.BULLET_EN);
						Game.entities.add(bullet);
						bullet.psTiles = xx + (yy*WIDTH);
		
					}else if (pixelAtual == 0xFFB200FF){ //Portas
						Door door = new Door(xx*16, yy*16, 16, 16, Entity.DOOR_EN);
						Game.entities.add(door);
						tiles[xx + (yy*WIDTH)].en = door;
						door.psTiles = xx + (yy*WIDTH);
						door.xTile = xx;
						door.yTile = yy;
						
					}else if(pixelAtual == 0xFFFF00DC) { // Arvores
						
						//método para randomizar a instancia de arvores
						
						if(Game.rand.nextInt(11) <=5) {
							Tree tree = new Oak(xx*16, yy*16, 16, 16, Entity.CARVALHO_EN);
							Game.entities.add(tree);
							tiles[xx + (yy*WIDTH)].en = tree;
							tree.psTiles = xx + (yy*WIDTH);
							tree.xTile = xx;
							tree.yTile = yy;
						}else {
							Tree tree = new Pine(xx*16, yy*16, 16, 16, Entity.PINHEIRO_EN);
							Game.entities.add(tree);
							tiles[xx + (yy*WIDTH)].en = tree;
							tree.psTiles = xx + (yy*WIDTH);
							tree.xTile = xx;
							tree.yTile = yy;
						}
						
					
					}
					
					else if(pixelAtual == 0xFF7F3300) { // Machado
						Axe axe = new Axe(xx*16, yy*16, 16, 16, Entity.AXE_EN);
						Game.entities.add(axe);
						axe.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = axe;
					}
					
					else if(pixelAtual == 0xFF0094FF) { // Água
						tiles[xx + (yy*WIDTH)] = new WaterTile(xx*16, yy*16, Tile.TILE_WATER);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
					}
					
					else if(pixelAtual == 0xFF4C1E00) { // Terra
						tiles[xx + (yy*WIDTH)] = new EarthTile(xx*16, yy*16, Tile.TILE_EARTH);
						tiles[xx + (yy*WIDTH)].psTiles = xx + (yy*WIDTH);
						
					}else if(pixelAtual == 0xFF808080) { //Isqueiro
						Lighter lighter = new Lighter(xx*16, yy*16, 16, 16, Entity.LIGHTER_EN);
						Game.entities.add(lighter);
						lighter.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = lighter;
					
					}else if (pixelAtual == 0xFF00FFFF) { // Local de pesca
						FishingSpot fs = new FishingSpot(xx*16, yy*16, 16, 16, Entity.FISHING_EN);
						Game.entities.add(fs);
						tiles[xx + (yy*WIDTH)].en = fs;
						fs.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = fs;
						
					}else if (pixelAtual == 0xFF5B7F00) {//Vara de pesca
						FishingRod fr = new FishingRod(xx*16, yy*16, 16, 16, Entity.FISHING_ROD_EN);
						Game.entities.add(fr);
						fr.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = fr;
						
						
					}else if (pixelAtual == 0xFF808042) {//Enxada
						Hoe hoe = new Hoe(xx*16, yy*16, 16, 16, Entity.HOE_EN);
						Game.entities.add(hoe);
						hoe.psTiles = xx + (yy*WIDTH);
						tiles[xx + (yy*WIDTH)].en = hoe;
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//Método randomico de gerar mapa
//
//		Game.player.setX(0);
//		Game.player.setY(0);
//		
//		WIDTH = 100;
//		HEIGHT = 100;
//		tiles = new Tile[WIDTH*HEIGHT];
//		
//		for(int xx =0; xx < WIDTH; xx++) {
//			for(int yy = 0; yy < HEIGHT; yy++) {
//				tiles[xx+yy*WIDTH] = new WallTile(xx*16, yy*16, Tile.TILE_WALL);
//			}
//		}
//		
//		int dir = 0;
//		int xx = 0, yy = 0;
//		
//		for(int i=0; i<3000; i++) {
//			
//			tiles[xx+yy*WIDTH] = new FloorTile(xx*16, yy*16, Tile.TILE_FLOOR);
//			
//			if(dir == 0) { //direita
//				if(xx < WIDTH) {
//					xx++;
//				}
//				
//			}else if (dir == 1) { //esquerda
//				if(xx > 0) {
//					xx--;
//				}
//				
//			}else if (dir == 2) { //baixo
//				if(yy < HEIGHT) {
//					yy++;
//				}
//				
//			}else if (dir == 3) { //cima
//				if(yy > 0) {
//					yy--;
//				}
//			}
//			
//			if(Game.rand.nextInt(100) < 30) {
//				dir = Game.rand.nextInt(4);
//			}
//			
//		}
		
	}
	
	public static void renderMiniMap() {
		
//		for(int i =0; i < Game.minimapaPixels.length; i++) {
//			Game.minimapaPixels[i] = 0xEFD551;
//		}
		
		for(int xx = 0; xx< WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				
				if(!tiles[xx + (yy*WIDTH)].show)
					Game.minimapaPixels[xx + (yy*WIDTH)] = 0x000000;
				else {
					Game.minimapaPixels[xx + (yy*WIDTH)] = 0xEFD551;
				}
				
				if(tiles[xx + (yy*WIDTH)] instanceof WallTile && tiles[xx + (yy*WIDTH)].show) {
					Game.minimapaPixels[xx + (yy*WIDTH)] = 0x87782D;
				}
				
				if(tiles[xx + (yy*WIDTH)] instanceof WaterTile && tiles[xx + (yy*WIDTH)].show) {
					Game.minimapaPixels[xx + (yy*WIDTH)] = 0x0094FF;
				}
				
				for(int i=0; i < Game.enemies.size(); i++) {
					int xEnemy = (int) (Game.enemies.get(i).x/16);
					int yEnemy = (int) (Game.enemies.get(i).y/16);
					
					int ps = (int)(xEnemy+ yEnemy*World.WIDTH);
					
					if(World.tiles[ps].show) {
						Game.minimapaPixels[ps] = 0xFF0000;
					}else {
						Game.minimapaPixels[ps] = 0x000000;
					}
					
				}
				
				if(yy == 0 || xx == 0 || xx == 99 || yy == 99 ) { //Vizualizar as bordas do mapa
					Game.minimapaPixels[xx + (yy*WIDTH)] = 0x87782D;
				}
				
			}
		}
		
		int xPlayer = Game.player.getX()/16;
		int yPlayer = Game.player.getY()/16;
		Game.minimapaPixels[xPlayer + (yPlayer*WIDTH)] = 0x00C2FF;
		
//		for(int i = 0; i < Game.enemies.size(); i++) {
//			Game.minimapaPixels[(Game.enemies.get(i).getX()/16) + ((Game.enemies.get(i).getY()/16)*WIDTH)] = 0xFF0000;
//		}
	}
	
	public static void generateParticles(int amount, int x, int y, int ps) {
		
		for(int i=0; i < amount; i++) {
			if(BulletShoot.collidingBullet) {
				Particle particle = new Particle(x + 8, y + 5, 1, 1, null);
				Game.particles.add(particle);
			}else {
				Particle particle = new Particle(x, y, 1, 1, null);
				Game.particles.add(particle);
			}
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
				(tiles[x4 + (y4*World.WIDTH)] instanceof WaterTile))){
			return true;
		}
		
//		if(zPlayer > 0) { // pular por cima das paredes
//			return true;
//		}
		
		return false;
				
	}
		
	public static boolean isFreeTree(int xNext, int yNext) {
				
		
		int x1 = (xNext + 4) / TILE_SIZE;
		int y1 = (yNext + 4) / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE - 6) / TILE_SIZE;
		int y2 = (yNext + 6) / TILE_SIZE;
		
		int x3 = (xNext + 6) / TILE_SIZE; 
		int y3 = (yNext + TILE_SIZE - 6) / TILE_SIZE; //direita
		
		int x4 = (xNext + TILE_SIZE - 6) / TILE_SIZE; // para baixo
		int y4 = (yNext + TILE_SIZE - 6) / TILE_SIZE;
		
		if (!((tiles[x1 + (y1*World.WIDTH)].en instanceof Tree) ||
				(tiles[x2 + (y2*World.WIDTH)].en instanceof Tree) ||
				(tiles[x3 + (y3*World.WIDTH)].en instanceof Tree) ||
				(tiles[x4 + (y4*World.WIDTH)].en instanceof Tree)) ){
			return true;
		}
			
		return false;
				
	}
	
	public static boolean isColiddingTile(Entity e, Tile t ) {
		
		Rectangle e1Mask = new Rectangle(e.getX(), e.getY() , 16, 16);
		Rectangle e2Mask = new Rectangle(t.getX() , t.getY() , 16,16);
		
		if(t instanceof FloorTile &&
				!(t.en instanceof Tree) &&
				e1Mask.intersects(e2Mask) &&
				e.getX() >= (t.getX()-1) &&
				e.getY() >= (t.getY())-1) {
			return true;
		}else {
			return false;	
		}
		
	}
	
	public static boolean checkCollidingFishingSpot(int xNext, int yNext) {
		
		int x1 = (xNext + 6) / TILE_SIZE;
		int y1 = (yNext + 6) / TILE_SIZE;
		
		int x2 = (xNext + TILE_SIZE -4) / TILE_SIZE;
		int y2 = (yNext  + 6)/ TILE_SIZE;
		
		int x3 = (xNext  + 6) / TILE_SIZE;
		int y3 = (yNext + TILE_SIZE -4) / TILE_SIZE;
		
		int x4 = (xNext + TILE_SIZE - 4) / TILE_SIZE;
		int y4 = (yNext + TILE_SIZE -4) / TILE_SIZE;
	
		if (!(((tiles[x1 + (y1*World.WIDTH)].en instanceof FishingSpot) ||
				(tiles[x2 + (y2*World.WIDTH)].en instanceof FishingSpot) ||
				(tiles[x3 + (y3*World.WIDTH)].en instanceof FishingSpot) ||
				(tiles[x4 + (y4*World.WIDTH)].en instanceof FishingSpot)))){
			return true;
		} 
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
		
		
//		g.setColor(Color.black);
//		g.fillRect(xStart - Camera.x  , yStart - Camera.y , xFinal, yFinal);
		
	}
}
