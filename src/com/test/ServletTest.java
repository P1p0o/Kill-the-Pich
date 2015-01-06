package com.test;
 
import java.io.IOException;
 
import javax.servlet.http.*;
 
import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
 
 
@SuppressWarnings("serial")
public class ServletTest extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	String player = req.getParameter("player");
	
	//ChannelService channelService = ChannelServiceFactory.getChannelService();
	//channelService.sendMessage(new ChannelMessage("mimo", "PAFF "));
	
	if(player.equals("1")){
		ChannelService channelService1 = ChannelServiceFactory.getChannelService();
		channelService1.sendMessage(new ChannelMessage("player1", "PAFF "));
	}
	if(player.equals("2")){
		ChannelService channelService2 = ChannelServiceFactory.getChannelService();
		channelService2.sendMessage(new ChannelMessage("player2", "PAFF "));
	}
	if(player.equals("3")){
		ChannelService channelService3 = ChannelServiceFactory.getChannelService();
		channelService3.sendMessage(new ChannelMessage("player3", "PAFF "));
	}
	if(player.equals("4")){
		ChannelService channelService4 = ChannelServiceFactory.getChannelService();
		channelService4.sendMessage(new ChannelMessage("player4", "PAFF "));
	}
	
	
	}
	
}