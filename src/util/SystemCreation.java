package util;

import java.awt.image.BufferedImage;

import entities.Entity;
import main.Game;
import main.Sound;
import world.World;

public class SystemCreation {
	
	public BufferedImage [] itens = new BufferedImage[5];
	public Entity slot [] = new Entity[5];
	
	public void addItem(Entity item) {
		
		if(!isFull()) {
			for(int i=0; i<slot.length-1; i++) {
				if(slot[i] == null) {
					slot[i] = item;
					itens[i] = item.getSprite();
					break;
				}
			}
		}
	}
	
	public void removeItem(int indexRemove) {
		
		int index;
		
		if(slot[indexRemove] != null) {
			if(!Game.player.checkPackInv(slot[indexRemove])) {
				index = Game.player.checkPositionGetInv();
				if (index >= 0 && index <= Game.player.inventario.length) {
					Game.player.inventario[index] = slot[indexRemove];
					Game.player.inv[index] = slot[indexRemove].getSprite();
					Game.player.handItem = slot[indexRemove];
					Game.player.handIndexItem = index;
					slot[indexRemove] = null;
					itens[indexRemove] = null;
				}
			}
		}
	}
	
	//Verifica se os slots está cheio ou não
	public boolean isFull() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] == null) {
				return false;
			}
		}
		
		return true;
	}
	
	//retorna o index do primeiro slot nulo
	public int indexCreation() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] == null)
				return i;
		}
		
		return -1;
	}
	
	//retorna o index do primeiro slot NÃO nulo
	public int indexSlot() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] != null)
				return i;
		}
		
		return -1;
	}
	
	public void closeCreation(int x, int y) {
		
		if(CheckSlots()){ // verifica se possui itens na aba de criação
			if(!isFullInv()) { //verifica se o inventario possui espaço ou está cheio
				
				int slotsInv = slotsInv(); //verifica quantas posições estão vazias
				
				if(slotsInv > 0) {
					
					while(slotsInv > 0) { //enquanto possuir posições vazias
						
						int indexRemove = indexSlot(); 
						if(indexRemove >= 0)
							removeItem(indexRemove); //remove da aba de criação para o inventario (já faz a chegagem de pack)
						
						slotsInv--;
					}
					
					if(CheckSlots()) { //se ainda possui objetos na aba de criação, dropa os itens
						dropItens(x, y);
					}
					
				}else { // dropa todos os itens restantes
					dropItens(x, y);
				}
			}else {
				//dropa cada item individualmente se não possue um pack imcompleto no inventario
				for(int i=0; i<slot.length; i++) {
					if(!Game.player.checkPackInv(slot[i])) {
						dropItem(x, y, i);
					}
				}
			}
		}
	}
	
	public void dropItens(int x, int y) {
		
		for(int i=0; i<slot.length; i++) {
			if(slot[i]!=null) {
				slot[i].setX(x);
				slot[i].setY(y);
				slot[i].show = true;
				Game.entities.add(slot[i]);
				World.tiles[slot[i].xTile + (slot[i].yTile*World.WIDTH)].en = slot[i];
				slot[i] = null;
				itens[i] = null;
				Sound.Clips.dropItem.play();
			}
		}
		
	}
	
public void dropItem(int x, int y, int index) {
		
			if(slot[index] != null) {
				slot[index].setX(x);
				slot[index].setY(y);
				slot[index].show = true;
				Game.entities.add(slot[index]);
				World.tiles[slot[index].xTile + (slot[index].yTile*World.WIDTH)].en = slot[index];
				slot[index] = null;
				itens[index] = null;
				Sound.Clips.dropItem.play();
			}
		
	}
	
	//verifica quantos espaços possuem 
	public int slotsInv() {
		
		int slots = 0;
		
		for(int i=0; i<Game.player.inventario.length; i++) {
			if(Game.player.inventario[i] == null) {
				slots++;
			}
		}
		
		return slots;
		
	}
	
	//chegar se ainda a objetos no slot
	public boolean CheckSlots() {
		
		for(int i=0; i<slot.length; i++) {
			if(slot[i] != null) {
				return true;
			}
		}
		return false;
	}
	
	
	//Apenas retorna se está cheio ou não
	public boolean isFullInv() {
		
		for(int i=0; i<Game.player.inventario.length; i++) {
			if(Game.player.inventario[i] == null) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean checkPackCreation(Entity atual) {
		
		if(atual != null && atual.pack) {
			if(atual.itensPack.size() == 0) {
				for(int i=0; i<slot.length-1; i++) {
					if(slot[i] != null) {
						if(atual.id == slot[i].id &&
							slot[i].itensPack.size() < slot[i].qtPack){
							
							//verifica se o item não está no iventario
							for(int k=0; k<Game.player.inventario.length; k++) {
								if(Game.player.inventario[k] == atual) {
									Game.player.inv[k] = null;
									Game.player.inventario[k] = null;
								}
							}
							
							slot[i].itensPack.add(atual);
							return true;
						}
					}
				}
			}
			else if (atual.itensPack.size() > 0 &&   // Se o pack tiver  lista maior q zero e menor que o qtPack do inventario
			atual.itensPack.size() < atual.qtPack){
				for(int i=0; i<slot.length-1; i++) {
					if(slot[i] != null) {
						//verifica se o id do pack do chão é o mesmo id do pack do inventario
						// e se o pack do inventario já está cheio
						if(atual.id == slot[i].id && slot[i].itensPack.size() < slot[i].qtPack) {
							//verifica se a soma do pack do chão e do pack do inventario +1 (o próprio pack do chão)
							//supera a qtPack do inventario
							//Se não superar, apenas adiciona os items da lista do pack do chão ao pack do inventario
							if((slot[i].itensPack.size() + atual.itensPack.size() + 1) <= slot[i].qtPack){
								
								//verifica se o item não está no iventario
								for(int k=0; k<Game.player.inventario.length; k++) {
									if(Game.player.inventario[k] == atual) {
										Game.player.inv[k] = null;
										Game.player.inventario[k] = null;
									}
								}
								
								if(atual.itensPack.size() > 0) {
									slot[i].itensPack.addAll(atual.itensPack);
									atual.itensPack.removeAll(atual.itensPack);
								}
								slot[i].itensPack.add(atual);
								Game.entities.remove(atual);
								return true;
								
							}else { // Se superar, calcula os itens que sobrarão
								
								//verifica se o pack é apenas o item, ou se tem algo na lista
								//Se não tiver nada na lista, apenas ignora e retorna false
								if(atual.itensPack.size() > 0) {
							
									int completa = 0;
									//Calcula quantos item completaram o pack do inventario
									completa = slot[i].qtPack - slot[i].itensPack.size(); 
					
									//adicionando os itens do pack no chão ao pack do inventario
									for(int j = 0; j < atual.itensPack.size(); j++) {
										if(j < completa) {
											slot[i].itensPack.add(atual.itensPack.get(j));
										}
									}
					
									int cont = 0;
	
									//removendo os itens do pack do chão que foram pegos do pack do chão para o pack do inventario
									while (cont < completa) {
											atual.itensPack.remove(0);
											cont++;
									}	
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}

}
