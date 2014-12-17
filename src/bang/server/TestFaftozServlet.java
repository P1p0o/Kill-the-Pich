package bang.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bang.model.game.GameModel;

public class TestFaftozServlet extends HttpServlet {
	
	public TestFaftozServlet() {
        super();
        GameModel newGame = new GameModel();
    }
    
   @Override
   public void init() throws ServletException {
	   super.init();
   }
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		GameModel newGame = new GameModel();
	}
}
