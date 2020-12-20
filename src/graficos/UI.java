package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entities.Enemy;
import main.Game;

public class UI {
	
	public void render(Graphics g) {
		
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
		g.setColor(Color.red);
		g.fillRect(8, 17, 70, 8);
		g.setColor(Color.green);
		
		double dif;
		dif = Game.player.maxExp[Game.player.levelPlayer] - Game.player.exp;
		
		if(dif <= Game.player.maxExp[Game.player.levelPlayer] && dif > 0){
			g.fillRect(8, 17,(int)((Game.player.exp/Game.player.maxExp[Game.player.levelPlayer])*70), 8);
		}else {
			g.fillRect(8, 17,(int)(((Game.player.exp + dif)/Game.player.maxExp[Game.player.levelPlayer])*70), 8);
		}
		
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString("EXP      " + (int)Game.player.exp + "/" + (int)Game.player.maxExp[Game.player.levelPlayer], 10, 24);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 8));
		g.drawString("Nível do jogador: " + (int)(Game.player.levelPlayer + 1), 7, 35);
		
	}
}
