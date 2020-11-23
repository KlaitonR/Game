package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity{
	
	public boolean rigth, left, down, up;
	public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3;
	public int dir = rightDir;
	public int maskx = 3, masky = 0, maskw = 10, maskh = 14;
	
	public double speed = 1.4;
	public double life = 100, maxLife = 100;
	public int ammo = 0;
	public double mx, my, moveMx = 0, moveMy = 0;
	
	private BufferedImage [] rightPlayer;
	private BufferedImage [] leftPlayer;
	private BufferedImage [] upPlayer;
	private BufferedImage [] downPlayer; 
	
	private BufferedImage [] rightPlayerDamage;
	private BufferedImage [] leftPlayerDamage;
	private BufferedImage [] upPlayerDamage;
	private BufferedImage [] downPlayerDamage;
	
	private BufferedImage [] rightPlayerWithGun;
	private BufferedImage [] leftPlayerWithGun;
	private BufferedImage [] upPlayerWithGun;
	private BufferedImage [] downPlayerWithGun; 
	
	private BufferedImage [] rightPlayerDamageWithGun;
	private BufferedImage [] leftPlayerDamageWithGun;
	private BufferedImage [] upPlayerDamageWithGun;
	private BufferedImage [] downPlayerDamageWithGun;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	public boolean isDamage;
	private int damageFrames;
	
	private boolean hasGun;
	public boolean shoot;
	public boolean mouseShoot;
	
	public String level;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		rightPlayerDamage = new BufferedImage[4];
		leftPlayerDamage = new BufferedImage[4];
		upPlayerDamage = new BufferedImage[4];
		downPlayerDamage = new BufferedImage[4];
		
		rightPlayerWithGun = new BufferedImage[4];
		leftPlayerWithGun = new BufferedImage[4];
		upPlayerWithGun = new BufferedImage[4];
		downPlayerWithGun = new BufferedImage[4];
		
		rightPlayerDamageWithGun = new BufferedImage[4];
		leftPlayerDamageWithGun = new BufferedImage[4];
		upPlayerDamageWithGun = new BufferedImage[4];
		downPlayerDamageWithGun = new BufferedImage[4];
		
		for(int i = 0; i<4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 32, 16, 16);
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 48, 16, 16);
			
			rightPlayerDamage[i] = Game.spritesheet.getSprite(32 + (i*16), 64, 16, 16);
			leftPlayerDamage[i] = Game.spritesheet.getSprite(32 + (i*16), 80, 16, 16);
			upPlayerDamage[i] = Game.spritesheet.getSprite(32 + (i*16), 96, 16, 16);
			downPlayerDamage[i] = Game.spritesheet.getSprite(32 + (i*16), 112, 16, 16);
			
			rightPlayerWithGun[i] = Game.spritesheet.getSprite(32 + (i*16), 128, 16, 16);
			leftPlayerWithGun[i] = Game.spritesheet.getSprite(32 + (i*16), 144, 16, 16);
			upPlayerWithGun[i] = Game.spritesheet.getSprite(32 + (i*16), 160, 16, 16);
			downPlayerWithGun[i] = Game.spritesheet.getSprite(32 + (i*16), 176, 16, 16);
			
			rightPlayerDamageWithGun[i] = Game.spritesheet.getSprite(32 + (i*16), 192, 16, 16);
			leftPlayerDamageWithGun[i] = Game.spritesheet.getSprite(32 + (i*16), 208, 16, 16);
			upPlayerDamageWithGun[i] = Game.spritesheet.getSprite(32 + (i*16), 224, 16, 16);
			downPlayerDamageWithGun[i] = Game.spritesheet.getSprite(32 + (i*16), 240, 16, 16);
		}
	}
	
	public void checkCollisionLifePack() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof LifePack) {
				if(Entity.isColidding(this, atual)) {
					
					if(life <= 90) {
						life += 10;
						Game.entities.remove(atual);
					}
				}
			}
		}
	}
	
	public void checkCollisionAmmo() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					ammo += 10;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void checkCollisionGun() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Wapon) {
				if(Entity.isColidding(this, atual)) {
					hasGun =  true;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public void tick() {
		
		moved = false;
		
		if(rigth && World.isFree((int)(x+speed), this.getY()) && World.isFreeBush((int)(x+speed), this.getY())) {
			moved =  true;
			//dir = rightDir; //Rotação de sprites com teclado
			x+=speed;
			
			
		}else if (left && World.isFree((int)(x-speed), this.getY()) && World.isFreeBush((int)(x-speed), this.getY())) {
			moved =  true;
			//dir = leftDir; //Rotação de sprites com teclado
			x-=speed;
		
		}
			
		if(up && World.isFree(this.getX(),(int)(y-speed)) && World.isFreeBush(this.getX(), (int)(y-speed))) {
			moved =  true;
			//dir = upDir; //Rotação de sprites com teclado 
			y-=speed;

		}else if (down && World.isFree(this.getX(), (int)(y+speed)) && World.isFreeBush(this.getX(), (int)(y+speed))) {
			moved =  true;
			//dir = downDir; //Rotação de sprites com teclado
			y+=speed;
			
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
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		
		if(!hasGun)
			checkCollisionGun();
		
		if(life <= 0) { // Game over
			life = 0;
			Game.gameState = "GAME OVER";
		}
		
		if(isDamage) {
			damageFrames++;
			if(this.damageFrames == 8) {
				damageFrames = 0; 
				isDamage = false;
			}
		}
		
//Atirar com o teclado
//		if(shoot) {
//			
//			shoot = false;
//			
//			if(hasGun && ammo > 0) {
//				ammo--;
//				int dx = 0;
//				int dy = 0;
//				int px = 0;
//				int py = 0;
//				
//				if(dir == rightDir) {
//					dx = 1;
//					px = 12;
//					py = 7;
//				}else if (dir == leftDir){
//					dx = -1;
//					px = 0;
//					py = 7;
//				}
//				
//				if (dir == downDir) {
//					dy = 1;
//					px = 6;
//					py = 6;
//				}else if (dir == upDir){
//					dy = -1;
//					px = 5;
//					py = 0;
//				}
//				
//				if(dir == rightDir || dir == leftDir) {
//					BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, 0);
//					Game.bulletShootes.add(bulletShoot);
//				}
//				
//				if(dir == downDir || dir == upDir) {
//					BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, 0, dy);
//					Game.bulletShootes.add(bulletShoot);
//				}
//			}
//		}
		
		//Rotacionar sprite
		double angle = Math.atan2(moveMy - (this.getY()+8 - Camera.y), moveMx - (this.getX()+8 - Camera.x));
		double direction = Math.toDegrees(angle);
		//System.out.println(direction);
		
		if(direction <= 35 && direction > -35)  //direita
			dir = rightDir;
		else if(direction <= -35 && direction > -145) //cima
			dir = upDir;
		else if (direction <= -145 || direction > 145)  //esquerda
			dir = leftDir;
		else if (direction <= 145 && direction > 50)  //baixo
			dir = downDir;
		
		if(mouseShoot) {
			
			mouseShoot = false;
			
			if(hasGun && ammo > 0) {
				ammo--;
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				int px = 0;
				int py = 0;
				
				if(dir == rightDir) {
					px = 12;
					py = 7;
				}else if (dir == leftDir){
					px = 0;
					py = 7;
				}
				
				if (dir == downDir) {
					px = 6;
					py = 6;
				}else if (dir == upDir){
					px = 5;
					py = 0;
				}
		
				BulletShoot bulletShoot = new BulletShoot(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
				Game.bulletShootes.add(bulletShoot);
				
			}
		}
		
	updateCamera();
		
	}
	
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y =  Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
	}
	
	@Override
	public void render(Graphics g) {
	
		if(!isDamage) {
				
			if(dir == rightDir) {
					
				if(hasGun) {
					g.drawImage(rightPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
					
			}else if (dir == leftDir) {
					
				if(hasGun) {
					g.drawImage(leftPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
					
			}else if(dir == upDir) {
					
				if(hasGun) {
					g.drawImage(upPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
				
			}else if(dir == downDir) {
					
				if(hasGun) {
					g.drawImage(downPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}
				
		}else {
				
			if(dir == rightDir) {
					
				if(hasGun) {
					g.drawImage(rightPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(rightPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
					
			}else if (dir == leftDir) {
					
				if(hasGun) {
					g.drawImage(leftPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(leftPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
					
			}else if(dir == upDir) {
				g.drawImage(upPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if(hasGun) {
					g.drawImage(upPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}else if(dir == downDir) {
					
				if(hasGun) {
					g.drawImage(downPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}else {
					g.drawImage(downPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				}
			}
		}
	}	
}
