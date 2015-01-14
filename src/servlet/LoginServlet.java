package servlet;

import java.io.IOException;  
import java.io.PrintWriter;  



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class LoginServlet extends HttpServlet {  

	private static final long serialVersionUID = 1L;
	private DatastoreService datastore;

	public LoginServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		//Create a connection to the datastore ONETIME at the init servlet process
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");  

		String email=request.getParameter("email");  
		String pass=request.getParameter("pass");  

		JSONObject json = new JSONObject();

		Query q = new Query("user").setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
		Entity user = datastore.prepare(q).asSingleEntity();
		if(user!=null)
		{
			// hashage
			MessageDigest md=null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String passw = pass + user.getProperty("login") + "killthepich"; // Salting avant hashage
			md.update(passw.getBytes());
	
			byte byteData[] = md.digest();
	
			//convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i=0;i<byteData.length;i++) {
				String hex=Integer.toHexString(0xff & byteData[i]);
				if(hex.length()==1) hexString.append('0');
				hexString.append(hex);
			}
			String hashedpass = hexString.toString();
	
			/// fin hashage
			if(hashedpass.equals(user.getProperty("pass"))){  
				try {
					json.put("response", "true");
					json.put("login", user.getProperty("login"));

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpSession session=request.getSession();  
				session.setAttribute("login",user.getProperty("login"));  
				session.setAttribute("email",user.getProperty("email"));
				session.setAttribute("score",user.getProperty("score"));
			}  
			else{  
				try {
					json.put("response", "false");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}  
		}
		else{
				// on passe jamais par la si tout va bien
				System.out.println("problem_loginservlet");
		}
		PrintWriter out=response.getWriter();  
		out.print(json);  
		out.flush();
	}  
} 
