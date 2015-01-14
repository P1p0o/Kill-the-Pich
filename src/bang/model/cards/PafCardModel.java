package bang.model.cards;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import bang.model.game.PlayerModel;

import java.util.ArrayList;

import bang.manager.CacheManager;
import bang.model.game.GameModel;
import bang.model.game.PlayerModel;

public class PafCardModel extends YellowCardModel{
	
	public PafCardModel()
	{
		super();
		this.setName("paf");
	}
	
	public void pafPlayer(String pPlayer)
	{
		//Notify players
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage("player1", "paf"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player2", "paf"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player3", "paf"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player4", "paf"+pPlayer));
		
		//Refresh life
		CacheManager cacheManager;
		cacheManager = CacheManager.getInstance();
		GameModel gameModel = (GameModel) cacheManager.get("game");
		ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
		for(PlayerModel playerModel : listPlayers){
			if(playerModel.getName().equals("player"+pPlayer)){
				ArrayList<CardModel> cardHand = playerModel.getHand();
				int cpt = 0;
				for(CardModel cardModel : cardHand){
					if(cardModel.getName().equals("rate")){
						cpt ++;
					}
				}
				if(cpt == 0){
					playerModel.setLife(playerModel.getLife()-1);
					System.out.println("on enleve une vie");	
				}
			}
		}
		//Notify players
		
	}
	
	public void decrementLife(String pPlayer){
		CacheManager cacheManager;
		cacheManager = CacheManager.getInstance();
		GameModel gameModel = (GameModel) cacheManager.get("game");
		ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
		for(PlayerModel playerModel : listPlayers){
			System.out.println(playerModel.getLife());
			if(playerModel.getName().equals("player"+pPlayer)){
				playerModel.setLife(playerModel.getLife()-1);
			}
			System.out.println(playerModel.getLife());			
		}
	}
}
