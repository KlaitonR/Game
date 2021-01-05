package entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.Tile;
import world.World;

public class Entity {
	
	protected double x;
	protected double y;
	protected int z;
	protected int width;
	protected int height;
	protected int maskx, masky, maskw, maskh; 
	
	private BufferedImage sprite;
	public static BufferedImage LIFE_PACK_EN = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(16, 16, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(0, 32, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(6*16, 16, 16, 16);
	public static BufferedImage TREE_EN = Game.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage FIREWOOD_EN = Game.spritesheet.getSprite(0, 64, 16, 16);
	public static BufferedImage AXE_EN = Game.spritesheet.getSprite(0, 96, 16, 16);
	
	public Entity (double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.maskw = width;
		this.maskh = height;
	}
	
	public void setMask(int maskx, int masky, int maskw, int maskh) {
		this.maskx = maskx;
		this.masky = masky;
		this.maskw = maskw;
		this.maskh = maskh;
	}
	
	public static boolean isColidding(Entity e1, Entity e2) {
		
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.maskw, e1.maskh);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.maskw, e2.maskh);
		
		if(e1Mask.intersects(e2Mask) && e1.z == e2.z) {
			return true;
		}else {
			return false;	
		}
		
	}
	
	public static boolean isColiddingTile(Entity e, Tile t) {
		
		Rectangle eMask = new Rectangle(e.getX() + e.getMaskx(), e.getY() + e.getMasky(), e.getMaskw(), e.getMaskh());
		Rectangle tMask = new Rectangle(t.getX(), t.getY(), World.TILE_SIZE, World.TILE_SIZE);
		
		return eMask.intersects(tMask);
		
	}
	
	public double calculateDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1-x2) * (x1-x2) + (y1-y2) * (y1 - y2));
	}
	
	public void render(Graphics g) {
		
		g.drawImage(sprite ,this.getX() - Camera.x, this.getY() - Camera.y , null);
		
//		g.setColor(Color.red);
//		g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
		
	}
	
	public void tick() {
		
	}

	public int getX() {
		return (int)this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int)this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMaskx() {
		return maskx;
	}

	public void setMaskx(int maskx) {
		this.maskx = maskx;
	}

	public int getMasky() {
		return masky;
	}

	public void setMasky(int masky) {
		this.masky = masky;
	}

	public int getMaskw() {
		return maskw;
	}

	public void setMaskw(int maskw) {
		this.maskw = maskw;
	}

	public int getMaskh() {
		return maskh;
	}

	public void setMaskh(int maskh) {
		this.maskh = maskh;
	}
	
}