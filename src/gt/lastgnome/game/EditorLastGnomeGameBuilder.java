package gt.lastgnome.game;

import gt.editor.BuildManager;
import gt.editor.EditorFacade;
import gt.editor.EditorTriggerManager;
import gt.editor.ParticleManager;
import gt.editor.PlayerManager;
import gt.editor.gui.EditorGuiManager;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.general.world.WorldManager;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.keyboard.Keyboard;


public class EditorLastGnomeGameBuilder extends AbstractLastGnomeGameBuilder {

	private EditorLastGnomeGame game;
	private EditorTriggerManager triggerManager;
	private PlayerManager playerManager;
	private ParticleManager particleManager;
	
	
	@Override
	public void instantiateGame() {
		game = new EditorLastGnomeGame();
	}

	@Override
	public void buildWorldInstance(final WorldManager worldManager, final String worldName) throws PersistanceException {
		super.buildWorldInstance(worldManager, worldName);
				
		triggerManager = new EditorTriggerManager();
		particleManager = new ParticleManager();
		
		playerManager = new PlayerManager(triggerManager, particleManager);
		BuildManager buildManager = new BuildManager(playerManager);
		
		MultiListener.registerListeners(playerManager,
										buildManager);
		
		game.getWorldInstance().init(triggerManager);
		
		
		
		

	}

	@Override
	public void startGame() {
		
		EditorFacade facade = new EditorFacade(triggerManager,
												playerManager,
												particleManager);
		
		SpoutManager
			.getKeyBindingManager()
			.registerBinding("triggerOverlay",
								Keyboard.KEY_C, 
								"trigger overlay",
								new EditorGuiManager(facade),
								Hello.getPlugin());
		
		Hello.scheduleSyncTask(particleManager, 0, 20);
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
	
	public void reload() throws PersistanceException {
		game.getWorldInstance().dispose();
		game.dispose();
				
		loadGameSpecific();
		game.getWorldInstance().init(triggerManager);
	}
	
}
