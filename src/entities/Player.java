package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import main.Sound;
import world.Camera;
import world.World;

public class Player extends Entity{
	
	public boolean rigth, left, down, up;
	public int rightDir = 0, leftDir = 1, upDir = 2, downDir = 3;
	public int dir = rightDir;
	public int maskx = 3, masky = 0, maskw = 10, maskh = 16;
	
	public double speed = 1.4;
	public double mx, my, moveMx = 0, moveMy = 0;
	
	public boolean jump;
	public boolean isJumping;
	public int z = 0;
	public int jumpFrames = 25, jumpCur =0;
	public int jumpSp = 1;
	public boolean jumpUp, jumpDown;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved;
	public boolean isDamage;
	private int damageFrames;
//	private boolean isCollidingTree;
	
	public boolean hasGun;
	public boolean hasAxe;
	public boolean shoot;
	public boolean mouseShoot;
	public boolean useLighter;
	public double life = 100, maxLife = 100;
	public int ammo = 1000;
	
	public boolean dropItem;
	public boolean getItem;
	public boolean useItem;
	public String handItem;
	public int handIndexItem;
	public boolean scrollItemLef;
	public boolean scrollItemDir;
	
	public boolean openLvls;
	public boolean offLvls = true;
	public String levelRoom;
	public int levelPlayer;
	
	public double exp;
	public int maxLevel = 4;
	public double [] maxExp = {100, 500, 1000, 5000, 10000};
	
	public String inventario[];
	
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
	
	private BufferedImage [] rightPlayerWithAxe;
	private BufferedImage [] leftPlayerWithAxe;
	private BufferedImage [] upPlayerWithAxe;
	private BufferedImage [] downPlayerWithAxe; 
	
	private BufferedImage [] rightPlayerDamageWithAxe;
	private BufferedImage [] leftPlayerDamageWithAxe;
	private BufferedImage [] upPlayerDamageWithAxe;
	private BufferedImage [] downPlayerDamageWithAxe;
	
	private BufferedImage rightPlayerJumping;
	private BufferedImage leftPlayerJumping;
	private BufferedImage upPlayerJumping;
	private BufferedImage downPlayerJumping; 
	
	private BufferedImage rightPlayerDamageJumping;
	private BufferedImage leftPlayerDamageJumping;
	private BufferedImage upPlayerDamageJumping;
	private BufferedImage downPlayerDamageJumping;
	
	private BufferedImage rightPlayerGunJumping;
	private BufferedImage leftPlayerGunJumping;
	private BufferedImage upPlayerGunJumping;
	private BufferedImage downPlayerGunJumping; 
	
	private BufferedImage rightPlayerGunDamageJumping;
	private BufferedImage leftPlayerGunDamageJumping;
	private BufferedImage upPlayerGunDamageJumping;
	private BufferedImage downPlayerGunDamageJumping;
	
	public BufferedImage [] inv = new BufferedImage[5];
	
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
		
		rightPlayerWithAxe = new BufferedImage[4];
		leftPlayerWithAxe = new BufferedImage[4];
		upPlayerWithAxe = new BufferedImage[4];
		downPlayerWithAxe = new BufferedImage[4];
		
		rightPlayerDamageWithAxe = new BufferedImage[4];
		leftPlayerDamageWithAxe = new BufferedImage[4];
		upPlayerDamageWithAxe = new BufferedImage[4];
		downPlayerDamageWithAxe = new BufferedImage[4];
		
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
			
			rightPlayerWithAxe[i] = Game.spritesheet.getSprite(160 + (i*16), 0, 16, 16);
			leftPlayerWithAxe[i] = Game.spritesheet.getSprite(160 + (i*16), 16, 16, 16);
			upPlayerWithAxe[i] = Game.spritesheet.getSprite(160 + (i*16), 32, 16, 16);
			downPlayerWithAxe[i] = Game.spritesheet.getSprite(160 + (i*16), 48, 16, 16);
			
			rightPlayerDamageWithAxe[i] = Game.spritesheet.getSprite(160 + (i*16), 64, 16, 16);
			leftPlayerDamageWithAxe[i] = Game.spritesheet.getSprite(160 + (i*16), 80, 16, 16);
			upPlayerDamageWithAxe[i] = Game.spritesheet.getSprite(160 + (i*16), 96, 16, 16);
			downPlayerDamageWithAxe[i] = Game.spritesheet.getSprite(160 + (i*16), 112, 16, 16);
			
		}
		
		rightPlayerJumping = Game.spritesheet.getSprite(96, 128, 16, 16);
		leftPlayerJumping  = Game.spritesheet.getSprite(112, 128, 16, 16);
		upPlayerJumping = Game.spritesheet.getSprite(128, 128, 16, 16);
		downPlayerJumping = Game.spritesheet.getSprite(144, 128, 16, 16);
		
		rightPlayerDamageJumping = Game.spritesheet.getSprite(96, 144, 16, 16);
		leftPlayerDamageJumping  = Game.spritesheet.getSprite(112, 144, 16, 16);
		upPlayerDamageJumping = Game.spritesheet.getSprite(128, 144, 16, 16);
		downPlayerDamageJumping = Game.spritesheet.getSprite(144, 144, 16, 16);
		
		rightPlayerGunJumping = Game.spritesheet.getSprite(48, 128, 16, 16);
		leftPlayerGunJumping  = Game.spritesheet.getSprite(48,  144, 16, 16);
		upPlayerGunJumping = Game.spritesheet.getSprite(48,  160, 16, 16);
		downPlayerGunJumping = Game.spritesheet.getSprite(48, 176, 16, 16);
		
		rightPlayerGunDamageJumping = Game.spritesheet.getSprite(48, 192, 16, 16);
		leftPlayerGunDamageJumping  = Game.spritesheet.getSprite(48, 208, 16, 16);
		upPlayerGunDamageJumping = Game.spritesheet.getSprite(48, 224, 16, 16);
		downPlayerGunDamageJumping = Game.spritesheet.getSprite(48, 240, 16, 16);
		
		inventario = new String[5];

	}
	
	public void checkCollisionLifePack() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			int index = checkPositionGetInv();
			if(getItem && index >= 0 && index <= inventario.length) {
				if(atual instanceof LifePack) {
					if(Entity.isColidding(this, atual)) {
						inventario[index] =  "lifePack";
						inv[index] = Game.spritesheet.getSprite(0, 16, 16, 16);
						handItem = inventario[index];
						handIndexItem = index;
						Game.entities.remove(atual);
					}
				}
			}
		}
		
	}
	
	public void checkCollisionLighter() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			int index = checkPositionGetInv();
			if(getItem && index >= 0 && index <= inventario.length) {
				if(atual instanceof Lighter) {
					if(Entity.isColidding(this, atual)) {
						inventario[index] =  "isqueiro";
						inv[index] = Game.spritesheet.getSprite(0, 128, 16, 16);
						handItem = inventario[index];
						handIndexItem = index;
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
					Sound.Clips.missAmo.play();
				}
			}
		}
		
	}
	
	public void checkCollisionGun() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			int index = checkPositionGetInv();
			if(getItem && index >= 0 && index <= inventario.length) {
				if(atual instanceof Wapon) {
					if(Entity.isColidding(this, atual)) {
						inventario[index] = "gun";
						inv[index] = Game.spritesheet.getSprite(16, 16, 16, 16);
						handItem = inventario[index];
						handIndexItem = index;
						Game.entities.remove(atual);
						Sound.Clips.reload.play();
					}
				}
			}
		}
		
	}
	
	public void checkCollisionAxe() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			int index = checkPositionGetInv();
			if(getItem && index >= 0 && index <= inventario.length) {
				if(atual instanceof Axe) {
					if(Entity.isColidding(this, atual)) {
						inventario[index] = "machado";
						inv[index] = Game.spritesheet.getSprite(0, 96, 16, 16);
						handItem = inventario[index];
						handIndexItem = index;
						Game.entities.remove(atual);
					}
				}
			}
		}
		
	}
	
	public void checkCollisionFirewood() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			int index = checkPositionGetInv();
			if(getItem && index >= 0 && index <= inventario.length) {
				if(atual instanceof Firewood) {
					if(Entity.isColidding(this, atual)) {
						inventario[index] = "lenha";
						inv[index] = Game.spritesheet.getSprite(0, 64, 16, 16);
						handItem = inventario[index];
						handIndexItem = index;
						Game.entities.remove(atual);
					}
				}
			}
		}
		
	}
	
	public void checkCollisionTree(){
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			
			if(atual instanceof Tree) {
				if(Entity.isColidding(this, atual)) {
//					isCollidingTree = true;
					if(useItem && handItem == "machado")
						((Tree) atual).life--;
				}
			}
		}
	}
	
	public void checkKillEnemy() {
		
		for(int i=0; i<maxExp.length; i++) {
			if(exp <= maxExp[i]) {
				levelPlayer = i;
				break;
			}
		}
		
	}
	
	public int checkPositionGetInv() {
			
		int index = -1;
			
		for(int i=0; i<inv.length; i++) {
			if(inv[i] == null) {
				index = i;
				break;
			}
		}

		return index;

	}
	
//	public int checkPositionDropInv() {
//		
//		int index = -1;
//			
//		for(int i=0; i<inv.length; i++) {
//			if(inv[i] != null) {
//				index =  i;
//				break;
//			}
//		}
//
//		return index;
//
//	}
	
	public void checkDropItem() {
		
		if(dropItem) {
			dropItem = false;
			int hi = handIndexItem; //Estas variaveis evitam que os valores sejam alocados nas posiõs erradas do vetor
			String h = handItem;    //após o processamento o checkScrollItem, no qual modifica o atributo handIndexItem do Player
			
			if(handItem != null){
				checkScrollItem(hi); // Método que modificará os atributos do Player
				if(inventario[hi] == "gun" && h == "gun") {
					inventario[hi] = null;
					inv[hi] = null;
					Game.entities.add(new Wapon(Game.player.getX(),Game.player.getY(), 16, 16, Entity.WEAPON_EN));
					Sound.Clips.dropItem.play();
					
				}else if(inventario[hi] == "lifePack" && h  == "lifePack") {
					inventario[hi] = null;
					inv[hi] = null;
					Game.entities.add(new LifePack(Game.player.getX(),Game.player.getY(), 16, 16, Entity.LIFE_PACK_EN));
					Sound.Clips.dropItem.play();
					
				}else if(inventario[hi] == "machado" && h  == "machado") {
					inventario[hi] = null;
					inv[hi] = null;
					Game.entities.add(new Axe(Game.player.getX(),Game.player.getY(), 16, 16, Entity.AXE_EN));
					Sound.Clips.dropItem.play();
					
				}else if(inventario[hi] == "lenha" && h  == "lenha") {
					inventario[hi] = null;
					inv[hi] = null;
					Game.entities.add(new Firewood(Game.player.getX(),Game.player.getY(), 16, 16, Entity.FIREWOOD_EN));
					Sound.Clips.dropItem.play();
					
				}else if(inventario[hi] == "isqueiro" && h  == "isqueiro") {
					inventario[hi] = null;
					inv[hi] = null;
					Game.entities.add(new Lighter(Game.player.getX(),Game.player.getY(), 16, 16, Entity.LIGHTER_EN));
					useLighter = false;
					Sound.Clips.dropItem.play();
				}
			}
		}
	}
	
	public void checkScrollItem(int handIndexItem) {
		
		if(handIndexItem-1 >= 0) {

			int index = handIndexItem-1;
			
			while(index >= 0 && inventario[index] == null) {
				index--;
			}
				
			if(index<0)
				index++;
		
			if(inventario[index] != null) {
				handItem = inventario[index];
				this.handIndexItem = index;
			}
		
		}else {
			
			int index = inventario.length-1;
			do {
				index--;
			}while(index > 0 && inventario[index] == null);
			
			if(inventario[index] != null) {
				handItem = inventario[index];	
				this.handIndexItem = index;
			}else {
				handItem = null;
				this.handIndexItem = 0;
			}
		}
		
	}
	
	public void checkUseItem() {
		
		if(useItem) {
			
			int hi = handIndexItem;
			String h = handItem;
			useItem = false;
				
			if(inventario[hi] == "lifePack" && h == "lifePack") {
				useLifePack(hi);
					
			}else {
						
				while (handItem == "lifePack") {
					checkScrollItem(handIndexItem);
				}
						
				hi = handIndexItem;
				h = handItem;
						
				if(inventario[hi] == "lifePack" && h == "lifePack") {
					useLifePack(hi);
				}
						
			}
			
			if(inventario[hi] == "isqueiro" && h == "isqueiro" && !useLighter) {
				useLighter = true;
				Sound.Clips.lighter.play();
			}else  if(inventario[hi] == "isqueiro" && useLighter){
				useLighter = false;
			}
			
		}
	}
	
	public void useLifePack(int index) {

		double dif;
		
		if(life <= 90) {
			checkScrollItem(index);
			life += 10;
			inventario[index] = null;
			inv[index] = null;
		}else if (life < 100){
			checkScrollItem(index);
			dif = 100 - life;
			life += dif;
			inventario[index] = null;
			inv[index] = null;
		}
			
	}
	
	public void checkScrollItem() {
		
		if(scrollItemLef) {
			scrollItemLef = false;
			
			if(handIndexItem - 1 >=0) {
				handIndexItem--;
				handItem = inventario[handIndexItem];
				
			}else {
				handIndexItem = inventario.length - 1;
				handItem = inventario[handIndexItem];
			}
			
		}else if(scrollItemDir) {
			scrollItemDir =  false;
			
			if(handIndexItem < inventario.length - 1) {
				handIndexItem++;
				handItem = inventario[handIndexItem];
				
			}else {
				handIndexItem = 0;
				handItem = inventario[handIndexItem];
			}
			
		}
		
	}
	
	public void checkItem() {
		
		if(handItem == "gun" && inventario[handIndexItem] == "gun") {
			hasGun = true;
		}else {
			hasGun = false;
		}
		
		if(handItem == "machado" && inventario[handIndexItem] == "machado") {
			hasAxe = true;
		}else {
			hasAxe = false;
		}
		
	}
	
	public void tick() {
		
		depth = 1;
		
		checkCollisionLifePack();
		checkCollisionAmmo();
		checkCollisionTree();
		checkCollisionFirewood();
		checkCollisionLighter();
		checkKillEnemy();
		
		checkDropItem();
		checkUseItem();
		checkScrollItem();
		checkItem();
		
		if(!hasGun)
			checkCollisionGun();
		
		if(!hasAxe)
			checkCollisionAxe();
		
		if(jump) {
			if(isJumping == false) {
				jump = false;
				isJumping = true;
				jumpUp = true;
			}
		}
		
		if(isJumping == true) {
			if(jumpUp) {
				jumpCur+= jumpSp;
			}else if(jumpDown) {
				jumpCur-= jumpSp;
				if(jumpCur<=0) {
					isJumping = false;
					jumpUp = false;
					jumpDown = false;
				}
			}
				z = jumpCur;
				if(jumpCur >= jumpFrames) {
					jumpUp = false;
					jumpDown = true;
				}
		}
		
		moved = false;
		
		if(rigth && World.isFree((int)(x+speed), this.getY(), this.z)) {
			moved =  true;
			//dir = rightDir; //Rotação de sprites com teclado
			x+=speed;
			
			
		}else if (left && World.isFree((int)(x-speed), this.getY(), this.z)) {
			moved =  true;
			//dir = leftDir; //Rotação de sprites com teclado
			x-=speed;
		
		}
			
		if(up && World.isFree(this.getX(),(int)(y-speed), this.z)) {
			moved =  true;
			//dir = upDir; //Rotação de sprites com teclado 
			y-=speed;

		}else if (down && World.isFree(this.getX(), (int)(y+speed), this.z)) {
			moved =  true;
			//dir = downDir; //Rotação de sprites com teclado
			y+=speed;
			
		}
		
		if(moved) {
//			Sound.passosGrama.loop();
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}else{
//			Sound.passosGrama.stop();
			index = 0;
			frames = 0;
		}
		
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
			//Sound.missAmo.play();
			
			if(hasGun && ammo > 0) {
				//Sound.missAmo.stop();
//				Sound.shootRifle.play();
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
	
		if(!isJumping) {
			
			if(!isDamage) {
					
				if(dir == rightDir) {
					
					if(hasGun) {
						g.drawImage(rightPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(rightPlayerWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if (dir == leftDir) {
						
					if(hasGun) {
						g.drawImage(leftPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(leftPlayerWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if(dir == upDir) {
						
					if(hasGun) {
						g.drawImage(upPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(upPlayerWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
					
				}else if(dir == downDir) {
						
					if(hasGun) {
						g.drawImage(downPlayerWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(downPlayerWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}
					
			}else {
					
				if(dir == rightDir) {
						
					if(hasGun) {
						g.drawImage(rightPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(rightPlayerDamageWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(rightPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if (dir == leftDir) { 
						
					if(hasGun) {
						g.drawImage(leftPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(rightPlayerDamageWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(leftPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if(dir == upDir) {
				
					if(hasGun) {
						g.drawImage(upPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(upPlayerDamageWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(upPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
					
				}else if(dir == downDir) {
						
					if(hasGun) {
						g.drawImage(downPlayerDamageWithGun[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else if(hasAxe){
						g.drawImage(downPlayerDamageWithAxe[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(downPlayerDamage[index], this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}
			}
			
		}else { // Se estiver pulando
			
			if(!isDamage) {
				
				if(dir == rightDir) {
					
					if(hasGun) {
						g.drawImage(rightPlayerGunJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(rightPlayerJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if (dir == leftDir) {
						
					if(hasGun) {
						g.drawImage(leftPlayerGunJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(leftPlayerJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if(dir == upDir) {
						
					if(hasGun) {
						g.drawImage(upPlayerGunJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(upPlayerJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
					
				}else if(dir == downDir) {
						
					if(hasGun) {
						g.drawImage(downPlayerGunJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(downPlayerJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}
					
			}else {
					
				if(dir == rightDir) {
						
					if(hasGun) {
						g.drawImage(rightPlayerGunDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(rightPlayerDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if (dir == leftDir) { 
						
					if(hasGun) {
						g.drawImage(leftPlayerGunDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(leftPlayerDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
						
				}else if(dir == upDir) {
					g.drawImage(upPlayerGunDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					if(hasGun) {
						g.drawImage(upPlayerDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}else if(dir == downDir) {
					
					if(hasGun) {
						g.drawImage(downPlayerGunDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}else {
						g.drawImage(downPlayerDamageJumping, this.getX() - Camera.x, this.getY() - Camera.y - z, null);
					}
				}
			}
		}
		
		if(isJumping) {
			g.setColor(Color.black);
			g.fillOval(this.getX() - Camera.x + 4, this.getY() - Camera.y + 12, 8, 8);
		}
		
//		g.setColor(Color.black);
//		g.fillRect(this.getX() - Camera.x + maskx, this.getY() - Camera.y + masky, maskw, maskh);
		
//		g.setColor(Color.black);
//		g.fillRect((int)mx, (int)my, 100 ,10);
		
	}	
}
