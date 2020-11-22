package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	public String[] options = {"Novo jogo", "Carregar jogo", "Sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length-1;
	
	public boolean up, down;
	
	public void tick() {
		
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.RED);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString("Game Clone", (Game.WIDTH*Game.SCALE) / 2 - 100, (Game.HEIGHT*Game.SCALE) / 2 - 170);
		
		//opções de menu
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 24));
		g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE) / 2 - 50, 200);
		g.drawString("Carregar Jogo", (Game.WIDTH*Game.SCALE) / 2 - 75, 240);
		g.drawString("Sair", (Game.WIDTH*Game.SCALE) / 2 - 15, 280);
		
		if(options[currentOption].equals("Novo jogo")) 
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 70, 200);
		else if(options[currentOption].equals("Carregar jogo"))
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 95, 240);
		else if(options[currentOption].equals("Sair"))
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 35, 280);
	}

}
