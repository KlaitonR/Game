package entities;

import java.awt.image.BufferedImage;
import main.Game;

public class Pig extends Mob{
	
	public int life = 2;
	public int exp = 100;
	public double speed;
	
	public boolean rigth, left, down, up;
	public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3;
	public int dir = rightDir;
	
	public int frames, maxFrames = 5, index, maxIndex = 3;
	public boolean moved;
	public boolean isDamage;
//	private int damageFrames;
	
	private BufferedImage [] rightPig;
	private BufferedImage [] leftPig;
	private BufferedImage [] upPig;
	private BufferedImage [] downPig; 
	
	private BufferedImage [] rightPigDamage;
	private BufferedImage [] leftPigDamage;
	private BufferedImage [] upPigDamage;
	private BufferedImage [] downPigDamage;
	
	public Pig(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPig = new BufferedImage[4];
		leftPig = new BufferedImage[4];
		upPig = new BufferedImage[4];
		downPig = new BufferedImage[4];
		
		rightPig = new BufferedImage[4];
		leftPigDamage = new BufferedImage[4];
		upPigDamage = new BufferedImage[4];
		downPigDamage = new BufferedImage[4]; 
		
		
		for(int i = 0; i<4; i++) {
			rightPig[i] = Game.spriteMobs.getSprite(0 + (i*16), 0, 16, 16);
			leftPig[i] = Game.spriteMobs.getSprite(0 + (i*16), 16, 16, 16);
			upPig[i] = Game.spriteMobs.getSprite(0 + (i*16), 32, 16, 16);
			downPig[i] = Game.spriteMobs.getSprite(0 + (i*16), 48, 16, 16);
			
			rightPigDamage[i] = Game.spriteMobs.getSprite(0 + (i*16), 64, 16, 16);
			leftPigDamage[i] = Game.spriteMobs.getSprite(0 + (i*16), 80, 16, 16);
			upPigDamage[i] = Game.spriteMobs.getSprite(0 + (i*16), 96, 16, 16);
			downPigDamage[i] = Game.spriteMobs.getSprite(0 + (i*16), 112, 16, 16);
		}
		
	}
	
	public void destroySelf() {
		if(life <= 0) {
			
			Beef pigBeef = new PigBeef((int)x + 3, (int)y -7, 16, 16, Entity.PIG_BEEF_EN);
			Game.entities.add(pigBeef);
			
		}
	}
	
	public void tick() {
		
	}
	

}
