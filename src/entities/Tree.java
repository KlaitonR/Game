package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.StumpTile;
import world.Tile;
import world.World;

public class Tree extends Entity{
	
	public int life = 1;
	public boolean isColliding;

	public Tree(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		height += 10;
		width -= 9;
		x += 4;
		y-=7;
	}
	
	public Tree(double x, double y, int width, int height, BufferedImage sprite, int ps, int xTile, int yTile) {
		super(x, y, width, height, sprite);
		height += 10;
		width -= 9;
		x += 4;
		y-=7;
		this.psTiles = ps;
		this.xTile = xTile;
		this.yTile = yTile;
	}
	
	public void destroySelf() {
		
		Game.entities.remove(this);
		
		//lenhas
		Firewood fireWood1 = new Firewood(this.x, this.y, 16, 16, Entity.FIREWOOD_EN);
		Game.entities.add(fireWood1);
		fireWood1.show = true;
		Firewood fireWood2 = new Firewood(this.x - 10, this.y - 5, 16, 16, Entity.FIREWOOD_EN);
		Game.entities.add(fireWood2);
		fireWood2.show = true;
		Firewood fireWood3 = new Firewood(this.x + 10, this.y + 5, 16, 16, Entity.FIREWOOD_EN);
		Game.entities.add(fireWood3);
		fireWood3.show = true;
		
		//Renderizar cepo
		World.tiles[this.psTiles] = new StumpTile(this.xTile*16, this.yTile*16, Tile.TILE_STUMP);
//		World.tiles[this.psTiles].show = true;
		
		//sementes
		Seed sd1 = new Seed(this.x - 6, this.y - 3, 16, 16, Entity.SEED1_EN);
		Game.entities.add(sd1);
		sd1.show = true;
		
		Seed sd2 = new Seed(this.x + 5, this.y + 5, 16, 16, Entity.SEED1_EN);
		Game.entities.add(sd2);
		sd2.show = true;
		
	}
	
	public void tick() {
		
		if(life == 0) {
			destroySelf();
		}
			
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

}
