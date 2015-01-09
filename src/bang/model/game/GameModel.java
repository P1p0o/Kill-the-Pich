package bang.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.persistence.Id;

import bang.model.cards.CardModel;
import bang.model.cards.PafCardModel;
import bang.model.cards.RateCardModel;

public class GameModel {
	@Id Long id;
	private ArrayList<CardModel> CardsDeck = new ArrayList<CardModel>();
	private ArrayList<PlayerModel> mListPlayers = new ArrayList<PlayerModel>();
	private Integer availableSlots = 4;
	private Integer nbPlayers = 4;
	
	public GameModel()
	{
		GenerateCardsDeck();
		
		CreatePlayers(4);
		
		HandOutCards();
		
		
		
		//Sherif starts
		//if(Players.get(0).hasCard("paf"))
		//{
		//	Players.get(0).playCard("paf");
		//	PafCardModel SherifPaf = new PafCardModel();
		//	SherifPaf.action(Players.get(1));
		//}
		
		
		//System.out.println("ready to play!");
	}
	
	public boolean startGame()
	{
		if(availableSlots == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void GenerateCardsDeck()
	{
		
		int i=0;
		
		for(i=0;i<16;i++){
			PafCardModel NewPaf = new PafCardModel();
			this.CardsDeck.add( NewPaf );
		}
		for(i=0;i<7;i++){
			RateCardModel NewRate = new RateCardModel();
			this.CardsDeck.add( NewRate );
		}
		Collections.shuffle(CardsDeck);
		
		for (CardModel C : CardsDeck) {
			System.out.println(C.getName());
		}
	}
	
	private void CreatePlayers(int nbPlayers)
	{
		ArrayList<String> lRoles = new ArrayList<String>();
		
		//Building roles array
		lRoles.add("sherif");
		lRoles.add("adjoint");
		lRoles.add("renegat");
		lRoles.add("hll");
		
		//Randomize roles array
		Random rnd = new Random();
		for (int i = lRoles.size() - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			String a = lRoles.get(index);
			lRoles.set(index, lRoles.get(i));
			lRoles.set(i, a);
		}
		
		//Set players' roles
		for( PlayerModel Player : mListPlayers )
		{
			Player.setName(lRoles.get(0));
			lRoles.remove(0);
		}
	}
	
	private void HandOutCards( )
	{
		int i = 1;
		Iterator<CardModel> it = CardsDeck.iterator();
		CardModel currentCard;
		for ( i=1 ; i<=5 ; i++ ) //Jamais plus de 5 cartes pour un joueur
		{
			for (PlayerModel P : mListPlayers) {
				if( P.getLife() >= i )
				{
					currentCard = it.next();
					P.addToHand(currentCard);
					it.remove();
					System.out.println(P.getName()+": "+currentCard.getName());
				}
			}
		}
	}
	
	public void addPlayer (String token)
	{
		PlayerModel lNewPlayer = new PlayerModel(token);
		mListPlayers.add(lNewPlayer);
	}

	public Integer getAvailableSlots() {
		return availableSlots;
	}

	public void setAvailableSlots(Integer availableSlots) {
		this.availableSlots = availableSlots;
	}

	public Integer getNbPlayers() {
		return nbPlayers;
	}

	public void setNbPlayers(Integer nbPlayers) {
		this.nbPlayers = nbPlayers;
	}

	public ArrayList<PlayerModel> getListPlayers() {
		return mListPlayers;
	}

}
