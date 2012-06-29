package gt.general;

import gt.general.character.Team;
import gt.general.world.WorldInstance;

public interface GameBuilder {

	void instantiateGame();
	
	void loadMetadata(WorldInstance worldInstance);
	
	void updateGui();
	
	
	void finalizeGame();
	
	Game getGame();

	
}
