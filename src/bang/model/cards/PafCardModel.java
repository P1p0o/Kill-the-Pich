package bang.model.cards;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import bang.model.game.PlayerModel;

import java.util.ArrayList;

import bang.manager.CacheManager;
import bang.model.game.GameModel;

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
					if(cardModel.getName().equals("missed")){
						cpt ++;
					}
				}
	/*			if(cpt == 0){
					playerModel.setLife(playerModel.getLife()-1);
					channelService.sendMessage(new ChannelMessage("player1", "loseLife"+pPlayer));
					channelService.sendMessage(new ChannelMessage("player2", "loseLife"+pPlayer));
					channelService.sendMessage(new ChannelMessage("player3", "loseLife"+pPlayer));
					channelService.sendMessage(new ChannelMessage("player4", "loseLife"+pPlayer));
				}
				else{*/
					channelService.sendMessage(new ChannelMessage("player1", "waitAnswer"+pPlayer));
					channelService.sendMessage(new ChannelMessage("player2", "waitAnswer"+pPlayer));
					channelService.sendMessage(new ChannelMessage("player3", "waitAnswer"+pPlayer));
					channelService.sendMessage(new ChannelMessage("player4", "waitAnswer"+pPlayer));
				//}
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
			if(playerModel.getName().equals("player"+pPlayer)){
				playerModel.setLife(playerModel.getLife()-1);
			}			
		}
	}
}
