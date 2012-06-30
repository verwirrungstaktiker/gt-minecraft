package gt.lastgnome.game;

import gt.general.world.WorldManager;
import gt.plugin.helloeditor.EditorTriggerManager;


public class EditorLastGnomeGameBuilder extends AbstractLastGnomeGameBuilder {

	private EditorLastGnomeGame game;
	
	
	@Override
	public void instantiateGame() {
		game = new EditorLastGnomeGame();
	}

	@Override
	public void buildWorldInstance(final WorldManager worldManager, final String worldName) {
		super.buildWorldInstance(worldManager, worldName);
				
		// TODO must the Editor Trigger Manager be set up?
		worldManager.setupWorldInstance(game.getWorldInstance(),
										new EditorTriggerManager());
	}
	
	@Override
	public void updateGui() {}
	
	@Override
	public void finalizeGame() {}

	@Override
	protected AbstractLastGnomeGame getAbstractGame() {
		// TODO Auto-generated method stub
		return game;
	}
	
}
