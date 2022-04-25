package cs320.TBAG.controller;

import cs320.TBAG.model.Game;

import java.util.List;
import java.util.ArrayList;

import cs320.TBAG.model.Actor;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Save;
import cs320.TBAG.database.DatabaseProvider;
import cs320.TBAG.database.IDatabase;
import cs320.TBAG.dbclass.InitDatabase;

public class GameController {
	private Game model;
	
	IDatabase db = DatabaseProvider.getInstance();
	
	public void createPlayers() {
		List<Player> playerList = db.findAllPlayers();
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