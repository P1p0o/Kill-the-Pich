package bang.model.game;

import java.util.ArrayList;

import javax.persistence.Id;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import bang.model.cards.CardModel;

public class PlayerModel {
	@Id Long id;
	private int Life = 4;
	private ArrayList<CardModel> Hand = new ArrayList<CardModel>();
	private String name;
	private String mRole;
	private String token;
	private String mail;
	
	public PlayerModel(String name, String pToken, String pMail)
	{
		setName(name);
		setToken(pToken);
		setMail(pMail);
	}
	
	public PlayerModel(){}
	
	public boolean hasCard(String pName)
	{
		for (CardModel C : Hand) {
			if( C.getName().equals(pName) )
			{
				return true;
			}
		}
		return false;
	}
	
	public void playCard(String pName)
	{
		CardModel lPlayedCard;
		for (CardModel C : Hand) {
			if( C.getName().equals(pName) )
			{
				Hand.remove(C);
				break;
			}
		}
	}
	
	public boolean isAlive()
	{
		if( this.getLife() == 0)
		{
			return false;
		}
		return true;
	}
	
	public int getLife() {
		return Life;
	}
	
	public String setLife(int pLife) {
		
		Life = pLife;
		
		if( Life == 0 && this.hasCard("poulemouth") )
		{
			//Chance de boire une biere
			ChannelService channelService = ChannelServiceFactory.getChannelService();
			
			channelService.sendMessage(new ChannelMessage("player1", "chancepoulemouth"+this.getName()));
			channelService.sendMessage(new ChannelMessage("player2", "chancepoulemouth"+this.getName()));
			channelService.sendMessage(new ChannelMessage("player3", "chancepoulemouth"+this.getName()));
			channelService.sendMessage(new ChannelMessage("player4", "chancepoulemouth"+this.getName()));
			
			return "alive";
		}
		else
		{
			//Mort
			if(Life < 0 || Life == 0)
			{
				Life = 0;

				ChannelService channelService = ChannelServiceFactory.getChannelService();
				channelService.sendMessage(new ChannelMessage("player1", "mort"+name));
				channelService.sendMessage(new ChannelMessage("player2", "mort"+name));
				channelService.sendMessage(new ChannelMessage("player3", "mort"+name));
				channelService.sendMessage(new ChannelMessage("player4", "mort"+name));

			}
		}
		return "alive";
	}
	public ArrayList<CardModel> getHand() {
		return Hand;
	}
	public void setHand(ArrayList<CardModel> hand) {
		Hand = hand;
	}
	public void addToHand(CardModel card) {
		Hand.add(card);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getmRole() {
		return mRole;
	}

	public void setmRole(String mRole) {
		this.mRole = mRole;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
}