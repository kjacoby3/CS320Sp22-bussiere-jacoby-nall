package cs320.TBAG.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.model.Combat;
import cs320.TBAG.model.Equipment;
import cs320.TBAG.model.Inventory;
import cs320.TBAG.model.Map;
import cs320.TBAG.model.NPC;
import cs320.TBAG.model.Player;
import cs320.TBAG.model.Weapon;


public class CombatServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	Player player = new Player();
	NPC npc1 = new NPC();
	Combat combatMod; // = new Combat(player, npc1);
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet");
		
		session = req.getSession();
		
		combatMod = (Combat) session.getAttribute("combatMod");
		String playerName = combatMod.getActor1().getName();
		String npcName = combatMod.getActor2().getName();
		String result2 = null;
		
		
		
		req.setAttribute("player", playerName);
		req.setAttribute("enemy", npcName);
		req.setAttribute("enemyHealth", combatMod.getActor2().getActorStats().getCurHP());
		req.setAttribute("playerHealth", combatMod.getActor1().getActorStats().getCurHP());
		
		if(combatMod.getTurn() == 2) {
			result2 = combatMod.actor2Attack();
//			combatMod.getActor1().getActorStats().subtractHP((int) combatMod.actor2CalcAttackDMG());
//			req.setAttribute("enemyHealth", combatMod.getActor2().getActorStats().getCurHP());
//			req.setAttribute("playerHealth", combatMod.getActor1().getActorStats().getCurHP());
//			combatMod.updateTurn();
//			if(combatMod.getActor1().getActorStats().getCurHP() <= 0) {
//				req.getRequestDispatcher("/_view/Game.jsp").forward(req,  resp);
//			}
		}
		
		req.setAttribute("result2", result2);
		req.getRequestDispatcher("/_view/combat.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		
		session = req.getSession();
		
		combatMod = (Combat) session.getAttribute("combatMod");
		
		String error = null;
		String finish = null;
		String result1 = null;
		String result2 = null;
		String result3 = null;
		
		req.setAttribute("player", combatMod.getActor1().getName());
		req.setAttribute("enemy", combatMod.getActor2().getName());
		req.setAttribute("enemyHealth", combatMod.getActor2().getActorStats().getCurHP());
		req.setAttribute("playerHealth", combatMod.getActor1().getActorStats().getCurHP());
		
		if (req.getParameter("attack") != null) {
			result1 = combatMod.actor1Attack();
			if(combatMod.getActor2Defeated() == true) {
				result3 = combatMod.actor1DefeatsActor2();
				finish = "true";
			}
		}
		
		if(req.getParameter("run") != null) {
			Boolean run = combatMod.actor1RunAttempt();
			if (run == true) {
				result1 = "You successfully ran away";
				finish = "true";
			} else {
				result1 = "You could not get away.";
			}
		}
		
		if(combatMod.getTurn() == 2) {
			result2 = combatMod.actor2Attack();
		}
		
		req.setAttribute("result1", result1);
		req.setAttribute("result2", result2);
		req.setAttribute("result3", result3);	
		req.setAttribute("finished", finish);
		
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
		
		req.setAttribute("enemyType", combatMod.getActor2().getType());
		req.setAttribute("playerLVL", combatMod.getActor1().getActorStats().getCurLvl());
		req.setAttribute("playerEXP", combatMod.getActor1().getActorStats().getCurExp());
		req.setAttribute("playerMaxEXP", combatMod.getActor1().getActorStats().getMaxExp());
		
		req.getRequestDispatcher("/_view/combat.jsp").forward(req, resp);
	}
	
	
}