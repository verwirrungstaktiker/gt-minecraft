package gt.editor.gui;

import gt.editor.EditorFacade;
import gt.editor.event.LogicChangeEvent;
import gt.editor.event.ParticleSuppressEvent;
import gt.general.gui.Prompt;
import gt.general.gui.Prompt.PromptCallback;
import gt.general.gui.Prompt.PromptCallback.Action;
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

public class TriggerOverlay extends GenericPopup implements Listener {
	
	private final SpoutPlayer player;
	private final EditorFacade facade;

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
				
				if(getSelectedObject() != null) {
					facade.switchToContext(player, getSelectedObject());
				} else {
					facade.exitContext(player);
				}
				
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

	private ToggleButton contextButton = new ToggleButton() {

		@Override
		protected String getASideText() {
			return "New Context";
		}

		@Override
		protected String getBSideText() {
			return "Delete selected Context!";
		}

		@Override
		protected void onASideClick(final ButtonClickEvent event) {
			facade.createContext(player);
		}

		@Override
		protected void onBSideClick(final ButtonClickEvent event) {
			facade.deleteContext(player);
		}

		@Override
		protected Side determineSide() {
			return facade.playerCanCreateContext(player) ? Side.A : Side.B;
		}
		
	};
	
	private ToggleButton highlightButton = new ToggleButton() {
		@Override
		protected String getASideText() {
			return "Toggle Highlight [on]";
		}

		@Override
		protected String getBSideText() {
			return "Toggle Highlight [off]";
		}

		@Override
		protected void onASideClick(final ButtonClickEvent event) {
			facade.setSuppressedHighlight(player, true);
		}

		@Override
		protected void onBSideClick(final ButtonClickEvent event) {
			facade.setSuppressedHighlight(player, false);
		}

		@Override
		protected Side determineSide() {
			return facade.hasSuppressedHighlight(player) ? Side.B : Side.A;
		}
	};
	

	private GenericButton right1 = new GenericButton("Rename selected item") {
		public void onButtonClick(final ButtonClickEvent event) {
			System.out.println("right 1");

			PromptCallback cb = new PromptCallback() {
				
				@Override
				public void onClose(Action action, String text) {
					System.out.println("CLOSE");
				}
			};
			
			
			player.getMainScreen().attachPopupScreen(new Prompt("test", cb));
			
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
	 * @param facade facade of the editor
	 */
	public TriggerOverlay(final SpoutPlayer player, final EditorFacade facade) {
		this.player = player;
		this.facade = facade;
		
		setupGui();
		
		buildContextList();
		
		MultiListener.registerListener(this);
	}

	@EventHandler
	public void onLogicChange(final LogicChangeEvent e) {
		System.out.println("logic changed");
		contextButton.updateSide();
		buildContextList();
	}
	
	@EventHandler
	public void onParticleSuppress(final ParticleSuppressEvent e) {
		if(e.getPlayer().equals(player)) {
			highlightButton.updateSide();
		}
	}

	/**
	 * updates the context list, preserves the selection
	 */
	private void buildContextList() {
		
		contextList.clear();
		contextList.addItem(new ListWidgetItem(NO_CONTEXT, ""));
		contextList.forceSelection(0);
		
		for(TriggerContext context : facade.getAllTriggerContexts()) {
			
			String subtext = "";
			
			if(!context.isComplete()) {
				subtext = "Context not complete - cannot save!";
			}
			
			contextList.add(new ListWidgetItem(context.getLabel(), subtext), context);
			
			
			System.out.println(context.getLabel());
			System.out.println(facade.getTriggerContext(player) == null ? "null" : facade.getTriggerContext(player).getLabel());
			
			if (context == facade.getTriggerContext(player)) {
				System.out.println("--highlight");
				
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
		contextButton.setWidth(200)
			 .setHeight(20)
			 .shiftXPos(- (contextButton.getWidth() + MARGIN_X))
			 .shiftYPos(contextList.getHeight() + left1.getHeight()+ 3 * MARGIN_Y);
		
		contextButton.setAnchor(WidgetAnchor.TOP_CENTER);
		contextButton.updateSide();
		attachWidget(Hello.getPlugin(), contextButton);
		
		// left 3
		highlightButton.setWidth(200)
			 .setHeight(20)
			 .shiftXPos(- (highlightButton.getWidth() + MARGIN_X))
			 .shiftYPos(contextList.getHeight() + left1.getHeight() + contextButton.getHeight() + 4 * MARGIN_Y);
		
		highlightButton.setAnchor(WidgetAnchor.TOP_CENTER);
		highlightButton.updateSide();
		attachWidget(Hello.getPlugin(), highlightButton);
		
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
