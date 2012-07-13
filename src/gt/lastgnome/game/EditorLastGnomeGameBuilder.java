package gt.lastgnome.game;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.keyboard.Keyboard;

import gt.editor.BuildManager;
import gt.editor.EditorTriggerManager;
import gt.editor.PlayerManager;
import gt.editor.gui.GuiManager;
import gt.editor.gui.TriggerOverlay;
import gt.general.world.WorldManager;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;


public class EditorLastGnomeGameBuilder extends AbstractLastGnomeGameBuilder {

	private EditorLastGnomeGame game;
	private EditorTriggerManager triggerManager;
	
	
	@Override
	public void instantiateGame() {
		game = new EditorLastGnomeGame();
	}

	@Override
	public void buildWorldInstance(final WorldManager worldManager, final String worldName) {
		super.buildWorldInstance(worldManager, worldName);
				
		triggerManager = new EditorTriggerManager();
		PlayerManager playerManager = new PlayerManager(triggerManager);
		BuildManager buildManager = new BuildManager(playerManager);
		
		MultiListener.registerListeners(playerManager,
										buildManager);
		
		game.getWorldInstance().init(triggerManager);
	}

	@Override
	public void startGame() {
		
		SpoutManager
			.getKeyBindingManager()
			.registerBinding("triggerOverlay",
								Keyboard.KEY_C, 
								"trigger overlay",
								new GuiManager(triggerManager),
								Hello.getPlugin());
		
	}

	@Override
	protected AbstractLastGnomeGame getAbstractGame() {
		return game;
	}

	/**
	 * @return the game that is wrapped
	 */
	public EditorLastGnomeGame getEditorGame() {
		return game;
	}
	
	public void reload() {
		game.getWorldInstance().dispose();
		game.dispose();
				
		loadGameSpecific();
		game.getWorldInstance().init(triggerManager);
	}
	
}
