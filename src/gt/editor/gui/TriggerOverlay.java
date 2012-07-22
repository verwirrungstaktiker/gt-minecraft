package gt.editor.gui;

import gt.editor.EditorTriggerManager;
import gt.editor.LogicObserver;
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

	private EditorTriggerManager triggerManager;
	private SpoutPlayer player;
		
	private static final int MARGIN_X = 5;
	private static final int MARGIN_Y = 10;
	
	private static final String NO_CONTEXT = "No Context";
	
	private SelectionListWidget<TriggerContext> contextList = new SelectionListWidget<TriggerContext>() {
		@Override
		public void onSelected(final int item, final boolean doubleClick) {
			super.onSelected(item, doubleClick);			
			buildItemList();
		}
	};
	private SelectionListWidget<YamlSerializable> itemList = new SelectionListWidget<YamlSerializable>();
	
	private GenericButton left1 = new GenericButton("Rename selected context") {
		public void onButtonClick(final ButtonClickEvent event) {
			System.out.println("LEFT 1");
		};
	};
	private GenericButton left2 = new GenericButton() {
		public void onButtonClick(final ButtonClickEvent event) {
			System.out.println("LEFT 2");
		};
	};
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
	 */
	public TriggerOverlay(final SpoutPlayer player, final EditorTriggerManager triggerManager) {
		this.player = player;
		this.triggerManager = triggerManager;
				
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
		
		
		buildContextList();
		triggerManager.addTriggerContextObserver(this);
		MultiListener.registerListener(this);
	}

	@Override
	public void update(final Observee type, final Object what) {
		
		switch (type) {
		case TRIGGER_MANAGER:
			System.out.println("UPDATE CONTEXT LIST");
			buildContextList();
			break;

		case TRIGGER_CONTEXT:
			if(contextList.getSelectedObject() == what) {
				System.out.println("UPDATE ITEM LIST");
				buildItemList();
			}
			break;
			
			
		default:break;
		}
		

	}

	/**
	 * updates the context list, preserves the selection
	 */
	private void buildContextList() {
		
		TriggerContext oldSelectedContext = contextList.getSelectedObject();
		
		contextList.clear();
		contextList.clearSelection();
		
		contextList.addItem(new ListWidgetItem(NO_CONTEXT, ""));
		contextList.setSelection(0);
		
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
