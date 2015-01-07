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
		//System.out.println("COUCOUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");


		Query q = new Query("user").setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
		Entity s = datastore.prepare(q).asSingleEntity();
		JSONObject json = new JSONObject();
		if(s==null)
		{
			MessageDigest md=null;
			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String text = pass;

			md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			byte[] digest = md.digest();
			//Create Entity for the datastore
			Entity user = new Entity("user");
			String decoded = new String(digest, "UTF-8");
			user.setProperty("login", login);
			user.setProperty("pass", decoded);
			user.setProperty("email", email);

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
			//System.out.println("Un compte est deja associe a cette adresse email.");
			//return;
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