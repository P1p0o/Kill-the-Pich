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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

 
@SuppressWarnings("serial")
public class CardServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String card = req.getParameter("card");
		String player = req.getParameter("player");
		String defausse = req.getParameter("defausse");
		String pioche = req.getParameter("pioche");

		CacheManager cacheManager;
		cacheManager = CacheManager.getInstance();
		GameModel gameModel = (GameModel) cacheManager.get("game");
		
		if(defausse != null || pioche != null){
			if( defausse != null )
			{ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
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
			}
			if( pioche != null)
			{
				ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
				String lNewCard = gameModel.drawCard(player);
				ChannelService channelService = ChannelServiceFactory.getChannelService();
				channelService.sendMessage(new ChannelMessage("player"+player, "draw"+lNewCard));
			}
			
			ChannelService channelService = ChannelServiceFactory.getChannelService();
			if( gameModel.EndOfAction().equals(""))
			{
				channelService.sendMessage(new ChannelMessage("player1", "refreshHand"));
				channelService.sendMessage(new ChannelMessage("player2", "refreshHand"));
				channelService.sendMessage(new ChannelMessage("player3", "refreshHand"));
				channelService.sendMessage(new ChannelMessage("player4", "refreshHand"));
			}
			else
			{
				String lWinner = gameModel.EndOfAction();
				channelService.sendMessage(new ChannelMessage("player1", lWinner));
				channelService.sendMessage(new ChannelMessage("player2", lWinner));
				channelService.sendMessage(new ChannelMessage("player3", lWinner));
				channelService.sendMessage(new ChannelMessage("player4", lWinner));
			}
			
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
		
		if( !gameModel.EndOfAction().equals(""))
		{
			String lWinner = gameModel.EndOfAction();
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			for(PlayerModel P : gameModel.getListPlayers()){
				String email = P.getMail();
				if(P.getmRole() == lWinner || (P.getmRole() == "adjoint" && lWinner == "sherif")){
					Query q = new Query("user").setFilter(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
					Entity user = datastore.prepare(q).asSingleEntity();
					long score = (long) user.getProperty("score");
					score = score+50;
					user.setProperty("score", score);
					datastore.put(user);
				} 
			}
			
			
			ChannelService channelService = ChannelServiceFactory.getChannelService();
			channelService.sendMessage(new ChannelMessage("player1", "win"+lWinner));
			channelService.sendMessage(new ChannelMessage("player2", "win"+lWinner));
			channelService.sendMessage(new ChannelMessage("player3", "win"+lWinner));
			channelService.sendMessage(new ChannelMessage("player4", "win"+lWinner));
		}
	} 
	
} 
