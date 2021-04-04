package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class FishingSpot extends Entity{
	
	private int frames, maxFrames = 20, index, maxIndex = 4;
	private BufferedImage [] moveFishing;

	public FishingSpot(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		moveFishing = new BufferedImage[5];
		
		for(int i = 0; i<5; i++) {
			moveFishing[i] = Game.spritesheet.getSprite(240 + (i*16), 16, 16, 16);
		}
		
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if(index > maxIndex)
				index = 0;
		}
		
		g.drawImage(moveFishing[index] ,this.getX() - Camera.x, this.getY() - Camera.y , null);
		
	}

}
