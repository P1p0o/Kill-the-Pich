package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class RankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatastoreService datastore;

	public RankServlet() {
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
		
		//Entity user = new Entity("user");
		ArrayList<String> results = new ArrayList<String>();
				
		Query q = new Query("user").addSort("score", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()){
				
			results.add((String)result.getProperty("login"));
			//results.add((String)result.getProperty("score"));
		}
		
		
		response.setContentType("application/json");
		JSONObject jsonToSend;
		jsonToSend=new JSONObject();

		try {
			jsonToSend.put("user",results);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Send the Json object to the web browser
		PrintWriter out= response.getWriter();
		out.write(jsonToSend.toString());
	}
	
	
}