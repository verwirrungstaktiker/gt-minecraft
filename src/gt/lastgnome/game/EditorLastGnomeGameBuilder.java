/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome.game;

import gt.editor.BuildManager;
import gt.editor.EditorFacade;
import gt.editor.EditorTriggerManager;
import gt.editor.ParticleManager;
import gt.editor.PlayerManager;
import gt.editor.gui.BlockSet;
import gt.editor.gui.EditorGuiManager;
import gt.editor.gui.EditorOverlay.LandingPage;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.WorldManager;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;


public class EditorLastGnomeGameBuilder extends AbstractLastGnomeGameBuilder {

	private EditorLastGnomeGame game;
	private EditorTriggerManager triggerManager;
	private PlayerManager playerManager;
	private ParticleManager particleManager;
	private BuildManager buildManager;
	
	
	@Override
	public void instantiateGame() {
		game = new EditorLastGnomeGame();
	}

	@Override
	public void buildWorldInstance(final WorldManager worldManager, final String worldName) throws PersistenceException {
		super.buildWorldInstance(worldManager, worldName);
				
		triggerManager = new EditorTriggerManager();
		
		
		playerManager = new PlayerManager(triggerManager, null);
		
		particleManager = new ParticleManager(playerManager);
		buildManager = new BuildManager(playerManager);
		
		game.getWorldInstance().init(triggerManager);
	}

	@Override
	public void startGame() {
		
		EditorFacade facade = new EditorFacade(triggerManager,
												playerManager,
												particleManager);
		
		MultiListener.registerListeners(playerManager,
				buildManager,
				particleManager);
		
		
		EditorGuiManager guiManager = new EditorGuiManager(facade);
		
		SpoutManager
			.getKeyBindingManager()
			.registerBinding(LandingPage.TRIGGER_PAGE.toString(),
								Keyboard.KEY_C, 
								"trigger overlay",
								guiManager,
								Hello.getPlugin());
		
		SpoutManager
			.getKeyBindingManager()
			.registerBinding(LandingPage.BLOCKS_PAGE.toString(),
								Keyboard.KEY_X, 
								"block selector",
								guiManager,
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
	
	public void reload() throws PersistenceException {
		game.dispose();
				
		loadGameSpecific();
		game.getWorldInstance().init(triggerManager);
	}
	
}
