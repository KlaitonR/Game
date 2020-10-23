package world;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int [] pixels = new int [map.getWidth() * map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels,0,map.getWidth());
			
			for(int i=9; i< pixels.length; i++) {
				
				if(pixels[i] == 0xFF000000) { //Chão
					
				}
				
				if(pixels[i] == 0xFFFFFFFF) { //Parede
					
				}
				
				if(pixels[i] == 0xFF0000FF) { //Player
					
				}
				
				if(pixels[i] == 0xFFFF0000) { //Inimigo
					
				}
				
				if(pixels[i] == 0xFFFF6A00) { //Arma
					
				}
				
				if(pixels[i] == 0xFF00FF00) { //Cura
	
				}
				
				
				if(pixels[i] == 0xFFFFFF00) { //Munição
	
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
