package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import main.Game;
import world.Camera;

public class UI {
	
	public void render(Graphics g) {
		
		//Life do Enemy
		for(int i = 0; i < Game.enemies.size(); i++) {
			g.setColor(Color.black); 
			g.fillRect((int)Game.enemies.get(i).getX() + 2 - Camera.x, (int)Game.enemies.get(i).getY() - 5 - Camera.y, 12, 3);
			g.setColor(Color.red);
			g.fillRect((int)Game.enemies.get(i).getX() + 3 - Camera.x, (int)Game.enemies.get(i).getY() - 4 - Camera.y, 10, 1);
			g.setColor(Color.green);
			g.fillRect((int)Game.enemies.get(i).getX() + 3 - Camera.x, (int)Game.enemies.get(i).getY() - 4 - Camera.y, (int)((Game.enemies.get(i).life/Game.enemies.get(i).maxLife)*10), 1);
		}
		
		g.setColor(Color.black); 
		g.fillRect(7, 3, 72, 10);
		g.setColor(Color.red);
		g.fillRect(8, 4, 70, 8);
		g.setColor(Color.green);
		g.fillRect(8, 4, (int)((Game.player.life/Game.player.maxLife)*70), 8);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 30, 11);
		
		g.setColor(Color.black);
		g.fillRect(7, 16, 72, 10);
		g.setColor(Color.blue);
		g.fillRect(8, 17, 70, 8);
		g.setColor(Color.yellow);
		
		double dif;
		dif = Game.player.maxExp[Game.player.levelPlayer] - Game.player.exp;
		
		if(dif <= Game.player.maxExp[Game.player.levelPlayer] && dif > 0){
			g.fillRect(8, 17,(int)((Game.player.exp/Game.player.maxExp[Game.player.levelPlayer])*70), 8);
		}else {
			g.fillRect(8, 17,(int)(((Game.player.exp + dif)/Game.player.maxExp[Game.player.levelPlayer])*70), 8);
		}

		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString("EXP   " + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 10, 24);
		
		g.setColor(Color.orange);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString("Nível do jogador: " + (int)(Game.player.levelPlayer + 1), 7, 35);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxLife, 30, 11);
		
		// Inventario do Player
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(45, 135, 150, 20);
		
		g.setColor(Color.darkGray); 
		g.fillRect(45, 135, 1, 20);
		g.setColor(Color.darkGray); 
		g.fillRect(194, 135, 1, 20);
		g.setColor(Color.darkGray); 
		g.fillRect(45, 154, 150, 1);
		g.setColor(Color.darkGray); 
		g.fillRect(45, 135, 150, 1);
		
		for(int i=0; i<5; i++) {
			g.setColor(Color.darkGray); 
			g.fillRect(45 + (i*30), 135, 1, 20);
		}
		
		//Apenas renderizando oque foi colocado no buffer
		for(int i=0; i < Game.player.inv.length; i++) {
			g.drawImage(Game.player.inv[i], 52 + (i*30), 137, null);
		}

	}
}
