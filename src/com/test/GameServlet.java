package com.test;

import java.io.IOException;
import java.util.ArrayList;

import javax.cache.Cache;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import bang.manager.CacheManager;
import bang.model.game.GameModel;
import bang.model.game.PlayerModel;


public class GameServlet extends HttpServlet {
   CacheManager cacheManager;
    public GameServlet() {
        super();
        cacheManager = new CacheManager();
    }
    

   @Override
   public void init() throws ServletException {
	   super.init();

   }

	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String token = request.getParameter("token");
		GameModel gameModel = (GameModel) cacheManager.get("game");
		
		if(gameModel == null){
			gameModel = new GameModel();
		}
		
		if(gameModel.getListPlayers().size() < 4){
			gameModel.addPlayer(token);
		}
		
		
		cacheManager.put("game", gameModel);
		
		gameModel = (GameModel) cacheManager.get("game");
		ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
		
		System.out.println("size : "+ listPlayers.size());
		if(listPlayers.size() == 4){
			gameModel.startGame();
			ChannelService channelService = ChannelServiceFactory.getChannelService();
			channelService.sendMessage(new ChannelMessage("player1", "gameStart"));
			channelService.sendMessage(new ChannelMessage("player2", "gameStart"));
			channelService.sendMessage(new ChannelMessage("player3", "gameStart"));
			channelService.sendMessage(new ChannelMessage("player4", "gameStart"));
		}

	}

}