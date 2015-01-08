package bang.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import bang.model.cards.CardModel;
import bang.model.cards.PafCardModel;
import bang.model.cards.RateCardModel;

public class GameModel {
	
	private ArrayList<CardModel> CardsDeck = new ArrayList<CardModel>();
	private ArrayList<PlayerModel> listPlayers = new ArrayList<PlayerModel>();
	private Integer availableSlots = 4;
	private Integer nbPlayers = 4;
	
	public GameModel()
	{
		GenerateCardsDeck();
		
		//CreatePlayers(4);
		
		//HandOutCards();
		
		
		
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
		
		if( nbPlayers == 4 )
		{
			//Players.add( new SherifPlayerModel() );
			//Players.add( new RenegatPlayerModel() );
			//Players.add( new HLLPlayerModel() );
			//Players.add( new AdjointPlayerModel() );
		}
	}
	
	private void HandOutCards( )
	{
		int i = 1;
		Iterator<CardModel> it = CardsDeck.iterator();
		CardModel currentCard;
		for ( i=1 ; i<=5 ; i++ ) //Jamais plus de 5 cartes pour un joueur
		{
			for (PlayerModel P : listPlayers) {
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
		listPlayers.add(lNewPlayer);
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
		return listPlayers;
	}

}
