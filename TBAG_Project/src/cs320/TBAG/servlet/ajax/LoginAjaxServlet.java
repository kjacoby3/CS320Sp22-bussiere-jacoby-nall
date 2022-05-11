package cs320.TBAG.servlet.ajax;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cs320.TBAG.database.DerbyDatabase;

public class LoginAjaxServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	HttpSession session;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		System.out.println("doGet");
		session = req.getSession();
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		System.out.println("loginAjaxServlet doPost");
		session = req.getSession();
		//System.out.println(session.getAttribute("saveUsername"));
		//System.out.println(session.getAttribute("savePassword"));
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		System.out.println(username);
		System.out.println(password);
		
		
		DerbyDatabase db = new DerbyDatabase();
		String saltHex = db.selectSaltFromUsername(username);
		String passwordHex = db.selectPasswordFromUsername(username);
		//String compare=db.selectAccountFromUsername(username);
		String compare = "hello";
		System.out.println(compare);
		if(validatePassword(passwordHex, saltHex, password)) {
			//req.getRequestDispatcher("/_view/Game.jsp").forward(req, resp);
			//resp.sendRedirect("/TBAG/game");
			session.setAttribute("playerID", 1);
			resp.setContentType("text/plain");
			resp.getWriter().write(username);
			resp.getWriter().close();
		}
		else {
			resp.setContentType("text/plain");
			resp.getWriter().write("failure");
			resp.getWriter().close();
		}
		
	}
	
	private static boolean validatePassword(String passwordHex, String saltHex, String comparePassword) {
		String comparePasswordHex=null;
		try {
			comparePasswordHex = generateHash(comparePassword, fromHex(saltHex));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(passwordHex.equals(comparePasswordHex));
	}
	
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException{
	    byte[] bytes = new byte[hex.length() / 2];
	    for(int i = 0; i < bytes.length ;i++)
	    {
	        bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return bytes;
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
	private static String generateHash (String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
		int iterations = 1000;
	    char[] chars = password.toCharArray();

	    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
	    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

	    byte[] hash = skf.generateSecret(spec).getEncoded();
	    return toHex(hash);
	}
	
}