package cs320.TBAG.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.model.Map;
import cs320.TBAG.model.NPC;
import cs320.TBAG.controller.GameController;
import cs320.TBAG.controller.InventoryController;
import cs320.TBAG.model.Combat;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Game;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Item;
import cs320.TBAG.model.LevelUp;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Weapon;


public class GameServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	Game model = new Game();
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet");
		Map map = new Map();
		
		session = req.getSession();
		
		session.setAttribute("commandHistory", "");
		session.setAttribute("history", new ArrayList<String>());
		
		String roomDesc = map.getRoomDescription();
		req.setAttribute("roomMessage", roomDesc);
		req.getRequestDispatcher("/_view/Game.jsp").forward(req,resp);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("doPost");
		GameController controller = new GameController();
		session = req.getSession();
		
		PrintWriter terminalWriter = resp.getWriter();
		
		
		controller.setModel(model);
		String input = (String) req.getParameter("command");
		
		System.out.println("t" + input);
		
		Player player= model.getPlayer();
		Map map = model.getMap();
		String error = null;
		
		
		req.setAttribute("game", model);
		
		if(input != null) {
			
			
			if(input.equalsIgnoreCase("north")) {
				if(map.checkMove("n")){
					map.setNewRoom(map.getNewRoomID());
					updateHistory(input, map.getRoomDescription());
					req.setAttribute("roomMessage", map.getRoomDescription());
					req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					//resp.getWriter().write(map.getRoomDescription());
					//terminalWriter.write(map.getRoomDescription());
					
				}
				else {
					updateHistory(input, map.getRoomDescription());
					req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				}
				
			}
			
			else if(input.equalsIgnoreCase("south")) {
				if(input.equalsIgnoreCase("south")) {
					if(map.checkMove("s")){
						
						map.setNewRoom(map.getNewRoomID());
						req.setAttribute("roomMessage", map.getRoomDescription());
						updateHistory(input, map.getRoomDescription());
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
						/*resp.getWriter().write(map.getRoomDescription());
						terminalWriter.write(map.getRoomDescription());*/
					}
					else {
						System.out.println("Southelse");
						updateHistory(input, map.getRoomDescription());
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					}
				}
			}
			
			else if(input.equalsIgnoreCase("west")) {
				if(input.equalsIgnoreCase("west")) {
					if(map.checkMove("w")){
						
						map.setNewRoom(map.getNewRoomID());
						req.setAttribute("roomMessage", map.getRoomDescription());
						updateHistory(input, map.getRoomDescription());
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
						//resp.getWriter().write(map.getRoomDescription());
						//terminalWriter.write(map.getRoomDescription());
					}
					else {
						updateHistory(input, map.getRoomDescription());
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					}
					
				}
			}
			
			else if(input.equalsIgnoreCase("east")) {
				if(input.equalsIgnoreCase("east")) {
					if(map.checkMove("e")){
						//updateHistory(input);
						map.setNewRoom(map.getNewRoomID());
						req.setAttribute("roomMessage", map.getRoomDescription());
						updateHistory(input, map.getRoomDescription());
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
						//resp.getWriter().write(map.getRoomDescription());
						//terminalWriter.write(map.getRoomDescription());
					}
					else {
						updateHistory(input, map.getRoomDescription());
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					}
					
				}
			}
			
			else if(input.equalsIgnoreCase("inventory")) {
				InventoryController ic = new InventoryController();
				Inventory inventory = ic.getPlayerInventory(1);
				
				req.setAttribute("weapons", new ArrayList<>(inventory.getWeapons().values()));
				req.setAttribute("equipment", new ArrayList<>(inventory.getEquipment().values()));
				req.setAttribute("trophies", new ArrayList<>(inventory.getTrophies().values()));
				req.setAttribute("usables", new ArrayList<>(inventory.getUsables().values()));
				req.setAttribute("treasures", new ArrayList<>(inventory.getTreasures().values()));
				req.setAttribute("consumables", new ArrayList<>(inventory.getConsumables().values()));
				
				
				/*req.setAttribute("weapons", new ArrayList<>(player.getInventory().getWeapons().values()));
				req.setAttribute("equipment", new ArrayList<>(player.getInventory().getEquipment().values()));
				req.setAttribute("trophies", new ArrayList<>(player.getInventory().getTrophies().values()));
				req.setAttribute("usables", new ArrayList<>(player.getInventory().getUsables().values()));
				req.setAttribute("treasures", new ArrayList<>(player.getInventory().getTreasures().values()));
				req.setAttribute("consumables", new ArrayList<>(player.getInventory().getConsumables().values()));*/
				req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
				//req.getRequestDispatcher("/_view/inventory.jsp").forward(req,resp);
				//terminalWriter.write("inventory");
			}
			
			else if(input.equalsIgnoreCase("pickup")) {
				player.getInventory().addItem(new Weapon("banana", 1000000, 1000000,1,0,0, true));
				updateHistory(input);
				req.setAttribute("roomMessage", map.getRoomDescription());
				req.getRequestDispatcher("/_view/Game.jsp").forward(req,resp);
			}
			else if(input.equalsIgnoreCase("start")) {
				map.setCurrRoom(1);
				updateHistory(input, map.getRoomDescription());
				req.setAttribute("roomMessage",map.getRoomDescription());
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				//terminalWriter.write(map.getRoomDescription());
			}
			else if(input.equalsIgnoreCase("attack")) {
				updateHistory(input);
				req.setAttribute("game", model);
				
				//For purposes of testing, I am foregoing this test and creating a blank enemy
				//if(player.getLocation().getActorsInRoom().size() == 1) {
				
					//Gets only NPC from Room to be Actor2 in the Combat Model
					
					NPC enemy = new NPC();/* (NPC) player.getLocation().getActorsInRoom().get(0)*/;
					
					//Creates a new combat model to pass to CombatServlet
					Combat combatMod = new Combat(player, enemy);
					session.setAttribute("combatMod", combatMod);
					//Sets initial attributes for CombatServlet
					req.setAttribute("player", combatMod.getActor1().getName());
					req.setAttribute("enemy", combatMod.getActor2().getName());
					
					req.setAttribute("enemyHealth", combatMod.getActor2().getActorStats().getCurHP());
					req.setAttribute("playerHealth", combatMod.getActor1().getActorStats().getCurHP());
					
					req.setAttribute("enemyDMG", combatMod.getActor2().getActorStats().getDmg());
					req.setAttribute("playerDMG", combatMod.getActor1().getActorStats().getDmg());
					
					req.setAttribute("enemyDEF", combatMod.getActor2().getActorStats().getDef());
					req.setAttribute("playerDEF", combatMod.getActor1().getActorStats().getDef());
					
					req.setAttribute("enemySPD", combatMod.getActor2().getActorStats().getSpd());
					req.setAttribute("playerSPD", combatMod.getActor1().getActorStats().getSpd());
					
					req.setAttribute("enemyWeapon", combatMod.getActor2().getEqWeap().getName());
					req.setAttribute("playerWeapon", combatMod.getActor1().getEqWeap().getName());
					
					req.setAttribute("playerLVL", combatMod.getActor1().getActorStats().getCurLvl());
					req.setAttribute("playerEXP", combatMod.getActor1().getActorStats().getCurExp());
					req.setAttribute("playerMaxEXP", combatMod.getActor1().getActorStats().getMaxExp());
				//} else {
					// Return message asking for clarification of who should be attacked
				//}
				req.getRequestDispatcher("/_view/combat.jsp").forward(req, resp);
			}
			else if(input.equalsIgnoreCase("level up") || input.equalsIgnoreCase("levelup") ||
					input.equalsIgnoreCase("lvl up")) {
				LevelUp levelUpModel = new LevelUp(player);
				session.setAttribute("levelUpModel", levelUpModel);
				
				req.setAttribute("player", player.getName());
				
				req.setAttribute("level", player.getActorStats().getCurLvl());
				req.setAttribute("projLvl", levelUpModel.getProjLvl());
				
				req.setAttribute("curXP", player.getActorStats().getCurExp());
				req.setAttribute("maxXP", player.getActorStats().getMaxExp());
				
				req.setAttribute("curHP", player.getActorStats().getCurHP());
				req.setAttribute("maxHP", player.getActorStats().getMaxHP());
				
				req.setAttribute("dmg", player.getActorStats().getDmg());
				
				req.setAttribute("def", player.getActorStats().getDef());
				
				req.setAttribute("spd", player.getActorStats().getSpd());
				
				req.setAttribute("projCurHP", levelUpModel.getProjCurHP());
				req.setAttribute("projMaxHP", levelUpModel.getProjMaxHP());
				req.setAttribute("hpInc", levelUpModel.getHPInc());
				
				req.setAttribute("projDMG", levelUpModel.getProjDMG());
				req.setAttribute("dmgInc", levelUpModel.getDMGInc());
				
				req.setAttribute("projDEF", levelUpModel.getProjDEF());
				req.setAttribute("defInc", levelUpModel.getDEFInc());
				
				req.setAttribute("projSPD", levelUpModel.getProjSPD());
				req.setAttribute("spdInc", levelUpModel.getSPDInc());
				
				req.setAttribute("projCurXP", levelUpModel.getProjCurExp());
				req.setAttribute("projMaxXP", levelUpModel.getProjMaxExp());
				
				req.getRequestDispatcher("/_view/levelUp.jsp").forward(req, resp);
			}
			else if (input.startsWith("equip")){
				if (input.equalsIgnoreCase("equip")) {
					updateHistory("What do you want to equip?");
				} else {
					String[] splitStr = input.split(" ", 2);
					String equipName = splitStr[1];
					Boolean itemWasEquipped = false;
					Item equippedItem;
					if(player.getInventory().getWeapons().size() > 0) {
						for(Weapon i : player.getInventory().getWeapons().values()) {
							if (equipName.equalsIgnoreCase(i.getName())) {
								player.equipWeapon(i);
								itemWasEquipped = true;
								equippedItem = i;
								//updateHistory(i.getName() + " was equipped.");
								//req.setAttribute("roomMessage", map.getRoomDescription());
								//req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
							} 
						}
					}
					if(player.getInventory().getEquipment().size() > 0) {
						for(Equipment i : player.getInventory().getEquipment().values()) {
							if (equipName.equalsIgnoreCase(i.getName())) {
								player.equipEquipment(i);
								itemWasEquipped = true;
								equippedItem = i;
							}
						}
					}
					if(itemWasEquipped == false) {
						updateHistory("An item of " + equipName + " could not be found in your inventory. "
								+ "Try checking that the name of the item is correct.");
					} else {
						updateHistory(equipName + " was equipped");
					}
				}
				req.setAttribute("roomMessage", map.getRoomDescription());
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
			else if (input.startsWith("unequip")) {
				Weapon eqWeap = player.getEqWeap();
				Equipment equipped = player.getEquipped();
				if(input.equalsIgnoreCase("unequip")) {
					if (eqWeap.getName() == "Fists" && equipped.getName() == "Bare") {
						updateHistory("You cannot unequip Fists or Bare.");
					} else if (eqWeap.getName() == "Fists" && equipped.getName() != "Bare") {
						player.unequipEquipment();
						updateHistory(equipped.getName() + " was unequipped.");
					} else if (eqWeap.getName() != "Fists" && equipped.getName() == "Bare") {
						player.unequipWeapon();
						updateHistory(eqWeap.getName() + " was unequipped.");
					} else {
						updateHistory("What do you want to unequip?");
					}
				}
				else {
					String[] splitStr = input.split(" ", 2);
					String str = splitStr[1];
					if(str.equalsIgnoreCase("weapon") || str.equalsIgnoreCase(eqWeap.getName())) {
						if (eqWeap.getName() != "Fists") {
							player.unequipWeapon();
							updateHistory(eqWeap.getName() + " was unequipped");
						} else {
							updateHistory("You cannot unequip Fists");
						}
					} else if(str.equalsIgnoreCase("equipment") || str.equalsIgnoreCase(equipped.getName())) {
						if (equipped.getName() != "Bare") {
							player.unequipEquipment();
							updateHistory(equipped.getName() + " was unequipped.");
						} else {
							updateHistory("You cannot unequip Bare.");
						}
					} else {
						updateHistory("The item of " + str + " is not equipped. Try checking your spelling.");
					}
				}
				req.setAttribute("roomMessage", map.getRoomDescription());
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
			else {
				error = "unsupported command";
				req.setAttribute("errorMessage", error);
				req.setAttribute("roomMessage",map.getRoomDescription());
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				//resp.getWriter().write("Nothing Happens");
			}
		}
		else {
			System.out.println("else");
			req.setAttribute("roomMessage", map.getRoomDescription());
			req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			//resp.getWriter().write("Nothing Happens");
		}
		
	}
	
	/*public void updateHistory(String input, String output) {
		if(session.getAttribute("history")!=null) {
			ArrayList<String> history = (ArrayList<String>) session.getAttribute("script");
			history.add(input);
			history.add(output);
			session.setAttribute("commandHistory", history);
		}
		else {
			ArrayList<String> history = new ArrayList<String>();
			history.add(input);
			history.add(output);
		}
		
	}
	public void updateHistory(String input) {
		if(session.getAttribute("history")!=null) {
			ArrayList<String> history = (ArrayList<String>) session.getAttribute("history");
			history.add(input);
			session.setAttribute("commandHistory", history);
		}
		else {
			ArrayList<String> history = new ArrayList<String>();
			history.add(input);
		}
		
	}*/
	
	@SuppressWarnings("unchecked")
	public void updateHistory(String input) {
		ArrayList<String> hist = (ArrayList<String>) session.getAttribute("history");
		Collections.reverse(hist);
		hist.add(input);
		Collections.reverse(hist);
		session.setAttribute("history", hist);
		
		String history = "" + session.getAttribute("commandHistory").toString() + input + "\n ";
		
		session.setAttribute("commandHistory", history);
	}
	
	public void updateHistory(String input, String output) {
		//String history = "" + session.getAttribute("commandHistory").toString() + input + "\n " + output + "\n";
		
		ArrayList<String> hist = (ArrayList<String>) session.getAttribute("history");
		if(hist==null) {
			hist = new ArrayList<String>();
		}
		Collections.reverse(hist);
		hist.add(input);
		hist.add(output);
		Collections.reverse(hist);
		session.setAttribute("history", hist);
		
		//session.setAttribute("commandHistory", history);
	}
		
}
	