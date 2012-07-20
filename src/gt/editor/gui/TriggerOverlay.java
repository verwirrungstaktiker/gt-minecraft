package gt.editor.gui;

import gt.editor.EditorTriggerManager;
import gt.editor.TriggerManagerObserver;
import gt.general.logic.TriggerContext;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericListView;
import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TriggerOverlay extends GenericPopup implements TriggerManagerObserver, Listener {

	EditorTriggerManager triggerManager;
	private SpoutPlayer player;
	
	private GenericContainer container;
	
	
	public TriggerOverlay(final SpoutPlayer player, final EditorTriggerManager triggerManager) {
		this.triggerManager = triggerManager;
		
		triggerManager.addTriggerContextObserver(this);
		
		
		container = new GenericContainer();
		
		attachWidget(Hello.getPlugin(), new GenericLabel("OVERLAY"));
		attachWidget(Hello.getPlugin(), container);
		
		buildList();
		MultiListener.registerListener(this);
	}

	@Override
	public void update() {
		buildList();
	}

	private void buildList() {
		System.out.println("available contexts");
	
		GenericListWidget list = new GenericListWidget();
		
		
		
		for(TriggerContext context : triggerManager.getTriggerContexts()) {
			list.addItem(new ListWidgetItem(context.getLabel(), ""));
			//System.out.println(context.getLabel());
		}
		
		for(Widget w : container.getChildren()) {
			container.removeChild(w);
		}
		
		container.addChild(list);
		container.setDirty(true);
		
		
		
	}
	
	@EventHandler
	public void onClose(final ScreenCloseEvent event){
		super.onScreenClose(event);
		
		MultiListener.unregisterListener(this);
		triggerManager.removeTriggerContextObserver(this);		

		
		System.out.println("CLOSE");
	}
}
