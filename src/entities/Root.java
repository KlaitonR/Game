package entities;

import java.awt.image.BufferedImage;

public class Root extends Entity{

	public Root(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		pack = true;
		qtPack = 9;
		id = 8;
	}

}
