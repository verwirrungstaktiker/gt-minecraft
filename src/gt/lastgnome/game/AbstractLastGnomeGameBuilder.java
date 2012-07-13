package gt.lastgnome.game;

import gt.general.Game;
import gt.general.GameBuilder;
import gt.general.character.ZombieManager;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.general.world.WorldInstance;
import gt.general.world.WorldManager;
import gt.lastgnome.GnomeSocketEnd;
import gt.lastgnome.GnomeSocketStart;

/**
 * Builds a suitable LastGnome Game
 * see Builder Pattern (AbstractBuilder)
 */
public abstract class AbstractLastGnomeGameBuilder implements GameBuilder {

	/**
	 * @return the LastGnomeGame that was build
	 */
	protected abstract AbstractLastGnomeGame getAbstractGame();
	
	@Override
	public void buildWorldInstance(final WorldManager worldManager, final String worldName) throws PersistanceException {
		AbstractLastGnomeGame game = getAbstractGame();
		
		WorldInstance worldInstance = worldManager.getWorld(worldName);
		game.setWorldInstance(worldInstance);
	}

	
	
	@Override
	public void loadGameSpecific() throws PersistanceException {
		
		AbstractLastGnomeGame game = getAbstractGame();
		WorldInstance worldInstance = game.getWorldInstance();
		
		
		GnomeSocketStart start = new GnomeSocketStart(game);
		start.setup(worldInstance.loadMeta(GnomeSocketStart.FILENAME),
					worldInstance.getWorld());
		game.setStartSocket(start);
		
		
		GnomeSocketEnd end = new GnomeSocketEnd(game);
		end.setup(worldInstance.loadMeta(GnomeSocketEnd.FILENAME),
				  worldInstance.getWorld());
		game.setEndSocket(end);
		
		
		ZombieManager zombieManager = new ZombieManager(worldInstance.getWorld());
		game.setZombieManager(zombieManager);
		
	}

	@Override
	public Game getGame() {
		return getAbstractGame();
	}
	
}
