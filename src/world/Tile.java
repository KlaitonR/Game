package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;

public class Tile {
	
	static public BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	static public BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
	static public BufferedImage TILE_TREE = Game.spritesheet.getSprite(0, 48, 16, 16);
	static public BufferedImage TILE_DOOR = Game.spritesheet.getSprite(16, 48, 16, 16);
	static public BufferedImage TILE_WATER = Game.spritesheet.getSprite(0, 80, 16, 16);
	static public BufferedImage TILE_EARTH = Game.spritesheet.getSprite(16, 80, 16, 16);
	static public BufferedImage TILE_STUMP = Game.spritesheet.getSprite(0, 144, 16, 16);
	
	public boolean show;
	
	protected BufferedImage sprite;
	protected int x,y,z;
	public int psTiles, xTile, yTile;
	public boolean open;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		if(show) 
			g.drawImage(sprite, x - Camera.x ,y - Camera.y ,null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
