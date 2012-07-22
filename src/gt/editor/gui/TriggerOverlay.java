package gt.editor.gui;

import gt.editor.EditorTriggerManager;
import gt.editor.LogicObserver;
import gt.editor.PlayerManager;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistance.YamlSerializable;
import gt.general.logic.response.Response;
import gt.general.logic.trigger.Trigger;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class TriggerOverlay extends GenericPopup implements LogicObserver, Listener {

	private final EditorTriggerManager triggerManager;
	private final PlayerManager playerManager;
	private final SpoutPlayer player;
		
	private static final int MARGIN_X = 5;
	private static final int MARGIN_Y = 10;
	
	private static final String NO_CONTEXT = "No Context";

	/**
	 * special selection behaviour: only the currently active context may be selected
	 */
	private class ContextListWidget extends SelectionListWidget<TriggerContext> {

		private int oldSelectedIndex = 0;
		
		@Override
		public void onSelected(final int selectedIndex, final boolean doubleClick) {			
			TriggerContext oldSelected = getObject(getItem(oldSelectedIndex));

			// only allow to change if empty element, context is complete or current is reselected
			if(oldSelected == null || oldSelected.isComplete() || selectedIndex == oldSelectedIndex) {
				super.onSelected(selectedIndex, doubleClick);

				oldSelectedIndex = selectedIndex;
				buildItemList();
			} else {
				setSelection(oldSelectedIndex);
			}
		}
		
		/**
		 * resets overrides the context constraint
		 * @param n the new selection
		 */
		public void forceSelection(final int n) {
			oldSelectedIndex = n;
			super.setSelection(n);
		}
		
	};
	
	private ContextListWidget contextList = new ContextListWidget();
	private SelectionListWidget<YamlSerializable> itemList = new SelectionListWidget<YamlSerializable>();
	
	private GenericButton left1 = new GenericButton("Rename selected context") {
		public void onButtonClick(final ButtonClickEvent event) {
			System.out.println("LEFT 1");
		};
	};
	
	private class ContextButton extends GenericButton {
		
		/** updates the textx of this button */
		public void updateText() {
			if(playerManager.canCreateContext(player)) {
				setText("New Context");
			} else {
				setText("Delete selected Context!");
			}
		}

		@Override
		public void onButtonClick(final ButtonClickEvent event) {			
			if(playerManager.canCreateContext(player)) {
				playerManager.createContext(player);
			} else {
				playerManager.cancelContext(player);
			}
			
			buildContextList();
			updateText();
		};	
	};
	private ContextButton left2 = new ContextButton();
	
	private GenericButton left3 = new GenericButton("Toggle Highlight [on]") {
		public void onButtonClick(final ButtonClickEvent event) {
			System.out.println("LEFT 3");
		};
	};

	private GenericButton right1 = new GenericButton("Rename selected item") {
		public void onButtonClick(final ButtonClickEvent event) {
			System.out.println("right 1");
		};
	};
	private GenericButton right2 = new GenericButton() {
		public void onButtonClick(final ButtonClickEvent event) {
			System.out.println("right 2");
		};
	};
	private GenericButton right3 = new GenericButton("Select blocks ...") {
		public void onButtonClick(final ButtonClickEvent event) {
			System.out.println("right 3");
		};
	};
	
	/**
	 * 
	 * @param player the player who opened this overlay
	 * @param triggerManager the currently running trigger manager
	 * @param playerManager the currently running player manager
	 */
	public TriggerOverlay(final SpoutPlayer player, final EditorTriggerManager triggerManager, final PlayerManager playerManager) {
		this.player = player;
		this.triggerManager = triggerManager;
		this.playerManager = playerManager;
				
		setupGui();
		
		buildContextList();
		triggerManager.addLogicObserver(this);
		playerManager.addLogicObserver(this);
		MultiListener.registerListener(this);
	}

	@Override
	public void update(final Observee type, final Object what) {		
		buildContextList();
	}

	/**
	 * updates the context list, preserves the selection
	 */
	private void buildContextList() {
		
		contextList.clear();
		contextList.addItem(new ListWidgetItem(NO_CONTEXT, ""));
		contextList.forceSelection(0);
		
		for(TriggerContext context : triggerManager.getTriggerContexts()) {
			
			String subtext = "";
			
			if(!context.isComplete()) {
				subtext = "Context not complete - cannot save!";
			}
			
			contextList.add(new ListWidgetItem(context.getLabel(), subtext), context);
			
			if (context == playerManager.getcontext(player)) {
				int position = contextList.getItems().length - 1;
				contextList.forceSelection(position);
			}
		}
		
		buildItemList();
	}
	
	/**
	 * updates the item list, preserves the selecton
	 */
	private void buildItemList() {
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
			triggerManager.removeLogicObserver(this);
			playerManager.removeLogicObserver(this);
		}
	}
	
	@Override
	public void onTick() {
		super.onTick();
		setDirty(true);
	}

	private void setupGui() {
		// setup context list
		contextList.setWidth(200)
					.setHeight(200)
					.shiftXPos(- (contextList.getWidth() + MARGIN_X))
					.shiftYPos(MARGIN_Y);
		
		contextList.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), contextList);
		
		// setup item list
		itemList.setWidth(200)
				.setHeight(200)
				.shiftXPos(MARGIN_X)
				.shiftYPos(MARGIN_Y);
		
		itemList.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), itemList);
		
		// left 1
		left1.setWidth(200)
			 .setHeight(20)
			 .shiftXPos(- (left1.getWidth() + MARGIN_X))
			 .shiftYPos(contextList.getHeight() + 2 * MARGIN_Y);
		
		left1.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), left1);
		
		// left 2
		left2.setWidth(200)
			 .setHeight(20)
			 .shiftXPos(- (left2.getWidth() + MARGIN_X))
			 .shiftYPos(contextList.getHeight() + left1.getHeight()+ 3 * MARGIN_Y);
		
		left2.setAnchor(WidgetAnchor.TOP_CENTER);
		left2.updateText();
		attachWidget(Hello.getPlugin(), left2);
		
		// left 3
		left3.setWidth(200)
			 .setHeight(20)
			 .shiftXPos(- (left3.getWidth() + MARGIN_X))
			 .shiftYPos(contextList.getHeight() + left1.getHeight() + left2.getHeight() + 4 * MARGIN_Y);
		
		left3.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), left3);
		
		// right 1
		right1.setWidth(200)
			 .setHeight(20)
			 .shiftXPos(MARGIN_X)
			 .shiftYPos(itemList.getHeight() + 2 * MARGIN_Y);
		
		right1.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), right1);
		
		// right 2
		right2.setWidth(200)
			 .setHeight(20)
			 .shiftXPos(MARGIN_X)
			 .shiftYPos(itemList.getHeight() + right1.getHeight()+ 3 * MARGIN_Y);
		
		right2.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), right2);
		
		// right 3
		right3.setWidth(200)
			 .setHeight(20)
			 .shiftXPos(MARGIN_X)
			 .shiftYPos(itemList.getHeight() + right1.getHeight() + right2.getHeight() + 4 * MARGIN_Y);
		
		right3.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(Hello.getPlugin(), right3);
	}
	
	
}
