package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
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
	
	public void destroySelf(BufferedImage sprite, BufferedImage seed) {
		
		//lenhas
		Firewood fireWood1 = new Firewood(this.x, this.y, 16, 16, sprite);
		Game.entities.add(fireWood1);
		fireWood1.show = true;
		Firewood fireWood2 = new Firewood(this.x - 10, this.y - 5, 16, 16, sprite);
		Game.entities.add(fireWood2);
		fireWood2.show = true;
		Firewood fireWood3 = new Firewood(this.x + 10, this.y + 5, 16, 16, sprite);
		Game.entities.add(fireWood3);
		fireWood3.show = true;
		
		//Renderizar cepo
		Stump sp = new Stump(this.getX(), this.getY(), 16, 16, Entity.STUMP_EN);
		Game.entities.add(sp);
		sp.show = true;
		
		//sementes
		Seed sd1 = new Seed(this.x - 6, this.y - 3, 16, 16, seed);
		Game.entities.add(sd1);
		sd1.show = true;
		
		Seed sd2 = new Seed(this.x + 5, this.y + 5, 16, 16, seed);
		Game.entities.add(sd2);
		sd2.show = true;
		
		if(this instanceof Oak) {
			fireWood1.tipo = "lenha de carvalho";
			fireWood2.tipo = "lenha de carvalho";
			fireWood3.tipo = "lenha de carvalho";
			sd1.tipo = "semente de carvalho";
			sd2.tipo = "semente de carvalho";
		}else if (this instanceof Pine) {
			fireWood1.tipo = "lenha de pinheiro";
			fireWood2.tipo = "lenha de pinheiro";
			fireWood3.tipo = "lenha de pinheiro";
			sd1.tipo = "semente de pinheiro";
			sd2.tipo = "semente de pinheiro";
		}
		
		World.tiles[this.psTiles].en = null;
		Game.entities.remove(this);
		
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

}
