package bang.model.cards;

import java.util.ArrayList;

import javax.persistence.Id;

import bang.manager.CacheManager;
import bang.model.game.GameModel;
import bang.model.game.PlayerModel;

public abstract class CardModel {
	@Id Long id;
	private boolean FaceUp = false;
	protected String name;
	
	public CardModel()
	{
		
	}
	
	public void action(){}
	
	public void action(PlayerModel pPlayer){}
	
	public boolean isFaceUp() {
		return FaceUp;
	}
	public void setFaceUp(boolean faceUp) {
		FaceUp = faceUp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void incrementLife(String pPlayer){
		CacheManager cacheManager;
		cacheManager = CacheManager.getInstance();
		GameModel gameModel = (GameModel) cacheManager.get("game");
		ArrayList<PlayerModel> listPlayers = gameModel.getListPlayers();
		for(PlayerModel playerModel : listPlayers){
			System.out.println(playerModel.getLife());
			if(playerModel.getName().equals("player"+pPlayer)){
				
				playerModel.setLife(playerModel.getLife()+1);
			}
			System.out.println(playerModel.getLife());			
		}
	}
	
	
}
