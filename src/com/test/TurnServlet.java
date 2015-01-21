package com.test;

import bang.manager.CacheManager;
import bang.model.cards.CardModel;
import bang.model.game.GameModel;
import bang.model.game.PlayerModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		GameModel lCurrentGame = (GameModel) cacheManager.get("game");
		String param = request.getParameter("turn");

		if(lCurrentGame != null){

			int lTurn = lCurrentGame.getmTurn();
				
			if(param.equals("next")){
				
				for(PlayerModel P :lCurrentGame.getListPlayers()){
					P.setCanPaf(true);
				}
				
				do
				{
					if ( lTurn == 4)
					{
						lTurn=1;
					}
					else
					{
						lTurn++;
					}
				}
				while( lCurrentGame.getListPlayers().get(lTurn-1).getLife() == 0 );
				
				if(lCurrentGame.getCardsDeck().size() == 0){
					ArrayList <CardModel> defausse = lCurrentGame.getDefausse();
					ArrayList <CardModel> pioche = lCurrentGame.getCardsDeck();
					
					for(CardModel card: defausse){
						pioche.add(card);
					}
					defausse.clear();
					Collections.shuffle(pioche);
				}
				else{
					PlayerModel player = lCurrentGame.getListPlayers().get(lTurn-1);
					ArrayList <CardModel> cardDeck = lCurrentGame.getCardsDeck();
					CardModel card = cardDeck.get(cardDeck.size()-1);
					player.getHand().add(card);
					cardDeck.remove(cardDeck.size()-1);
				}
				
			}
			lCurrentGame.setmTurn(lTurn);
			cacheManager.put("game", lCurrentGame);
			
			ChannelService channelService = ChannelServiceFactory.getChannelService();
			channelService.sendMessage(new ChannelMessage("player1", "turn"+lTurn));
			channelService.sendMessage(new ChannelMessage("player2", "turn"+lTurn));
			channelService.sendMessage(new ChannelMessage("player3", "turn"+lTurn));
			channelService.sendMessage(new ChannelMessage("player4", "turn"+lTurn));
		} 

	}

}