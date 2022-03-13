package cs320.TBAG.model;

import cs320.TBAG.model.Map;
import cs320.TBAG.model.Actor;

public class Game {
	private String username;
	private String password;
	private Save save;
	private Map map;
	
	public Game() {
		
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setSave(Save save) {
		this.save = save;
	}
	
	public Save getSave() {
		return save;
	}
}