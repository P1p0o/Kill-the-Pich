package bang.model.cards;

import java.util.ArrayList;

import bang.manager.CacheManager;
import bang.model.game.GameModel;
import bang.model.game.PlayerModel;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class ChasseCardModel extends YellowCardModel {
	
	public ChasseCardModel()
	{
		super();
		this.setName("chasse");
	}
	
	public void goChasse (String pPlayer)
	{
		
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage("player1", "chasse"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player2", "chasse"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player3", "chasse"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player4", "chasse"+pPlayer));
		
		CacheManager cacheManager;
		cacheManager = CacheManager.getInstance();
		GameModel gameModel = (GameModel) cacheManager.get("game");
		gameModel.drawCard(pPlayer);
		
		channelService.sendMessage(new ChannelMessage("player1", "refreshHand"));
		channelService.sendMessage(new ChannelMessage("player2", "refreshHand"));
		channelService.sendMessage(new ChannelMessage("player3", "refreshHand"));
		channelService.sendMessage(new ChannelMessage("player4", "refreshHand"));
		
	}
}
