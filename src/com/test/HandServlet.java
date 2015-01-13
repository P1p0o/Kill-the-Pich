package com.test;

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
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import bang.manager.CacheManager;
import bang.model.cards.CardModel;
import bang.model.game.GameModel;
import bang.model.game.PlayerModel;


public class HandServlet extends HttpServlet {
   CacheManager cacheManager;
    public HandServlet() {
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
		String numberString = request.getParameter("number");
		int numberInt = Integer.parseInt(numberString);
		
		JSONObject jsonToReturn = new JSONObject();
        JSONArray array = new JSONArray();
		
		if(gameModel != null){
			
			if(gameModel.getListPlayers().size() == 4){
				ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
				PlayerModel player = listPlayers.get(numberInt-1);
				ArrayList<CardModel> cardHand = player.getHand();
				for(int i = 0; i < cardHand.size(); i++){
					JSONObject json = new JSONObject();
			        
			        try {
			        	json.put("name", cardHand.get(i).getName());
				        array.put(json);
		
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			
			try {
				jsonToReturn.put("cards", array);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}