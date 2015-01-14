package bang.model.cards;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;


public class RateCardModel extends YellowCardModel{

	public RateCardModel()
	{
		super();
		this.setName("rate");
	}
	
	public void action(String pPlayer){
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		channelService.sendMessage(new ChannelMessage("player1", "missed"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player2", "missed"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player3", "missed"+pPlayer));
		channelService.sendMessage(new ChannelMessage("player4", "missed"+pPlayer));
		
	}
	
}
