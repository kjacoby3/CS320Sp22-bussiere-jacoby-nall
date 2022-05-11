package cs320.TBAG.servlet.ajax;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.database.DerbyDatabase;
import cs320.TBAG.model.Player;

public class CreateAccountAjaxServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("createAccountAjaxdoGet");
		doPost(req,resp);
	}
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		DerbyDatabase db = new DerbyDatabase();
		System.out.println("CreateAccountAjaxdoPost");
		session = req.getSession();
		System.out.println(req.getParameter("username"));
		byte[] salt = new byte[16];
		String saltHex = null;
		String passwordHashHex = null;
		
		
		/*try {
			byte[] salt1 = new byte[16];
			salt1 = getSalt();
			System.out.println(toHex(salt1));
			System.out.println(generateHash("password", salt1));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		if(db.selectPasswordFromUsername(req.getParameter("username"))==null) {
			try {
				salt = getSalt();
				saltHex = toHex(salt);
				passwordHashHex = generateHash(req.getParameter("password"), salt);
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
			
			ArrayList<Player> players = db.findAllPlayers();
			System.out.println(players.get(0).getName());
			db.insertAccount(req.getParameter("username"), passwordHashHex, saltHex);
			resp.setContentType("text/plain");
			session.setAttribute("playerID", 1);
			resp.getWriter().write(req.getParameter("username"));
			resp.getWriter().close();
		}
		else {
			resp.getWriter().write("exists");
			resp.getWriter().close();
		}
		
		
	}
	
	private static byte[] getSalt() throws NoSuchAlgorithmException{
	    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	    byte[] salt = new byte[16];
	    sr.nextBytes(salt);
	    return salt;
	}
	private static String generateHash (String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		int iterations = 1000;
	    char[] chars = password.toCharArray();

	    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
	    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

	    byte[] hash = skf.generateSecret(spec).getEncoded();
	    return toHex(hash);
	}
	
	private static String toHex(byte[] array) throws NoSuchAlgorithmException{
	    BigInteger bi = new BigInteger(1, array);
	    String hex = bi.toString(16);
	    
	    int paddingLength = (array.length * 2) - hex.length();
	    if(paddingLength > 0)
	    {
	        return String.format("%0"  +paddingLength + "d", 0) + hex;
	    }else{
	        return hex;
	    }
	}
	
}