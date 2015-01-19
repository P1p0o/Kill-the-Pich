package bang.manager;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import bang.model.game.GameModel;
import java.lang.Integer;
import bang.manager.CacheManager;

public class CustomChannelManager {
	
	private GameModel mCurrentGame;
	
	public CustomChannelManager(String pGameID)
	{
		CacheManager lCache = new CacheManager();
		mCurrentGame = (GameModel) lCache.get("game");
		
		
		
	}
	
	public void NotifyAll(String pMessage)
	{
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage(mCurrentGame.getListPlayers().get(0).getToken(), pMessage));
		channelService.sendMessage(new ChannelMessage(mCurrentGame.getListPlayers().get(1).getToken(), pMessage));
		channelService.sendMessage(new ChannelMessage(mCurrentGame.getListPlayers().get(2).getToken(), pMessage));
		channelService.sendMessage(new ChannelMessage(mCurrentGame.getListPlayers().get(3).getToken(), pMessage));
	}
	
	public void NotifyPlayer( String pPlayerNumber, String pMessage)
	{
		
		String lToken = mCurrentGame.getListPlayers().get( Integer.parseInt(pPlayerNumber) ).getToken();
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage(lToken, pMessage));
	}
}
