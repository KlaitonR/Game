package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import main.Game;
import main.Menu;
import world.Camera;

public class UI {
	
	private BufferedImage [] button;
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixelfont.ttf");
	public Font newfont;
	
	public UI(Spritsheet spritButton) {
		
		button = new BufferedImage[2];
		button[0] = Game.spritButton.getSprite(0, 0, 5, 5);
		button[1] = Game.spritButton.getSprite(0, 5, 5, 5);
		
		try {
			newfont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(14f);
		}catch (FontFormatException f) {
			f.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	private void lifeEnemy(Graphics g) {
		//Life do Enemy
		for(int i = 0; i < Game.enemies.size(); i++) {
			g.setColor(Color.black); 
			g.fillRect((int)Game.enemies.get(i).getX() + 2 - Camera.x, (int)Game.enemies.get(i).getY() - 5 - Camera.y, 12, 3);
			g.setColor(Color.red);
			g.fillRect((int)Game.enemies.get(i).getX() + 3 - Camera.x, (int)Game.enemies.get(i).getY() - 4 - Camera.y, 10, 1);
			g.setColor(Color.green);
			g.fillRect((int)Game.enemies.get(i).getX() + 3 - Camera.x, (int)Game.enemies.get(i).getY() - 4 - Camera.y, (int)((Game.enemies.get(i).life/Game.enemies.get(i).maxLife)*10), 1);
		}
	}
	
	private void timeSystem(Graphics g) {
		if(Game.gameState.equals("NORMAL") || Menu.pause) {
			g.setFont(new Font("arial", Font.BOLD, 9));
			g.setColor(Color.white);
			
			if(Game.hour < 10 && Game.minute < 10) 
				g.drawString("Hora: 0" + Game.hour + ":0" + Game.minute,  20,  30 );
			if(Game.hour >= 10 && Game.minute < 10) 
				g.drawString("Hora: " + Game.hour + ":0" + Game.minute,  20,  30 );
			if(Game.hour < 10 && Game.minute >= 10) 
				g.drawString("Hora: 0" + Game.hour + ":" + Game.minute,  20,  30 );
			if(Game.hour >= 10 && Game.minute >= 10) 
				g.drawString("Hora: " + Game.hour + ":" + Game.minute,  20,  30 );
		}
	}
	
	private void invSystem(Graphics g) {
		if(Game.player.handItem != null) {
			g.setFont(newfont);
			g.setColor(Color.white);
			//Ajudar a centralizar o texto
			if(Game.player.handItem.length() > 5)
				g.drawString(Game.player.handItem, 105, 132);
			else
				g.drawString(Game.player.handItem, 115, 132);
		}
				
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(45, 135, 150, 20);
				
		g.setColor(Color.black); 
		g.fillRect(45, 135, 1, 20);
		g.setColor(Color.black); 
		g.fillRect(195, 135, 1, 20);
		g.setColor(Color.black); 
		g.fillRect(45, 154, 150, 1);
		g.setColor(Color.black); 
		g.fillRect(45, 135, 150, 1);
				
		for(int i=1; i<5; i++) {
			g.setColor(Color.black); 
			g.fillRect(45 + (i*30), 135, 1, 20);
		}
				
		//Apenas renderizando oque foi colocado no buffer do inventario
		for(int i=0; i < Game.player.inv.length; i++) {
					
			g.drawImage(Game.player.inv[i], 52 + (i*30), 137, null);
					
			if(Game.player.handIndexItem == i) { //Indica qual item está na mão do Player
				g2.setColor(new Color(255,255,255,150));
				g2.fillRect(46 + (i*30), 136, 1, 18);
				g2.fillRect(74 + (i*30), 136, 1, 18);
				g2.fillRect(47 + (i*30), 153, 27, 1); 
				g2.fillRect(47 + (i*30), 136, 27, 1);
			}		
		}
	}
	
	private void lifePlayer(Graphics g) {
		g.setColor(Color.black); 
		g.fillRect(7, 3, 72, 10);
		g.setColor(Color.red);
		g.fillRect(8, 4, 70, 8);
		g.setColor(Color.green);
		g.fillRect(8, 4, (int)((Game.player.life/Game.player.maxLife)*70), 8);
		g.setFont(newfont);
		g.setColor(Color.white);
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 30, 11);
		
		g.setFont(newfont);
		g.setColor(Color.white);
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 30, 11);
	}
	
	private void levelTab(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,150));
			
			if(Game.player.openLvls && !Game.player.offLvls) {
					
				g2.fillRect(5, 40, 75, 90);
					
				g.setColor(Color.black); 
				g.fillRect(4, 40, 77, 10);
				g.drawImage(button[1], 42 ,42 , null);
					
				g.setColor(Color.black);
				g.fillRect(7, 65, 72, 10);
				g.setColor(Color.blue);
				g.fillRect(8, 66, 70, 8);
				g.setColor(Color.yellow);
					
				double dif;
				dif = Game.player.maxExp[Game.player.levelPlayer] - Game.player.exp;
				
				if(dif <= Game.player.maxExp[Game.player.levelPlayer] && dif > 0){
					g.fillRect(8, 66,(int)((Game.player.exp/Game.player.maxExp[Game.player.levelPlayer])*70), 8);
				}else {
					g.fillRect(8, 66,(int)(((Game.player.exp + dif)/Game.player.maxExp[Game.player.levelPlayer])*70), 8);
				}

				g.setFont(newfont);
				g.setColor(Color.white);
				g.drawString("EXP  " + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 8, 73);
				
				//barra esquerda
				g.setColor(Color.black); 
				g.fillRect(4, 40, 1, 90);
				//barra direita
				g.setColor(Color.black); 
				g.fillRect(80, 40, 1, 90);
				//barra de cima
				g.setColor(Color.black); 
				g.fillRect(4, 40, 77, 1);
				//baara de baixo
				g.setColor(Color.black); 
				g.fillRect(4, 130, 77, 1);
				
				g.setFont(newfont);
				g.setColor(Color.orange);
				g.drawString("Nível do jogador:" + (int)(Game.player.levelPlayer + 1), 7, 60);
				
			}else {
				
				g.setColor(Color.black); 
				g.fillRect(4, 40, 77, 10);
				g.drawImage(button[0], 42 ,42 , null);
				Game.player.offLvls = false;
			}
	}
	
	public void systemBag(Graphics g) {
		g.setColor(new Color(0xC0C0C0));
		g.fillRect(75, 10, 100, 120);
				
		//barra esquerda
		g.setColor(Color.black); 
		g.fillRect(175, 2, 1, 129);
		//barra direita
		g.setColor(Color.black); 
		g.fillRect(74, 2, 1, 128);
		//barra de cima
		g.setColor(Color.black); 
		g.fillRect(74, 2, 101, 8);
		//baara de baixo
		g.setColor(Color.black); 
		g.fillRect(74, 130, 102, 1);
		
		g.setFont(newfont);
		g.setColor(Color.white);
		g.drawString("Mochila", 78, 9);
		
		for(int i=1; i<4; i++) { // linhas horizontais
			g.setColor(Color.white); 
			g.fillRect(75 + (i*25), 10, 1, 120);
		}
		
		for(int i=1; i<6; i++) { // linhas verticais
			g.setColor(Color.white); 
			g.fillRect(75, 10 + (i*20) , 100, 1);
		}
		
		//Apenas renderizando oque foi colocado no buffer do inventario
		for(int j=0; j < 6; j++) {
			for(int i=0; i < 4; i++) {
					g.drawImage(Game.player.bag[i][j], 80 + (i*25), 12 + (j*20), null);
				}
			}
		
	}
	
	
	public void render(Graphics g) {
		
		lifeEnemy(g);
		lifePlayer(g);
		timeSystem(g);
		invSystem(g);
		levelTab(g);
		
		if(Game.player.useBag)
			systemBag(g);
		
	}
}
