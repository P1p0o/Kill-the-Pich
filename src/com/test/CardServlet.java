package com.test;
 
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.*;

import bang.manager.CacheManager;
import bang.model.cards.CardModel;
import bang.model.cards.PafCardModel;
import bang.model.cards.MissedCardModel;
import bang.model.game.GameModel;
import bang.model.game.PlayerModel;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
 
 
@SuppressWarnings("serial")
public class CardServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String card = req.getParameter("card");
		String player = req.getParameter("player");
		String defausse = req.getParameter("defausse");
		
		if(defausse != null){
			CacheManager cacheManager;
			cacheManager = CacheManager.getInstance();
			GameModel gameModel = (GameModel) cacheManager.get("game");
			ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
			for(PlayerModel playerModel : listPlayers){
				if(playerModel.getName().equals("player"+player)){
					ArrayList<CardModel> cardHand = playerModel.getHand();
					for(CardModel cardModel : cardHand){
						if(cardModel.getName().equals(card)){
							cardHand.remove(cardModel);
							gameModel.addToDefausse(cardModel);
							break;
						}
						
					}
				}
			}

			ChannelService channelService = ChannelServiceFactory.getChannelService();
			channelService.sendMessage(new ChannelMessage("player1", "refreshHand"));
			channelService.sendMessage(new ChannelMessage("player2", "refreshHand"));
			channelService.sendMessage(new ChannelMessage("player3", "refreshHand"));
			channelService.sendMessage(new ChannelMessage("player4", "refreshHand"));
		}
		else{
			if(card.equals("paf")){
				PafCardModel pafCard = new PafCardModel();
				pafCard.pafPlayer(player);
			}
			if(card.equals("missed")){
				MissedCardModel missedCard = new MissedCardModel();
				missedCard.action(player);

			}
			if(card.equals("loseLife")){
				PafCardModel pafCard = new PafCardModel();
				pafCard.decrementLife(player);
			}
		}
		
	}
	
} 
