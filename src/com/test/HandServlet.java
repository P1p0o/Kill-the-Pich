package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        JSONArray newArray = new JSONArray();
		
		if(gameModel != null){
			
			if(gameModel.getListPlayers().size() == 4){
				ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
				for(PlayerModel playerModel : listPlayers){		
								
					if(playerModel == listPlayers.get(numberInt-1)){
						ArrayList<CardModel> cardHand = playerModel.getHand();
						for(int i = 0; i < cardHand.size(); i++){
							JSONObject json = new JSONObject();
					        
					        try {
					        	json.put("name", cardHand.get(i).getName());
						        array.put(json);
				
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					
						try {
							jsonToReturn.put("life", playerModel.getLife());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							jsonToReturn.put("role", playerModel.getmRole());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						try {
							jsonToReturn.put("login", playerModel.getLogin());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							jsonToReturn.put("canPaf", playerModel.isCanPaf());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
					}
					
					else{
						JSONObject newJson = new JSONObject();
						try {
							newJson.put("name", playerModel.getName());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        try {
							newJson.put("life", playerModel.getLife());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        try {
							newJson.put("login", playerModel.getLogin());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							newJson.put("nbCards", playerModel.getHand().size());
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if(playerModel.getmRole().equals("sherif")){
							try {
								newJson.put("role", playerModel.getmRole());
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						
						
						newArray.put(newJson);	
					}
				}
			//	PlayerModel player = listPlayers.get(numberInt-1);
					}
			
			try {
				jsonToReturn.put("cards", array);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				jsonToReturn.put("otherPlayers", newArray);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONArray deckArray = new JSONArray();
				
			ArrayList<CardModel> cardDeck = gameModel.getCardsDeck();
			
			try {
				jsonToReturn.put("pioche", cardDeck.size());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			ArrayList<CardModel> defausse = gameModel.getDefausse();
			if(defausse.size() != 0){
				int size = gameModel.getDefausse().size();
				String defausseName = gameModel.getDefausse().get(size-1).getName();
				try {
					jsonToReturn.put("defausse", defausseName);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					jsonToReturn.put("defausseSize", defausse.size());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		PrintWriter out = response.getWriter();
        out.print(jsonToReturn);
        out.flush();
	}

}