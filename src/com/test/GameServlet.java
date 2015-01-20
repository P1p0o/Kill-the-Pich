package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import bang.manager.CacheManager;
import bang.model.game.GameModel;
import bang.model.game.PlayerModel;


public class GameServlet extends HttpServlet {
   CacheManager cacheManager;
    public GameServlet() {
        super();
        cacheManager = cacheManager.getInstance();
    }
    

   @Override
   public void init() throws ServletException {
	   super.init();

   }

	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		GameModel gameModel = (GameModel) cacheManager.get("game");
		if(gameModel == null){
			gameModel = new GameModel();
		}
		
		ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
		int size = listPlayers.size();
		
		JSONObject json = new JSONObject();
		try {
			json.put("numberOfPlayer", size);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String token = request.getParameter("token");
		String name = request.getParameter("name");
		GameModel gameModel = (GameModel) cacheManager.get("game");
		
		HttpSession session = request.getSession();
		String mail = (String) session.getAttribute("email");
		
		if(gameModel == null){
			gameModel = new GameModel();
		}
		
		boolean check = false;
		if(gameModel.getListPlayers().size() < 4){
			for(PlayerModel P : gameModel.getListPlayers()){
				if(P.getName().equals(name)){
					check = true;
				}
			}
			if(check == false){
				gameModel.addPlayer(name, token, mail);
			}
		}
		
		cacheManager.put("game", gameModel);
		
		gameModel = (GameModel) cacheManager.get("game");
		ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
		
		if(listPlayers.size() == 4){
			if(!gameModel.isGameStarted()){
				
				gameModel.startGame();
				
				//Le jeu commence
				ChannelService channelService = ChannelServiceFactory.getChannelService();
				channelService.sendMessage(new ChannelMessage("player1", "startGameTurn"+gameModel.getmTurn()));
				channelService.sendMessage(new ChannelMessage("player2", "startGameTurn"+gameModel.getmTurn()));
				channelService.sendMessage(new ChannelMessage("player3", "startGameTurn"+gameModel.getmTurn()));
				channelService.sendMessage(new ChannelMessage("player4", "startGameTurn"+gameModel.getmTurn()));
				
				//C'est le tour du sherif!
				/*channelService.sendMessage(new ChannelMessage("player1", "turn"));
				channelService.sendMessage(new ChannelMessage("player2", "turn"+gameModel.getmTurn()));
				channelService.sendMessage(new ChannelMessage("player3", "turn"+gameModel.getmTurn()));
				channelService.sendMessage(new ChannelMessage("player4", "turn"+gameModel.getmTurn()));*/

			}
			else{
				String player = name;
				ChannelService channelService = ChannelServiceFactory.getChannelService();
				channelService.sendMessage(new ChannelMessage(name, "refreshHand"));
			}
		}
		
	}

}