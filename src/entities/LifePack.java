package entities;

import java.awt.image.BufferedImage;

public class LifePack extends Entity{

	public LifePack(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		pack = true;
		qtPack = 2;
		id = 3;
		
	}

}
