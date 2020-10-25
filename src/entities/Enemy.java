package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;

public class Enemy extends Entity{
	
	private double speed = 0.6;

	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
	}
	
	public boolean isColidding(int xNext, int yNext) {
		
		Rectangle enemyCurrent = new Rectangle(xNext, yNext, World.TILE_SIZE,  World.TILE_SIZE);
		
		for(int i =0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e==this)
				continue;
			
			Rectangle targetEnemy= new Rectangle(e.getX(),e.getY(), World.TILE_SIZE,  World.TILE_SIZE);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void tick() {
		
		if(Game.rand.nextInt(100) < 30) {
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
