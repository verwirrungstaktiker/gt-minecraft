package gt.lastgnome.game;

import gt.general.world.WorldManager;
import gt.plugin.helloeditor.BuildManager;
import gt.plugin.helloeditor.EditorTriggerManager;
import gt.plugin.helloeditor.PlayerManager;
import gt.plugin.listener.MultiListener;


public class EditorLastGnomeGameBuilder extends AbstractLastGnomeGameBuilder {

	private EditorLastGnomeGame game;
	
	
	@Override
	public void instantiateGame() {
		game = new EditorLastGnomeGame();
	}

	@Override
	public void buildWorldInstance(final WorldManager worldManager, final String worldName) {
		super.buildWorldInstance(worldManager, worldName);
				
		EditorTriggerManager triggerManager = new EditorTriggerManager();
		PlayerManager playerManager = new PlayerManager(triggerManager);
		BuildManager buildManager = new BuildManager(playerManager);
		
		MultiListener.registerListeners(playerManager,
										buildManager);
		
		
		// TODO must the Editor Trigger Manager be set up?
		worldManager.setupWorldInstance(game.getWorldInstance(),
										triggerManager);
	}
	
	@Override
	public void updateGui() {}
	
	@Override
	public void startGame() {}

	@Override
	protected AbstractLastGnomeGame getAbstractGame() {
		return game;
	}

	public EditorLastGnomeGame getEditorGame() {
		return game;
	}
	
}
