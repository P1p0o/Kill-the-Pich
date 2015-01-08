package bang.dao.fabric;

import java.util.HashMap;
import java.util.Map;

import bang.dao.instance.GameDao;

import javax.cache.*;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

public final class DaoFabric 
{
	private static volatile DaoFabric instance = null;
	
	Cache cache=null;
	   
	private Cache Cache = cache;

	private DaoFabric() 
	{
		super();
	    cache = initialisationCache(cache);
	}
	
	private Cache initialisationCache(Cache cache)
    {
    	
    	Map props = new HashMap();
		   props.put(GCacheFactory.EXPIRATION_DELTA, 3600);
		   props.put(MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT, true);
		   try {
		   
		   CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();

		   cache = cacheFactory.createCache(props);

		   } catch (CacheException e) {

		   }
		   
    	return cache;
    }

	public final static DaoFabric getInstance() 
	{
		if(DaoFabric.instance == null) 
		{
			synchronized(DaoFabric.class) 
			{
				if(DaoFabric.instance == null) 
				{
					DaoFabric.instance = new DaoFabric();
				}
			}
		}
		return DaoFabric.instance;
	}

	public GameDao createGameDao() 
	{
		GameDao gameDao = new GameDao(this.Cache);
		return gameDao;
	}

}
