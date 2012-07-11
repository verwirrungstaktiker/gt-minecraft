package gt.lastgnome.game;

import gt.editor.BuildManager;
import gt.editor.EditorTriggerManager;
import gt.editor.PlayerManager;
import gt.general.world.WorldManager;
import gt.plugin.meta.MultiListener;


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
		
		game.getWorldInstance().init(triggerManager);
	}

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
