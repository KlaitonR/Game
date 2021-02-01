package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Npc extends Entity{
	
	public String[] frases = new String[1];
	public boolean showMessage;
	public boolean offMessage;
	public int curIndexMsg;
	public int fraseIndex = 0;
	public int time = 0;
	public int maxTime = 5;
	
	public Npc(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.depth = 1;
		frases[0] = "Você irá morrer fazendeiro!";
		
	}
	
	public void tick() {
		
		int xPlayer = Game.player.getX();
		int yPlayer = Game.player.getY();
		
		if(Math.abs(xPlayer - this.x) < 20 && Math.abs(yPlayer - this.y) < 20) { 
			if(offMessage == false) {
				showMessage = true;
				offMessage = true;
			}
		}
		
		
		if(showMessage) {
			time++;
			if(time >= maxTime) {
				time = 0;
				if(curIndexMsg < frases[fraseIndex].length()) {
					curIndexMsg++;
				}else if(fraseIndex < frases.length - 1){
					fraseIndex++;
					curIndexMsg = 0;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		if(showMessage) {
		
			g.setColor(Color.black);
			g.fillRect(49, 89, 144, 42);
			g.setColor(Color.blue);
			g.fillRect(50, 90, 142, 40);
			
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 9));
			g.drawString(frases[fraseIndex].substring(0, curIndexMsg), 55, 100);
			g.setFont(new Font("Arial", Font.BOLD, 9));
			g.drawString("> ENTER par fechar <", 55, 125);
		}
		
	}

}
