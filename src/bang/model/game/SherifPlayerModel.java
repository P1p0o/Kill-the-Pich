package bang.model.game;

public class SherifPlayerModel extends PlayerModel {

	public SherifPlayerModel()
	{
		super();
		this.setName("sherif");
		this.setLife(this.getLife()+1);  //1 point de vie de plus pour le sherif
	}
}
