package entities;

//import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.Game;
import main.Sound;
import world.Camera;
import world.World;

public class Enemy extends Entity{
	
	private double speed = Game.rand.nextDouble();
	
	private int maskx = 4, masky = 0, maskw = 9, maskh = 16;
	
	private BufferedImage [] rightEnemy;
	private BufferedImage [] leftEnemy;
	private BufferedImage [] upEnemy;
	private BufferedImage [] downEnemy; 
	private BufferedImage [] rightEnemyDamage;
	private BufferedImage [] leftEnemyDamage;
	private BufferedImage [] upEnemyDamage;
	private BufferedImage [] downEnemyDamage;
	
	public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3;
	public int dir = rightDir;
	private boolean moved = false;
	private int frames = 0, maxFrames = 20, index = 0, maxIndex = 3;
	
	private int life = 10;
	boolean isDamage;
	private int damageFrames;

	public Enemy(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		
		rightEnemy = new BufferedImage[4];
		leftEnemy = new BufferedImage[4];
		upEnemy = new BufferedImage[4];
		downEnemy = new BufferedImage[4];
		
		rightEnemyDamage = new BufferedImage[4];
		leftEnemyDamage = new BufferedImage[4];
		upEnemyDamage = new BufferedImage[4];
		downEnemyDamage = new BufferedImage[4];
		
		for(int i = 0; i<4; i++) {
			rightEnemy[i] = Game.spritesheet.getSprite(96 + (i*16), 0, 16, 16);
			leftEnemy[i] = Game.spritesheet.getSprite(96 + (i*16), 16, 16, 16);
			upEnemy[i] = Game.spritesheet.getSprite(96 + (i*16), 32, 16, 16);
			downEnemy[i] = Game.spritesheet.getSprite(96 + (i*16), 48, 16, 16);
			
			rightEnemyDamage[i] = Game.spritesheet.getSprite(96 + (i*16), 64, 16, 16);
			leftEnemyDamage[i] = Game.spritesheet.getSprite(96 + (i*16), 80, 16, 16);
			upEnemyDamage[i] = Game.spritesheet.getSprite(96 + (i*16), 96, 16, 16);
			downEnemyDamage[i] = Game.spritesheet.getSprite(96 + (i*16), 112, 16, 16);
			
		}
	}
	
	public boolean isColiddingWithPlayer() {
		
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX() + Game.player.maskx, Game.player.getY()+ Game.player.masky, Game.player.maskw, Game.player.maskh);

		return enemyCurrent.intersects(player);
	}
	
	public boolean isColidding(int xNext, int yNext) {
		
		Rectangle enemyCurrent = new Rectangle(xNext + maskx, yNext + masky, maskw, maskh);
		
		for(int i =0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e==this && (e instanceof Enemy))
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
		
		if(speed<0.3) 
			speed +=0.25;
		else if(speed>0.8)
			speed -= 0.25;
		
		if(!isColiddingWithPlayer()) {
			
			if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY()) && !isColidding((int)(x+speed), this.getY())) {
				moved = true;
				x += speed;
				dir = rightDir;
			}else if ((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY()) && !isColidding((int)(x-speed), this.getY())){
				moved =  true;
				x -= speed;
				dir = leftDir;
			}
			
			if((int)y < Game.player.getY() && World.isFree(this.getX(), (int)(y+speed)) && !isColidding(this.getX(), (int)(y+speed))) {
				moved = true;
				y += speed;
				dir = downDir;
			}else if ((int)y > Game.player.getY() && World.isFree(this.getX(), (int)(y-speed)) && !isColidding(this.getX(), (int)(y-speed))){
				moved = true;
				y -= speed;
				dir = upDir;
			}
		}else {
			
			if(Game.rand.nextInt(100) < 10) {
				Sound.hurtEffect.play();
				Game.player.life -= Game.rand.nextInt(3);
				Game.player.isDamage = true;
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
		
		collidingBullet();
		
		if(life <= 0) 
			destroySelf();
		
		if(isDamage) {
			damageFrames++;
			if(this.damageFrames == 10) {
				damageFrames = 0; 
				isDamage = false;
			}
		}
		
	}
	
	public void destroySelf() {
		Game.entities.remove(this);
		Game.enemies.remove(this);
	}
	
	public void collidingBullet() {
		
		isDamage = false;
		
		for(int i=0; i < Game.bulletShootes.size(); i++) {
			if(Entity.isColidding(this, Game.bulletShootes.get(i))) {
				Game.bulletShootes.remove(i);
				life --;
				isDamage = true;
				return;
			}
		}
		
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		if(isDamage) {
			
			if(dir == rightDir)
				g.drawImage(rightEnemyDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if (dir == leftDir)
				g.drawImage(leftEnemyDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == upDir)
				g.drawImage(upEnemyDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == downDir)
				g.drawImage(downEnemyDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}else {
		
			if(dir == rightDir)
				g.drawImage(rightEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if (dir == leftDir)
				g.drawImage(leftEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == upDir)
				g.drawImage(upEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			else if(dir == downDir)
				g.drawImage(downEnemy[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, maskw, maskh);
//		
//		g.setColor(Color.black);
//		g.fillRect(Game.player.getX() - Camera.x + Game.player.maskx, Game.player.getY() - Camera.y + Game.player.masky, Game.player.maskw, Game.player.maskh);
	}

}
