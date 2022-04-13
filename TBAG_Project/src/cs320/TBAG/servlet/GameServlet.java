package cs320.TBAG.servlet;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.model.Map;
import cs320.TBAG.model.NPC;
import cs320.TBAG.controller.GameController;
import cs320.TBAG.model.Combat;
import cs320.TBAG.model.Game;
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
		
		String roomDesc = map.getRoomDescription();
		req.setAttribute("roomMessage", roomDesc);
		req.getRequestDispatcher("/_view/Game.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("doPost");
		GameController controller = new GameController();
		
		
		
		
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
					updateHistory(input);
					req.setAttribute("roomMessage", map.getRoomDescription());
					req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
				}
				
			}
			
			else if(input.equalsIgnoreCase("south")) {
				if(input.equalsIgnoreCase("south")) {
					if(map.checkMove("s")){
						updateHistory(input);
						map.setNewRoom(map.getNewRoomID());
						req.setAttribute("roomMessage", map.getRoomDescription());
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					}
				}
			}
			
			else if(input.equalsIgnoreCase("west")) {
				if(input.equalsIgnoreCase("west")) {
					if(map.checkMove("w")){
						updateHistory(input);
						map.setNewRoom(map.getNewRoomID());
						req.setAttribute("roomMessage", map.getRoomDescription());
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					}
					
				}
			}
			
			else if(input.equalsIgnoreCase("east")) {
				if(input.equalsIgnoreCase("east")) {
					if(map.checkMove("e")){
						updateHistory(input);
						map.setNewRoom(map.getNewRoomID());
						req.setAttribute("roomMessage", map.getRoomDescription());
						req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
					}
					
				}
			}
			
			else if(input.equalsIgnoreCase("inventory")) {
				req.setAttribute("weapons", new ArrayList<>(player.getInventory().getWeapons().values()));
				req.setAttribute("equipment", new ArrayList<>(player.getInventory().getEquipment().values()));
				req.setAttribute("trophies", new ArrayList<>(player.getInventory().getTrophies().values()));
				req.setAttribute("usables", new ArrayList<>(player.getInventory().getUsables().values()));
				req.setAttribute("treasures", new ArrayList<>(player.getInventory().getTreasures().values()));
				req.setAttribute("consumables", new ArrayList<>(player.getInventory().getConsumables().values()));
				req.getRequestDispatcher("/_view/inventory.jsp").forward(req, resp);
			}
			
			else if(input.equalsIgnoreCase("pickup")) {
				player.getInventory().addItem(new Weapon("banana", 100000000, 1000000));
				updateHistory(input);
				req.setAttribute("roomMessage", map.getRoomDescription());
				req.getRequestDispatcher("/_view/Game.jsp").forward(req,resp);
			}
			else if(input.equalsIgnoreCase("start")) {
				map.setCurrRoom(1);
				updateHistory(input);
				req.setAttribute("roomMessage",map.getRoomDescription());
				req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
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
			else if (input.equalsIgnoreCase("equip")){
				if(player.getInventory().getWeapons().size() > 1) {
					//updateHistory("What weapon do you want to equip");
					for(Weapon i : player.getInventory().getWeapons().values()) {
						if (input.equalsIgnoreCase(i.getName())) {
							player.equipWeapon(i);
							//updateHistory(i.getName() + " was equipped.");
							//req.setAttribute("roomMessage", map.getRoomDescription());
							//req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
						}
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
			}
		}
		else {
			System.out.println("else");
			req.setAttribute("roomMessage", map.getRoomDescription());
			req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
		}
		
	}
	
	public void updateHistory(String input) {
		String history = "" + session.getAttribute("commandHistory").toString() + input + ", ";
		
		session.setAttribute("commandHistory", history);
	}
		
}
	