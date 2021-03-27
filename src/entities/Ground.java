package entities;

import java.awt.image.BufferedImage;

import main.Game;
import world.World;

public class Ground extends Entity{
	
	public int time;
	public int f1 = 1*60, f2 = 2*60, f3 = 3*60;;
	public boolean plant;

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
				Tree tr = new Tree(this.x, this.y, 16, 16, Entity.TREE_EN, psTiles, this.xTile, this.yTile);
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
	
	public void tick() {
		
		depth = 0;
		plant();
		
	}

}
