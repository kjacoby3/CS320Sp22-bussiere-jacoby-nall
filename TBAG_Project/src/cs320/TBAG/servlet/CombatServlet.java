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
	
	
	
	Combat combatMod = new Combat(player, npc1);
	
	HttpSession session;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet");
		
		session = req.getSession();
		
		req.setAttribute("player", combatMod.getActor1().getName());
		req.setAttribute("enemy", combatMod.getActor2().getName());
		req.setAttribute("enemyHealth", combatMod.getActor2().getActorStats().getCurHP());
		req.setAttribute("playerHealth", combatMod.getActor1().getActorStats().getCurHP());
		
		if(combatMod.getTurn() == 2) {
			combatMod.getActor1().getActorStats().subtractHP((int) combatMod.actor2CalcAttackDMG());
			req.setAttribute("enemyHealth", combatMod.getActor2().getActorStats().getCurHP());
			req.setAttribute("playerHealth", combatMod.getActor1().getActorStats().getCurHP());
			combatMod.updateTurn();
			if(combatMod.getActor1().getActorStats().getCurHP() <= 0) {
				req.getRequestDispatcher("/_view/Game.jsp").forward(req,  resp);
			}
		}
		
		req.getRequestDispatcher("/_view/combat.jsp").forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		
		String error = null;
		
		if (req.getParameter("attack") != null) {
			if(combatMod.getTurn() == 1) {
				combatMod.getActor2().getActorStats().subtractHP((int) combatMod.actor1CalcAttackDMG());
				req.setAttribute("enemyHealth", combatMod.getActor2().getActorStats().getCurHP());
				req.setAttribute("playerHealth", combatMod.getActor1().getActorStats().getCurHP());
				combatMod.updateTurn();
				if(combatMod.getActor2().getActorStats().getCurHP() <= 0) {
					req.getRequestDispatcher("/_view/Game.jsp").forward(req,  resp);
				}
			} else {
				error = "It is not your turn.";
				req.setAttribute("errorMessage", error);
				req.getRequestDispatcher("/_view/combat.jsp").forward(req, resp);
			}
		} else if (req.getParameter("run") != null) {
			if(combatMod.getTurn() == 1) {
				if(combatMod.boolDiceRoll((int) combatMod.actor1RunChance()) == true) {
					req.getRequestDispatcher("/_view/Game.jsp").forward(req,  resp);
				} else {
					error = "You were not able to escape.";
					req.setAttribute("errorMessage", error);
					req.getRequestDispatcher("/_view/combat.jsp").forward(req, resp);
				}
			} else {
				error = "It is not your turn.";
				req.setAttribute("errorMessage", error);
				req.getRequestDispatcher("/_view/combat.jsp").forward(req, resp);
			}
			
		}
		
		req.getRequestDispatcher("/_view/combat.jsp").forward(req, resp);
	}
	
	
}