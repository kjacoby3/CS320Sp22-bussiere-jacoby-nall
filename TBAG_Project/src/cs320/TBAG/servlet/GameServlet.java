package cs320.TBAG.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.model.Map;
import cs320.TBAG.model.NPC;
import cs320.TBAG.controller.GameController;
import cs320.TBAG.controller.InventoryController;
import cs320.TBAG.controller.MapController;
import cs320.TBAG.model.Combat;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Game;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Item;
import cs320.TBAG.model.LevelUp;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Weapon;
import cs320.TBAG.model.Convo.Conversation;
import cs320.TBAG.model.InteractableObj.Interactable;
import cs320.TBAG.model.InteractableObj.Keypad;
import cs320.TBAG.model.PuzzleType.PinPuzzle;


public class GameServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	//Game model = new Game();
	GameController controller;
	Player player;
	Game model;
	int gameID;
	int playerID;
	Map map;
	
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet");
		gameID = 1;
		playerID = 1;
		controller = new GameController();
		model = new Game();
		controller.setModel(model);
		controller.startGame(playerID, gameID);
		
		//player = new Player();
		//gameID = 1;
		//map = model.getMap();
		player = model.getPlayer();
		map = model.getMap();
		
		session = req.getSession();
		session.setAttribute("controller", controller);
		session.setAttribute("model", model);
		session.setAttribute("player", player);
		session.setAttribute("gameID", gameID);
		session.setAttribute("history", new ArrayList<String>());
		
		forwardRoomDesc(player, player.getRoomId(), null, req, resp);
		
		
		//String roomDesc = map.getRoomDescription();
		//req.setAttribute("roomMessage", roomDesc);
		//req.getRequestDispatcher("/_view/Game.jsp").forward(req,resp);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//System.out.println("GameServletdoPost");
		
		String prevInput;
		
				//int gameID = 1;
		//GameController controller = new GameController();
		session = req.getSession();
		
		/*if(session.getAttribute("player")== null) {
			System.out.println("null");
		}*/
		model = (Game) session.getAttribute("model");
		
		player = (Player) session.getAttribute("player");
		map = model.getMap();
		
		System.out.println(player.getRoomId());
		
		Boolean objActivated = (Boolean) session.getAttribute("objActivated");
		Interactable activeObj = (Interactable) session.getAttribute("activeObj");
		
		Boolean conversationEnded = (Boolean) session.getAttribute("conversationEnded");
		Conversation conversation = (Conversation) session.getAttribute("conversation");

		controller.setModel(model);
		String input = (String) req.getParameter("command");
		/*if(input!= null) {
			input = input.toLowerCase();
		}*/

		
		System.out.println("" + input);
		
		//Player player= model.getPlayer();
		//Map map = model.getMap();
		//String error = null;
		
		
		/*req.setAttribute("game", model);
		session.setAttribute("controller", controller);
		session.setAttribute("model", model);
		session.setAttribute("player", player);
		session.setAttribute("gameID", gameID);*/
		
		if(input != null) {
			input = input.toLowerCase();
			if(conversationEnded != null && !conversationEnded) {
				System.out.println("Working");
				//updateHistory(input, )
				
			}
			
			if(objActivated != null && activeObj != null && objActivated) {
				System.out.println("ObjActivated");
				updateHistory(input, player.activateObj(input, activeObj));
				objActivated = false;
				session.setAttribute("objActivated", false);
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
			
			else if(input.equalsIgnoreCase("north") || input.equalsIgnoreCase("n")) {
				
				int directionCheck = map.canMove(player.getRoomId(), "north");
				if(directionCheck>0) {
					forwardRoomDesc(player, directionCheck, input, req, resp);	
				}
				else {
					updateHistory(input, "You cannot go that direction");
					req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				}			
				
			}
			
			else if(input.equalsIgnoreCase("south") || input.equalsIgnoreCase("s")) {
				System.out.println("south");
				int directionCheck = map.canMove(player.getRoomId(), "south");
				if(directionCheck>0) {
					forwardRoomDesc(player, directionCheck, input, req, resp);	
				}
				else {
					updateHistory(input, "You cannot go that direction");
					req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				}			
			}
			
			else if(input.equalsIgnoreCase("west") || input.equalsIgnoreCase("w")) {
				int directionCheck = map.canMove(player.getRoomId(), "west");
				if(directionCheck>0) {
					forwardRoomDesc(player, directionCheck, input, req, resp);	
				}
				else {
					updateHistory(input, "You cannot go that direction");
					req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				}			
					
			}
			
			else if(input.equalsIgnoreCase("east")|| input.equalsIgnoreCase("e")) {
				int directionCheck = map.canMove(player.getRoomId(), "east");
				if(directionCheck>0) {
					forwardRoomDesc(player, directionCheck, input, req, resp);	
				}
				else {
					updateHistory(input, "You cannot go that direction");
					req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				}			
			}
			else if(input.equals("exit")) {
				int directionCheck = map.canMove(player.getRoomId(), "exit");
				if(directionCheck>0) {
					forwardRoomDesc(player, directionCheck, input, req, resp);	
				}
				else {
					updateHistory(input, "You cannot go that direction");
					req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				}	
				
			}
			
			else if(input.equalsIgnoreCase("inventory")) {
				/*InventoryController ic = new InventoryController();
				Inventory inventory = ic.getPlayerInventory(ID);
				
				req.setAttribute("weapons", new ArrayList<>(inventory.getWeapons().values()));
				req.setAttribute("equipment", new ArrayList<>(inventory.getEquipment().values()));
				req.setAttribute("trophies", new ArrayList<>(inventory.getTrophies().values()));
				req.setAttribute("usables", new ArrayList<>(inventory.getUsables().values()));
				req.setAttribute("treasures", new ArrayList<>(inventory.getTreasures().values()));
				req.setAttribute("consumables", new ArrayList<>(inventory.getConsumables().values()));*/
				
				
				
				//req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
				
				//resp.sendRedirect("/_view/inventory.jsp");
				resp.sendRedirect("/TBAG/inventory");
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
			
			else if (input.startsWith("enter")){
				String activation;
				
				if(input.equalsIgnoreCase("enter")) {
					activation = input;
				} else {
					String[] splitStr = input.split(" ");
					activation = splitStr[0];
				}
				int count = 0;
				Interactable Obj;
				
				//This block of code is for testing only, will be deleted later
				Keypad ob = new Keypad();
				PinPuzzle pinPuzzle = new PinPuzzle();
				Obj = ob;
				ob.setPuzzle(pinPuzzle);
				player.getLocation().addInteractable(ob);
				//---------------------------------//
				
				for(Interactable obj : player.getLocation().getRoomInteractables()) {
					if(obj.getActivationKeyword().equalsIgnoreCase("enter")) {
						Obj = obj;
						count++;
					}
				}
				if(count == 1) {
					updateHistory(player.activateObj(activation, Obj));
					objActivated = true;
					session.setAttribute("objActivated", objActivated);
					System.out.println("First activated obj");
					activeObj = Obj;
					session.setAttribute("activeObj", activeObj);
				} else if(count < 1) {
					updateHistory(input + " does not work here.");
				} else if (count > 1) {
					updateHistory("What object are you trying to interact with?");
				}
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
			else if(input.startsWith("talk")) {
				int count = 0;
				if(input.equalsIgnoreCase("talk")) {
					if(player.getLocation().getNPCsInRoom().size() == 1) {
						if(player.getLocation().getNPCsInRoom().get(0).getAggression() >= 0) {
							conversation = new Conversation(player, player.getLocation().getNPCsInRoom().get(0));
							session.setAttribute("conversation", conversation);
							session.setAttribute("conversationEnded", conversation.getEnded());
							updateHistory(input, "Talking to " + player.getLocation().getNPCsInRoom().get(0).getName());
							for(String str : conversation.getDisplayList()) {
								updateHistory(str);
							}
						} else {
							updateHistory(input, player.getLocation().getNPCsInRoom().get(0).getName() + " doesn't want to talk.");
						}
					} else if (player.getLocation().getNPCsInRoom().size() == 0){
						updateHistory(input, "There is no one to talk to.");
					} else {
						updateHistory(input, "Please specify who you want to talk to.");
					}
				} else {
					String[] splitStr = input.split(" ", 2);
					String npcName = splitStr[1];
					NPC talking = null;
					if(player.getLocation().getNPCsInRoom().size() > 0) {
						for(NPC npc : player.getLocation().getNPCsInRoom()) {
							if(npcName.equalsIgnoreCase(npc.getName()) || npcName.equalsIgnoreCase(npc.getType())) {
								count++;
								talking = npc;
							}
						}
						if(count == 1 ) {
							if(talking.getAggression() >= 0) {
								conversation = new Conversation(player, talking);
								session.setAttribute("conversation", conversation);
								session.setAttribute("conversationEnded", conversation.getEnded());
								updateHistory(input, "Talking to " + talking.getName());
								for(String str : conversation.getDisplayList()) {
									updateHistory(str);
								}
							} else {
								updateHistory(input, talking.getName() + " doesn't want to talk.");
							}
						} else if (count > 1) {
							updateHistory(input, "Please specify who you want to talk to.");
						} else {
							updateHistory(input, "There is no one to talk to.");
						}
					}
				}
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			}
			else {
				//String error = "unsupported command";
				//req.setAttribute("errorMessage", error);
				//req.setAttribute("roomMessage",map.getRoomDescription());
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				//resp.getWriter().write("Nothing Happens");
			}
			prevInput = input;
		}
		else {
			System.out.println("else");
			//req.setAttribute("roomMessage", map.getRoomDescription());
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
		
		//String history = "" + session.getAttribute("commandHistory").toString() + input + "\n ";
		
		//session.setAttribute("commandHistory", history);
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
	
	public void forwardRoomDesc (Player player, int directionCheck, String input, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		player.move(directionCheck);
		MapController mc = new MapController();
		if(input== null) {
			updateHistory(mc.getRoomDescByID(directionCheck));
		}
		else {
			updateHistory(input, mc.getRoomDescByID(directionCheck));
		}
		req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
	}
		
}
	