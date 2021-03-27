package entities;

import java.awt.image.BufferedImage;

public class Lighter extends Entity{
	
	public Lighter(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		pack = true;
		qtPack = 64;
		id = 4;
		
	}

}
