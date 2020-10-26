package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class BulletShoot extends Entity {
	
	private int dx;
	private int dy;
	private double speed = 4;
	private int life = 60, curLife = 0;
	
	public BulletShoot(double x, double y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		x += dx * speed;
		y += dy * speed;
		curLife++;
		if(curLife == life) {
			Game.bulletShootes.remove(this);
			return;	
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 3, 3);
	}

}
