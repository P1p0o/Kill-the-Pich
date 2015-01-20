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
	
	public boolean eatPoulemouth (String pPlayer)
	{
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		
		
		//Refresh life
		CacheManager cacheManager;
		cacheManager = CacheManager.getInstance();
		GameModel gameModel = (GameModel) cacheManager.get("game");
		ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
		
		for(PlayerModel P : listPlayers){
			if( P.getmRole().equals("sherif") )
			{
				if( P.getLife() == 5 )
				{
					channelService.sendMessage(new ChannelMessage("player"+pPlayer, "lifemax"+pPlayer));
					return false;
				}
				else
				{
					P.setLife( P.getLife() + 1 );
					return true;
				}
			}
			else
			{
				if( P.getLife() == 4 )
				{
					channelService.sendMessage(new ChannelMessage("player"+pPlayer, "lifemax"+pPlayer));
					return false;
				}
				else
				{
					P.setLife( P.getLife() + 1 );
					return true;
				}
			}
		}
		return false;
	}
}
