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
	
	//Verifica se os slots est� cheio ou n�o
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
	
	//retorna o index do primeiro slot N�O nulo
	public int indexSlot() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] != null)
				return i;
		}
		
		return -1;
	}
	
	public void closeCreation(int x, int y) {
		
		if(CheckSlots()){ // verifica se possui itens na aba de cria��o
			if(!isFullInv()) { //verifica se o inventario possui espa�o ou est� cheio
				
				int slotsInv = slotsInv(); //verifica quantas posi��es est�o vazias
				
				if(slotsInv > 0) {
					
					while(slotsInv > 0) { //enquanto possuir posi��es vazias
						
						int indexRemove = indexSlot(); 
						if(indexRemove >= 0)
							removeItem(indexRemove); //remove da aba de cria��o para o inventario (j� faz a chegagem de pack)
						
						slotsInv--;
					}
					
					if(CheckSlots()) { //se ainda possui objetos na aba de cria��o, dropa os itens
						dropItens(x, y);
					}
					
				}else { // dropa todos os itens restantes
					dropItens(x, y);
				}
			}else {
				//dropa cada item individualmente se n�o possue um pack imcompleto no inventario
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
	
	//verifica quantos espa�os possuem 
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
	
	
	//Apenas retorna se est� cheio ou n�o
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
							
							//verifica se o item n�o est� no iventario
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
						//verifica se o id do pack do ch�o � o mesmo id do pack do inventario
						// e se o pack do inventario j� est� cheio
						if(atual.id == slot[i].id && slot[i].itensPack.size() < slot[i].qtPack) {
							//verifica se a soma do pack do ch�o e do pack do inventario +1 (o pr�prio pack do ch�o)
							//supera a qtPack do inventario
							//Se n�o superar, apenas adiciona os items da lista do pack do ch�o ao pack do inventario
							if((slot[i].itensPack.size() + atual.itensPack.size() + 1) <= slot[i].qtPack){
								
								//verifica se o item n�o est� no iventario
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
								
							}else { // Se superar, calcula os itens que sobrar�o
								
								//verifica se o pack � apenas o item, ou se tem algo na lista
								//Se n�o tiver nada na lista, apenas ignora e retorna false
								if(atual.itensPack.size() > 0) {
							
									int completa = 0;
									//Calcula quantos item completaram o pack do inventario
									completa = slot[i].qtPack - slot[i].itensPack.size(); 
					
									//adicionando os itens do pack no ch�o ao pack do inventario
									for(int j = 0; j < atual.itensPack.size(); j++) {
										if(j < completa) {
											slot[i].itensPack.add(atual.itensPack.get(j));
										}
									}
					
									int cont = 0;
	
									//removendo os itens do pack do ch�o que foram pegos do pack do ch�o para o pack do inventario
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
