package bang.model.game;

import java.util.ArrayList;
import java.util.List;

import bang.model.cards.CardModel;

public class PlayerModel {
	
	private int Life = 4;
	private ArrayList<CardModel> Hand = new ArrayList<CardModel>();
	private String name;
	
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
	
	
}