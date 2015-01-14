package com.test;
 
import java.io.IOException;

import javax.servlet.http.*;

import bang.model.cards.PafCardModel;
import bang.model.cards.RateCardModel;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
 
 
@SuppressWarnings("serial")
public class CardServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String card = req.getParameter("card");
		if(card.equals("paf")){
			PafCardModel pafCard = new PafCardModel();
			String player = req.getParameter("player");
			pafCard.pafPlayer(player);
		}
		if(card.equals("missed")){
			String player = req.getParameter("player");
			RateCardModel rateCard = new RateCardModel();
			rateCard.action(player);

		}
		if(card.equals("loseLife")){
			String player = req.getParameter("player");
			PafCardModel pafCard = new PafCardModel();
			pafCard.decrementLife(player);
		}
		
	}
	
} 
