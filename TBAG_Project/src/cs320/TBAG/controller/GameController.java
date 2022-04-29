package cs320.TBAG.controller;

import cs320.TBAG.model.Game;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import cs320.TBAG.model.Actor;
import cs320.TBAG.model.ActorStats;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Save;
import cs320.TBAG.model.Weapon;
import cs320.TBAG.model.Convo.ConversationTree;
import cs320.TBAG.database.DatabaseProvider;
import cs320.TBAG.database.IDatabase;
import cs320.TBAG.dbclass.InitDatabase;

public class GameController {
	private Game model;
	private InventoryController invCreator;
	IDatabase db = null;
	
	public GameController() {
		invCreator = new InventoryController();
		InitDatabase.init(1);
		db = DatabaseProvider.getInstance();
		
	}
	
	
	
	public void createPlayers() {
		List<Player> playerList = db.findAllPlayers();
		Iterator<Player> iterator = playerList.iterator();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			ActorStats stats = db.findActorStatsByPlayerId(player.getPlayerId());
			player.setActorStats(stats);
			Inventory inv = invCreator.getPlayerInventory(player.getPlayerId());
			player.setInventory(inv);
			
			for(Weapon weap : inv.getWeapons().values()) {
				if(weap.getEquipped()) {
					player.equipWeapon(weap);
				}
			}
			
			for(Equipment equipped : inv.getEquipment().values()) {
				if(equipped.getEquipped()) {
					player.equipEquipment(equipped);
				}
			}
			
			//Add player to game list
			model.addPlayer(player);
		}
	}
	
	public void createNPCs() {
		List<NPC> npcList = db.findAllNPCs();
		Iterator<NPC> iterator = npcList.iterator();
		while (iterator.hasNext()) {
			NPC npc = iterator.next();
			ActorStats stats = db.findActorStatsByNPCId(npc.getNPCId());
			npc.setActorStats(stats);
			Inventory inv = invCreator.getNPCInventory(npc.getNPCId());
			npc.setInventory(inv);
			
			for(Weapon weap : inv.getWeapons().values()) {
				if(weap.getEquipped()) {
					npc.equipWeapon(weap);
				}
			}
			
			for(Equipment equipped : inv.getEquipment().values()) {
				if(equipped.getEquipped()) {
					npc.equipEquipment(equipped);
				}
			}
			
			ConversationTree convoTree = db.constructConversationTreeByNPCID(npc.getNPCId());
			npc.setConversationTree(convoTree);
			
			//Add npc to game list
			model.addNPC(npc);
		}
	}
	

	public void setModel(Game model) {
		this.model = model;
	}

	public void newGame() {
		model.addEmptyPlayer();
		model.addEmptyNPC();
	}
	
	public void saveGame() {
		model.getSave().setActorsList(model.getActorsList());
		model.getSave().setInventories(model.getInventories());
		model.getSave().setMap(model.getMap());
		
	}
}