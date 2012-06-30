package gt.general;

import gt.general.character.Team;
import gt.general.world.WorldInstance;
import gt.general.world.WorldManager;

public interface GameBuilder {

	void instantiateGame();
	
	void buildWorldInstance(WorldManager worldManager, String worldName);
	
	void loadGameSpecific();
	
	void updateGui();
	
	
	void finalizeGame();
	
	Game getGame();

	
}
