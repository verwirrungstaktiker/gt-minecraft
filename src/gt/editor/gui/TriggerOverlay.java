package gt.editor.gui;

import static com.google.common.collect.Maps.*;
import gt.editor.EditorTriggerManager;
import gt.editor.TriggerManagerObserver;
import gt.general.logic.TriggerContext;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.ListWidget;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TriggerOverlay extends GenericPopup implements TriggerManagerObserver, Listener {

	private EditorTriggerManager triggerManager;
	private SpoutPlayer player;
		
	private static final int LIST_MARGIN_X = 5;
	private static final int LIST_MARGIN_Y = 10;
	
	/**
	 * inner class to keep track of selected contexts
	 */
	private class ContextListWidget extends GenericListWidget {
		private Map<ListWidgetItem, TriggerContext> map = newHashMap();
		
		@Override
		public void onSelected(final int item, final boolean doubleClick) {
			super.onSelected(item, doubleClick);			
			buildItemList(map.get(getItem(item)));
		}
		
		/**
		 * @param context the context to be added
		 */
		public void addContext(final TriggerContext context) {
			ListWidgetItem item = new ListWidgetItem(context.getLabel(), "");
			super.addItem(item);
			map.put(item, context);
		}
		
		/**
		 * @return the currently selected trigger context
		 */
		public TriggerContext getSelectedContext() {
			return map.get(getSelectedItem());
		}
		
		@Override
		public ListWidget addItem(final ListWidgetItem item) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public void clear() {
			super.clear();
			map.clear();
		}
	}
	
	private ContextListWidget contextList = new ContextListWidget();
	private GenericListWidget itemList = new GenericListWidget();

	/**
	 * 
	 * @param player the player who opened this overlay
	 * @param triggerManager the currently running trigger manager
	 */
	public TriggerOverlay(final SpoutPlayer player, final EditorTriggerManager triggerManager) {
		this.player = player;
		this.triggerManager = triggerManager;
				
		// setup context list
		contextList.setWidth(200)
					.setHeight(200)
					.shiftXPos(- (contextList.getWidth() + LIST_MARGIN_X))
					.shiftYPos(LIST_MARGIN_Y);
		
		contextList.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), contextList);
		
		// setup item list
		itemList.setWidth(200)
				.setHeight(200)
				.shiftXPos(LIST_MARGIN_X)
				.shiftYPos(LIST_MARGIN_Y);
		
		itemList.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), itemList);
		
		
		buildContextList();
		triggerManager.addTriggerContextObserver(this);
		MultiListener.registerListener(this);
	}

	@Override
	public void update() {
		System.out.println("UPDATE CONTEXT LIST");
		buildContextList();
	}

	private void buildContextList() {
		
		TriggerContext oldSelectedContext = contextList.getSelectedContext();
		
		contextList.clear();
		
		for(TriggerContext context : triggerManager.getTriggerContexts()) {
			contextList.addContext(context);
			
			if (context == oldSelectedContext) {
				int position = contextList.getItems().length - 1;
				contextList.setSelection(position);
			}
		}
	}
	
	private void buildItemList(final TriggerContext context) {
		System.out.println(context.getLabel());
		
		itemList.clear();
		
	}
	
	@EventHandler
	public void onClose(final ScreenCloseEvent event){
		if(event.getScreen() == this) {
			super.onScreenClose(event);
			
			MultiListener.unregisterListener(this);
			triggerManager.removeTriggerContextObserver(this);
			
			System.out.println("CLOSE");
		}
	}
	
	@Override
	public void onTick() {
		super.onTick();
		setDirty(true);
	}
	
	
}
