package bang.model.cards;

import java.util.ArrayList;

import bang.manager.CacheManager;
import bang.model.game.GameModel;
import bang.model.game.PlayerModel;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class HippopotamouthCardModel extends YellowCardModel {
	
	public HippopotamouthCardModel()
	{
		super();
		this.setName("hippopotamouth");
	}
	
	public void eatHippopotamouth (String pPlayer)
	{
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage("player1", "hippopotamouth"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player2", "hippopotamouth"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player3", "hippopotamouth"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player4", "hippopotamouth"+pPlayer));
		
		//Refresh life
		CacheManager cacheManager;
		cacheManager = CacheManager.getInstance();
		GameModel gameModel = (GameModel) cacheManager.get("game");
		ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
		
		for(PlayerModel P : listPlayers){
			if( P.getmRole().equals("sherif") )
			{
				if( P.getLife() < 5 )
				{	
					P.setLife( P.getLife() + 1 );
				}
			}
			else
			{
				if( P.getLife() < 4 )
				{
					P.setLife( P.getLife() + 1 );
				}
			}
		}

		channelService.sendMessage(new ChannelMessage("player1", "refreshHand"));
		channelService.sendMessage(new ChannelMessage("player2", "refreshHand"));
		channelService.sendMessage(new ChannelMessage("player3", "refreshHand"));
		channelService.sendMessage(new ChannelMessage("player4", "refreshHand"));
	}
}
