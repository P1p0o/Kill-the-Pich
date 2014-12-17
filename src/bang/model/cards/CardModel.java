package bang.model.cards;

public class CardModel {
	
	private boolean FaceUp = false;
	protected String name;
	
	public CardModel()
	{
		
	}
	
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
