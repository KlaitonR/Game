package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;

public class Enemy extends Entity{
	
	private double speed = 0.7;
	
	private int maskx = 8, masky = 8, maskw = 8, maskh = 14;
	
	private BufferedImage [] rightPlayer;
	private BufferedImage [] leftPlayer;
	private BufferedImage [] upPlayer;
	private BufferedImage [] downPlayer; 
	public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3;
	public int dir = rightDir;
	private boolean moved = false;
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;

	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		for(int i = 0; i<4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(96 + (i*16), 0, 16, 16);
			leftPlayer[i] = Game.spritesheet.getSprite(96 + (i*16), 16, 16, 16);
			upPlayer[i] = Game.spritesheet.getSprite(96 + (i*16), 32, 16, 16);
			downPlayer[i] = Game.spritesheet.getSprite(96 + (i*16), 48, 16, 16);
		}
		
	}
	
	public boolean isColidding(int xNext, int yNext) {
		
		Rectangle enemyCurrent = new Rectangle(xNext + maskx, yNext + masky, maskw, maskh);
		
		for(int i =0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e==this)
				continue;
			
			Rectangle targetEnemy= new Rectangle(e.getX() + maskx,e.getY() + masky, maskw, maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void tick() {
		
		moved = false;
		
		if(Game.rand.nextInt(100) < 40) {
			if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) && !isColidding((int)(x+speed), this.getY())) {
				x += speed;
				dir = rightDir;
				moved =  true;
			}else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY()) && !isColidding((int)(x-speed), this.getY())){
				x -= speed;
				dir = leftDir;
				moved =  true;
			}
			
			if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed)) && !isColidding(this.getX(), (int)(y+speed))) {
				y += speed;
				dir = downDir;
				moved =  true;
			}else if ((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed)) && !isColidding(this.getX(), (int)(y-speed))){
				y -= speed;
				dir = upDir;
				moved =  true;
			}
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}else{
			index = 0;
			frames = 0;
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		if(dir == rightDir)
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		else if (dir == leftDir)
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		else if(dir == upDir)
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		else if(dir == downDir)
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, 16, 16);
	}


}
