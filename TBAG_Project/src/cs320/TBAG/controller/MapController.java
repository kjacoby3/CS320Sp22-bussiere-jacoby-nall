package cs320.TBAG.controller;

import cs320.TBAG.database.*;
import cs320.TBAG.model.Inventory;

public class MapController {

	private IDatabase db = null;
	public MapController() {
		db = new DerbyDatabase();
	}
	
	public String getRoomDescByID(int ID) {
		return null;
	}
}
