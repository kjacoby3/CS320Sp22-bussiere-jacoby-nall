package cs320.TBAG.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.model.Game;
import cs320.TBAG.model.LevelUp;
import cs320.TBAG.model.Player;


public class LevelUpServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	LevelUp levelUpModel;
	Player player;
	Game model;
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doGet");
		
		session = req.getSession();
		
		levelUpModel = (LevelUp) session.getAttribute("levelUpModel");
		
		req.getRequestDispatcher("/_view/levelUp.jsp").forward(req,resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		System.out.println("LevelUpdoPost");
		
		String result = null;
		
		session = req.getSession();
		
		levelUpModel = (LevelUp) session.getAttribute("levelUpModel");
		model = (Game) session.getAttribute("game");
		player = (Player) session.getAttribute("player");
		
		if (req.getParameter("incHP") != null) {
			String stat = "health";
			result = levelUpModel.useLevelUp(stat);
		}
		
		if (req.getParameter("incDMG") != null) {
			String stat = "damage";
			result = levelUpModel.useLevelUp(stat);
		}
		if (req.getParameter("incDEF") != null) {
			String stat = "defense";
			result = levelUpModel.useLevelUp(stat);
		}
		if (req.getParameter("incSPD") != null) {
			String stat = "speed";
			result = levelUpModel.useLevelUp(stat);
		}
		
		req.setAttribute("result", result);
		
		//Player player = model.getPlayer();
		
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
}