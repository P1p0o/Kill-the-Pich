package com.test;

import bang.manager.CacheManager;
import bang.model.game.GameModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.cache.Cache;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class TurnServlet extends HttpServlet {
	
   private CacheManager cacheManager;
   
    public TurnServlet() {
        super();
        cacheManager = CacheManager.getInstance();
    }
    

   @Override
   public void init() throws ServletException {
	   super.init();

   }

	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		GameModel lCurrentGame = (GameModel) cacheManager.get("game");
		int lNbPlayers = Integer.parseInt(request.getParameter("NbPlayers"));
		
		int lTurn = lCurrentGame.getmTurn();
		do
		{
			if ( lTurn == lNbPlayers)
			{
				lTurn=1;
			}
			else
			{
				lTurn++;
			}
		}
		while( lCurrentGame.getListPlayers().get(lTurn-1).getLife() == 0 );
		
		lCurrentGame.setmTurn(lTurn);
		cacheManager.put("game", lCurrentGame);
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage("player1", "turn"+lTurn));
		channelService.sendMessage(new ChannelMessage("player2", "turn"+lTurn));
		channelService.sendMessage(new ChannelMessage("player3", "turn"+lTurn));
		channelService.sendMessage(new ChannelMessage("player4", "turn"+lTurn));
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}