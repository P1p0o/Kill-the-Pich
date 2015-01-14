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
   CacheManager cacheManager;
    public TurnServlet() {
        super();
        cacheManager = cacheManager.getInstance();
    }
    

   @Override
   public void init() throws ServletException {
	   super.init();

   }

	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		GameModel gameModel = (GameModel) cacheManager.get("game");
		int lTurn = gameModel.getmTurn();
		
		if ( lTurn == 4 )
		{
			lTurn=1;
		}
		else
		{
			lTurn++;
		}
		
		gameModel.setmTurn(lTurn);
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage("player1", "gameStart"));
		channelService.sendMessage(new ChannelMessage("player2", "gameStart"));
		channelService.sendMessage(new ChannelMessage("player3", "gameStart"));
		channelService.sendMessage(new ChannelMessage("player4", "gameStart"));
		
		
	}

}