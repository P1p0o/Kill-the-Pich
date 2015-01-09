package bang.model.game;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import bang.model.cards.CardModel;

public class PlayerModel {
	@Id Long id;
	private int Life = 4;
	private ArrayList<CardModel> Hand = new ArrayList<CardModel>();
	private String name;
	private String token;
	
	public PlayerModel(String pToken)
	{
		setToken(pToken);
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
	
	public int getLife() {
		return Life;
	}
	public void setLife(int life) {
		Life = life;
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
	
	
}