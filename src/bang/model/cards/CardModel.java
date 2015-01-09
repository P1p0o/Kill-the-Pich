package bang.model.cards;

import javax.persistence.Id;

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
	
	
}
