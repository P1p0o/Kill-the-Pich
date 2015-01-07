package bang.model.cards;

import bang.model.game.PlayerModel;

public class PafCardModel extends YellowCardModel{
	
	public PafCardModel()
	{
		super();
		this.setName("paf");
	}
	
	public void action(PlayerModel pPlayer)
	{
		//Notify players
		
		
		//Wait for pafed player's response
		
		
		//Refresh life
		pPlayer.setLife(pPlayer.getLife()-1);
		
		//Notify players
		System.out.println(pPlayer.getName() + " vie " + pPlayer.getLife());
	}
}
