package gt.lastgnome.game;

import gt.general.Game;
import gt.general.GameBuilder;
import gt.general.character.ZombieManager;
import gt.general.world.WorldInstance;
import gt.lastgnome.GnomeSocketEnd;
import gt.lastgnome.GnomeSocketStart;

public abstract class AbstractLastGnomeGameBuilder implements GameBuilder {

	protected abstract AbstractLastGnomeGame getAbstractGame();
	
	@Override
	public void loadWorldSpecific(final WorldInstance worldInstance) {
		
		GnomeSocketStart start = new GnomeSocketStart(getAbstractGame());
		start.setup(worldInstance.loadMeta("start_socket.yml"),
					worldInstance.getWorld());
		
		GnomeSocketEnd end = new GnomeSocketEnd(getAbstractGame());
		end.setup(worldInstance.loadMeta("end_socket.yml"),
				  worldInstance.getWorld());
		
		
		ZombieManager zombieManager = new ZombieManager(worldInstance.getWorld());
		getAbstractGame().setZombieManager(zombieManager);
		
	}

	@Override
	public Game getGame() {
		return getAbstractGame();
	}
	
}
