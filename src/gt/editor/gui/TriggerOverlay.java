package gt.editor.gui;

import gt.editor.EditorTriggerManager;
import gt.editor.TriggerManagerObserver;
import gt.general.logic.TriggerContext;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TriggerOverlay extends GenericPopup implements TriggerManagerObserver, Listener {

	EditorTriggerManager triggerManager;
	private SpoutPlayer player;
	
	public TriggerOverlay(final SpoutPlayer player, final EditorTriggerManager triggerManager) {
		this.triggerManager = triggerManager;
		
		triggerManager.addTriggerContextObserver(this);
		
		attachWidget(Hello.getPlugin(), new GenericLabel("OVERLAY"));
		
		buildList();
		MultiListener.registerListener(this);
	}

	@Override
	public void update() {
		buildList();
	}

	private void buildList() {
		System.out.println("available contexts");
		
		for(TriggerContext context : triggerManager.getTriggerContexts()) {
			System.out.println(context.getLabel());
		}
	}
	
	@Override
	@EventHandler
	public void onScreenClose(final ScreenCloseEvent event){
		super.onScreenClose(event);
		
		MultiListener.unregisterListener(this);
		triggerManager.removeTriggerContextObserver(this);		

		
		System.out.println("CLOSE");
	}
}
