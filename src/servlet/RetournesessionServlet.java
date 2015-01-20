
package servlet;

import java.io.IOException;  
import java.io.PrintWriter;  



import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class RetournesessionServlet extends HttpServlet {  

	private static final long serialVersionUID = 1L;
	public RetournesessionServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		DatastoreServiceFactory.getDatastoreService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  

		JSONObject json = new JSONObject();
		HttpSession session=request.getSession();  
		String login = (String)session.getAttribute("login");

		if(login!=null)
		{
			//int score = ((Long) session.getAttribute("score")).intValue();
			String score = ((Long)session.getAttribute("score")).toString();
			try {
				json.put("response", "true");
				json.put("login", login);
				json.put("score", score);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
		else{  
			try {
				json.put("response", "false");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}  
		PrintWriter out=response.getWriter();  
		out.print(json);  
		out.flush();
	}
}


