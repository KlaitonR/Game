package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Game;
import world.World;

public class Enemy extends Entity{
	
	private double speed = 0.7;
	
	private int maskx = 8, masky = 8, maskw = 8, maskh = 14;

	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
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
		
		if(Game.rand.nextInt(100) < 40) {
			if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) && !isColidding((int)(x+speed), this.getY())) {
				x += speed;
			}else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY()) && !isColidding((int)(x-speed), this.getY())){
				x -= speed;
			}
			
			if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed)) && !isColidding(this.getX(), (int)(y+speed))) {
				y += speed;
			}else if ((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed)) && !isColidding(this.getX(), (int)(y-speed))){
				y -= speed;
			}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, 16, 16);
	}


}
