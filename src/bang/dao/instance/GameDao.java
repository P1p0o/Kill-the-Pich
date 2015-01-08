package bang.dao.instance;

import java.util.ArrayList;

import javax.cache.Cache;

import com.google.appengine.api.datastore.Entity;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import bang.model.game.GameModel;
import bang.model.game.PlayerModel;

public class GameDao {
	private Cache mCache; 

	public GameDao(Cache pCache){
		setmCache(pCache);
	}

	public void saveToCache(GameModel pGameModel){
		ObjectifyService.register(GameModel.class);
		//ObjectifyService.register(PlayerModel.class);
		Objectify ofy = ObjectifyService.begin();
		ofy.put(pGameModel);
	}
	
	public GameModel loadFromCache(){
		GameModel gameModel = new GameModel();
		Objectify ofy = ObjectifyService.begin();
		gameModel = ofy.query(GameModel.class).get();
		return gameModel;
	}

	public Cache getmCache() {
		return mCache;
	}

	public void setmCache(Cache mCache) {
		this.mCache = mCache;
	}

}
