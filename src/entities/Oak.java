package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Oak extends Tree{
	
	public Oak(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}

	public Oak(double x, double y, int width, int height, BufferedImage sprite, int ps, int xTile, int yTile) {
		super(x, y, width, height, sprite, ps, xTile, yTile);
		// TODO Auto-generated constructor stub
	}
	
	public void tick() {
		super.tick();
		if(life == 0) {
			destroySelf(Entity.FIREWOOD_CARVALHO_EN, Entity.SEED_CARVALHO_EN);
		}
			
	}
	
	public void render(Graphics g) {
		super.render(g);
	}
	
}
