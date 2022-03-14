package cs320.TBAG.controller;

import cs320.TBAG.model.Game;
import cs320.TBAG.model.Actor;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Map;

public class GameController {
	private Game model;


	public void setModel(Game model) {
		this.model = model;
	}

	public void newGame() {
		Player player = new Player();
	}
}