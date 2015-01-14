package servlet;

import java.io.IOException;  
import java.io.PrintWriter;  

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class LogoutServlet extends HttpServlet {  
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
			throws ServletException, IOException {  

		response.setContentType("text/html");  
		PrintWriter out=response.getWriter();  

		HttpSession session=request.getSession();  
		session.invalidate();  
		JSONObject json = new JSONObject();

		out.print(json);  
		out.flush();
		//out.print("You are successfully logged out!");  
		System.out.println("poneylogout");
		out.close();  
		return;
	}  
}  