package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.World;

public class Tree extends Entity{
	
	public int life = 1;
//	public boolean isColliding;

	public Tree(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		height += 10;
		width -= 9;
		x += 4;
		y-=7;
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
		sp.tipo = "cepo";
		Game.entities.add(sp);
		sp.show = true;
		sp.psTiles = psTiles;
		
		//sementes
		Seed sd1 = new Seed(this.x - 6, this.y - 3, 16, 16, seed);
		Game.entities.add(sd1);
		sd1.show = true;
		
		Seed sd2 = new Seed(this.x + 5, this.y + 5, 16, 16, seed);
		Game.entities.add(sd2);
		sd2.show = true;
		
		if(this instanceof Oak) {
			fireWood1.tipo = "lenha de carvalho";
			fireWood1.id = 1;
			fireWood2.tipo = "lenha de carvalho";
			fireWood2.id = 1;
			fireWood3.tipo = "lenha de carvalho";
			fireWood3.id = 1;
			sd1.tipo = "semente de carvalho";
			sd1.id = 10;
			sd2.tipo = "semente de carvalho";
			sd2.id = 10;
		}else if (this instanceof Pine) {
			fireWood1.tipo = "lenha de pinheiro";
			fireWood1.id = 11;
			fireWood2.tipo = "lenha de pinheiro";
			fireWood2.id = 11;
			fireWood3.tipo = "lenha de pinheiro";
			fireWood3.id = 11;
			sd1.tipo = "semente de pinheiro";
			sd1.id = 12;
			sd2.tipo = "semente de pinheiro";
			sd2.id = 12;
		}
		
		World.tiles[psTiles].en = null;
		Game.entities.remove(this);
		
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

}
