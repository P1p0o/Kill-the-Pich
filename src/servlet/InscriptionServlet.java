package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class InscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatastoreService datastore;

	public InscriptionServlet() {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		String login =request.getParameter("login");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
		
		JSONObject json = new JSONObject();
		
		/* PROTECTION CHAMPS FORMULAIRE */ 
		
		if(email.length()>30) 
		{
			try {
				json.put("response", "false2");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			return;
		}
		if(email.matches(".*<.*")==true||email.matches(".*>.*")||email.matches(".*;.*")||email.matches(".*&.*"))
		{
			try {
				json.put("response", "false3");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			return;
		}
		if(pass.matches(".*<.*")==true||pass.matches(".*>.*")||pass.matches(".*;.*")||pass.matches(".*&.*"))
		{
			try {
				json.put("response", "false4");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			return;
		}
		if(login.matches(".*<.*")==true||login.matches(".*>.*")||login.matches(".*;.*")||login.matches(".*&.*"))
		{
			try {
				json.put("response", "false5");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			return;
		}
		
		/* PREPARATION REQUETE DATASTORE */
		
		Query q = new Query("user").setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
		Entity s = datastore.prepare(q).asSingleEntity();

		if(s==null)
		{

			//// hashage password avec salting + login + string arbitraire

			MessageDigest md=null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String passw = pass + login + "killthepich"; // Salting avant hashage
			md.update(passw.getBytes());

			byte byteData[] = md.digest();

			//convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i=0;i<byteData.length;i++) {
				String hex=Integer.toHexString(0xff & byteData[i]);
				if(hex.length()==1) hexString.append('0');
				hexString.append(hex);
			}
			String hash = hexString.toString();
			long score = 0;
			//Create Entity for the datastore
			Entity user = new Entity("user");
			user.setProperty("login", login);
			user.setProperty("pass", hash);//decoded);
			user.setProperty("email", email);
			user.setProperty("score", score);

			datastore.put(user);
			try {
				json.put("response", "true");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				json.put("response", "false");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();

	}
}