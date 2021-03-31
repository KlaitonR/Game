package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Pine extends Tree{
	
	public Pine(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}

	public Pine(double x, double y, int width, int height, BufferedImage sprite, int ps, int xTile, int yTile) {
		super(x, y, width, height, sprite, ps, xTile, yTile);
		// TODO Auto-generated constructor stub
	}

	
	public void tick() {
		super.tick();
		if(life == 0) {
			destroySelf(Entity.FIREWOOD_PINHEIRO_EN, Entity.SEED_PINHEIRO_EN );
		}
			
	}
	
	public void render(Graphics g) {
		super.render(g);
	}
	
	
}
