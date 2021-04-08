package util;

import java.awt.image.BufferedImage;

import entities.Entity;
import entities.Potion;
import main.Game;
import main.Sound;
import world.World;

public class SystemCreation {
	
	public BufferedImage [] itens = new BufferedImage[5];
	public Entity slot [] = new Entity[5];
	public final int indexCraft = 4;
	
	public void addItem(Entity item) {
		
		if(isNull()) {
			int i = indexSlotNull();
			if(i<slot.length-1) {
				slot[i] = item;
				itens[i] = item.getSprite();
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
	
	//Verifica se tem algum slots vazio
	public boolean isNull() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] == null) {
				return true;
			}
		}
		
		return false;
	}
	
	//Verifica se todos os espa�os est�o vazios
	public boolean isAllNull() {
		
		int cont = 0;
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] == null) {
				cont++;
			}
		}
		
		if(cont == slot.length-1)
			return true;
		else 
			return false;
		
	}
	
	//Verifica se o slot de craft est� vazio
	public boolean isNullCraft() {
		
		if(slot[slot.length-1] == null)
			return true;
		
		return false;
	}
	
	//retorna o index do primeiro slot nulo
	public int indexSlotNull() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] == null)
				return i;
		}
		
		return -1;
	}
	
	//retorna o index do primeiro slot N�O nulo
	public int indexSlotNotNull() {
		
		for(int i=0; i<slot.length-1;i++) {
			if(slot[i] != null)
				return i;
		}
		
		return -1;
	}
	
	public void closeCreation(int x, int y) {
		
		if(CheckSlots()){ // verifica se possui itens na aba de cria��o
			if(!isFullInv()) { //verifica se o inventario possui espa�o ou est� cheio
				
				int slotsInv = qtSlotsInv(); //verifica quantas posi��es est�o vazias
				
				if(slotsInv > 0) {
					
					while(slotsInv > 0) { //enquanto possuir posi��es vazias
						
						int indexRemove = indexSlotNotNull(); 
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
				//dropa cada item individualmente se n�o possue um pack imcompleto no inventario ou espa�o vazio
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
	public int qtSlotsInv() {
		
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
	
	//tenta pegar o craft e jogar no inventario
	public void checkGetCraft(int x, int y) {
	
		if(slot[indexCraft] != null) {
			if(qtSlotsInv() > 0) { //Se tem espa�o na bag, em qualquer caso ser� adicionado ao inventario
								  //Caso n�o tenha espa�o, item n�o empulhaveis seram dropados
				int index = Game.player.checkPositionGetInv();
				if(!Game.player.checkPackInv(slot[indexCraft])) {
					if (index >= 0 && index <= Game.player.inventario.length) {
						Game.player.inventario[index] = slot[indexCraft];
						Game.player.inv[index] = slot[indexCraft].getSprite();
						Game.player.handItem = Game.player.inventario[index];
						Game.player.handIndexItem = index;
					}
				}
			}else if(!Game.player.checkPackInv(slot[indexCraft])) { //Tento adicionar em um pack, se n�o adicionar, dropa
				dropItem(x, y, indexCraft);
			}
		}
	}
	
	public boolean checkCraft(int index1, int index2) {
		
		for(int i=0; i<slot.length-1;i++) {
			if(index1 != i && index2 != i) {
				if(slot[i] != null)
					return false;
			}
		}
		
		return true;
	}
	
	public void craftPotion(int id1, int id2, int required1, int required2) {
		
		int index1 = checkStuff(id1);
		int index2 = checkStuff(id2);
		
		if(index1 >= 0 && index2 >= 0) {
			if(checkCraft(index1, index2)) {
				if(checkQtStuff(index1, required1) && checkQtStuff(index2, required2)) {
					
					Entity item = null;
					
					if((id1==8 && id2==10) || (id1==10 && id2==8)) {
						item = new Potion(0, 0, 16, 16, Entity.POTION_EN);
						item.tipo = "Po��o de regenera��o";
					}
					
					slot[indexCraft] = item;
					itens[indexCraft] = item.getSprite();
					
					if(Game.player.clickCraft) {
						
						Game.player.clickCraft = false;
						
						if(slot[index1].itensPack.size()>=required1) {
							int i=0;
							while(i<required1) {
								slot[index1].itensPack.remove(0);
								i++;
							}
						}else {
							slot[index1] = null;
							itens[index1] = null;
						}
						
						if(slot[index2].itensPack.size()>=required2) {
							int i=0;
							while(i<required2) {
								slot[index2].itensPack.remove(0);
								i++;
							}
						}else {
							slot[index2] = null;
							itens[index2] = null;
						}
						
						checkGetCraft(Game.player.getX(), Game.player.getY());
					}
				}else {
					slot[indexCraft] = null;
					itens[indexCraft] = null;
				}
			}else {
				slot[indexCraft] = null;
				itens[indexCraft] = null;
			}
		}else {
			slot[indexCraft] = null;
			itens[indexCraft] = null;
		}
	}
	
	//Retorna se o indice do objeto necess�rio se estiver na aba de craft
	public int checkStuff(int id) {
		
		for(int i=0; i<slot.length-1; i++) {
			if(slot[i] != null) {
				if(slot[i].id == id) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
	//retorna se a quantidade de iten � suficiente pra fazer o craft
	public boolean checkQtStuff(int index, int required ) {

		if(index >= 0) {
			if((slot[index].itensPack.size()+1) >= required)
				return true;
		}
		
		return false;
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
