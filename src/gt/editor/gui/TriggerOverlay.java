package gt.editor.gui;

import gt.editor.EditorTriggerManager;
import gt.editor.TriggerManagerObserver;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistance.YamlSerializable;
import gt.general.logic.response.Response;
import gt.general.logic.trigger.Trigger;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TriggerOverlay extends GenericPopup implements TriggerManagerObserver, Listener {

	private EditorTriggerManager triggerManager;
	private SpoutPlayer player;
		
	private static final int LIST_MARGIN_X = 5;
	private static final int LIST_MARGIN_Y = 10;
	
	private static final String NO_CONTEXT = "No Context selected";
	
	private SelectionListWidget<TriggerContext> contextList = new SelectionListWidget<TriggerContext>() {
		@Override
		public void onSelected(final int item, final boolean doubleClick) {
			super.onSelected(item, doubleClick);			
			buildItemList();
		}
	};
	private SelectionListWidget<YamlSerializable> itemList = new SelectionListWidget<YamlSerializable>();
	
	private GenericLabel currentContext = new GenericLabel();

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
		
		
		currentContext.setWidth(200)
					  .shiftXPos(- (currentContext.getWidth() + LIST_MARGIN_X))
					  .shiftYPos(contextList.getHeight() + 2 * LIST_MARGIN_Y);
		
		currentContext.setTextColor(new Color(0, 128, 0));
		currentContext.setAlign(WidgetAnchor.TOP_LEFT);

		currentContext.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), currentContext);
		
		buildContextList();
		triggerManager.addTriggerContextObserver(this);
		MultiListener.registerListener(this);
	}

	@Override
	public void update() {
		System.out.println("UPDATE CONTEXT LIST");
		buildContextList();
	}

	/**
	 * updates the context list, preserves the selection
	 */
	private void buildContextList() {
		
		TriggerContext oldSelectedContext = contextList.getSelectedObject();
		
		contextList.clear();
		contextList.clearSelection();
		
		for(TriggerContext context : triggerManager.getTriggerContexts()) {
			contextList.add(new ListWidgetItem(context.getLabel(), ""), context);
			
			if (context == oldSelectedContext) {
				int position = contextList.getItems().length - 1;
				contextList.setSelection(position);
			}
		}
		
		buildItemList();
	}
	
	/**
	 * updates the item list, preserves the selecton
	 */
	private void buildItemList() {
		/*
		 * TODO autoupdate this list
		 */
		TriggerContext selected = contextList.getSelectedObject();
		
		YamlSerializable oldSelected = itemList.getSelectedObject();
		itemList.clear();
		itemList.clearSelection();
		
		if(selected != null) {
			
			for(Trigger trigger : selected.getTriggers()) {
				itemList.add(new ListWidgetItem(trigger.getLabel(), ""), trigger);
				
				if (trigger == oldSelected) {
					int position = itemList.getItems().length - 1;
					itemList.setSelection(position);
				}
			}
			
			for(Response response : selected.getResponses()) {
				itemList.add(new ListWidgetItem(response.getLabel(), ""), response);
				
				if (response == oldSelected) {
					int position = itemList.getItems().length - 1;
					itemList.setSelection(position);
				}
			}
			
		}
	}
	
	@EventHandler
	public void onClose(final ScreenCloseEvent event){
		if(event.getScreen() == this) {
			super.onScreenClose(event);
			
			MultiListener.unregisterListener(this);
			triggerManager.removeTriggerContextObserver(this);
		}
	}
	
	@Override
	public void onTick() {
		super.onTick();
		setDirty(true);
	}
	
	
}
