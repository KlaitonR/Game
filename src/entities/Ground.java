package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.FloorTile;
import world.Tile;
import world.World;

public class Ground extends Entity{
	
	public int time;
	public int f1 = 1*60, f2 = 2*60, f3 = 3*60;
	public boolean plant;
	public int life = 100;
	public int cont;
	public String tipo;

	public Ground(double x, double y, int width, int height, BufferedImage sprite, int ps, int xTile, int yTile) {
		super(x, y, width, height, sprite);
		this.psTiles = ps;
		this.xTile = xTile;
		this.yTile = yTile;
		depth = 0;
	}
	
	public void plant() {
		
		if(plant && time < f3) {
			time++;
			depth = 1;
			if(time < f1 && time > 0) {
				this.setSprite(Entity.GROUND_F1_EN);
			}else if(time < f2) {
				this.setSprite(Entity.GROUND_F2_EN);
			}else if(time < f3) {
				this.setSprite(Entity.GROUND_F3_EN);
			}
			
			if (time == f3 && !Game.player.checkColisionGroundToTree(this)) {
				
				Tree tr = null;
				
				if(tipo.equals("semente de carvalho")) {
					 tr = new Tree(this.x, this.y, 16, 16, Entity.CARVALHO_EN, psTiles, this.xTile, this.yTile);
				}else if (tipo.equals("semente de pinheiro")){
					tr = new Tree(this.x, this.y, 16, 16, Entity.PINHEIRO_EN, psTiles, this.xTile, this.yTile);
				}
				
				Game.entities.add(tr);
				tr.show = true;
				World.tiles[psTiles].en = tr;
				
				plant = false;
				Game.entities.remove(this);
				
			}else if(time == f3){
				time--;
			}
		}
		
	}
	
	public void generateFloor() {
		
		cont++;
		
		if(!plant && cont >= 60){
			life --;
		}
		
		if(life == 0) {
			
			World.tiles[psTiles] = new FloorTile((int)x, (int)y, Tile.TILE_FLOOR);
			World.tiles[psTiles].show = true;
			Game.entities.remove(this);
			
		}

		
	}
	
	public void tick() {
		
		depth = 0;
		plant();
		generateFloor();
		
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

}
