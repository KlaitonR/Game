package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Tree extends Entity{
	
	int life = 1;
	public boolean isColliding;

	public Tree(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void destroySelf() {
		Game.entities.remove(this);
		Game.entities.add(new Firewood(this.x, this.y, 16, 16, Entity.FIREWOOD_EN));
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
