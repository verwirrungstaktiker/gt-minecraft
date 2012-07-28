package gt.editor.gui;

import gt.editor.EditorFacade;
import gt.editor.event.HighlightSuppressEvent;
import gt.editor.event.LogicChangeEvent;
import gt.editor.event.LogicSelectionEvent;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistance.YamlSerializable;
import gt.plugin.meta.MultiListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.ListWidgetItem;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class MainPage extends OverlayPage implements Listener {

	private static final int MARGIN_X = 5;
	private static final int MARGIN_Y = 10;

	private static final String NO_CONTEXT = "No Context";
	private static final String NO_ITEM = "No Item";

	/**
	 * special selection behaviour: only the currently active context may be
	 * selected
	 */
	private SelectionListWidget<TriggerContext> contextList = new SelectionListWidget<TriggerContext>() {
		@Override
		protected boolean canLeaveOldSelected(final TriggerContext oldSelected) {
			return oldSelected == null || oldSelected.isComplete();
		}

		@Override
		protected void onAllowedSelectionChange() {
			if (getSelectedObject() != null) {
				facade.enterContext(player, getSelectedObject());
			} else {
				facade.exitContext(player);
			}
		}
	};

	private SelectionListWidget<YamlSerializable> itemList = new SelectionListWidget<YamlSerializable>() {

		@Override
		protected boolean canLeaveOldSelected(YamlSerializable oldSelected) {
			return true;
		}

		@Override
		protected void onAllowedSelectionChange() {
			facade.setSelectedItem(player, getSelectedObject());
		}
		
	};

	private GenericButton renameContextButton = new GenericButton("Rename selected context") {
		public void onButtonClick(final ButtonClickEvent event) {
			final TriggerContext selectedItem = contextList.getSelectedObject();

			if (selectedItem != null) {

				final PromptCallback cb = new PromptCallback() {
					@Override
					public void onClose(final Action action, final String text) {

						if (action == Action.SUBMIT) {
							selectedItem.setLabel(text);
						}
						overlay.switchToPage(overlay.getMainPage());
					}
				};
				overlay.switchToPage(overlay.getPromptPage("rename: "
						+ selectedItem.getLabel(), cb));
			}
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
	
	private GenericButton loadButton = new GenericButton("Load") {
		@Override
		public void onButtonClick(final ButtonClickEvent event) {
			event.getPlayer().performCommand("load");
		};
	};
	
	private GenericButton saveButton = new GenericButton("Save") {
		@Override
		public void onButtonClick(final ButtonClickEvent event) {
			event.getPlayer().performCommand("save");
		};
	};
	
	private GenericButton renameItemButton = new GenericButton(
			"Rename selected item") {
		@Override
		public void onButtonClick(final ButtonClickEvent event) {
			final YamlSerializable selectedItem = itemList.getSelectedObject();

			if (selectedItem != null) {

				final PromptCallback cb = new PromptCallback() {
					@Override
					public void onClose(final Action action, final String text) {

						if (action == Action.SUBMIT) {
							selectedItem.setLabel(text);
						}
						overlay.switchToPage(overlay.getMainPage());
					}
				};
				overlay.switchToPage(overlay.getPromptPage("rename: "
						+ selectedItem.getLabel(), cb));
			}
		};
	};
	
	private ToggleButton highlightButton = new ToggleButton() {
		@Override
		protected String getASideText() {
			return "Highlight Selected Context [on]";
		}
	
		@Override
		protected String getBSideText() {
			return "Highlight Selected Context [off]";
		}
	
		@Override
		protected void onASideClick(final ButtonClickEvent event) {
			facade.setSuppressHighlight(player, true);
		}
	
		@Override
		protected void onBSideClick(final ButtonClickEvent event) {
			facade.setSuppressHighlight(player, false);
		}
	
		@Override
		protected Side determineSide() {
			return facade.isSuppressHighlight(player) ? Side.B : Side.A;
		}
	};
	
	private GenericButton right3 = new GenericButton("Select blocks ...") {
		public void onButtonClick(final ButtonClickEvent event) {
			System.out.println("right 3");
		};
	};

	MainPage(final EditorOverlay overlay, final SpoutPlayer player,
			final EditorFacade facade) {
		super(overlay, player, facade);
	}

	@Override
	protected void setup() {
		setupGui();
		buildContextList();

		MultiListener.registerListener(this);
	}

	@Override
	protected void dispose() {
		MultiListener.unregisterListener(this);
	}

	@EventHandler
	public void onLogicChange(final LogicChangeEvent e) {
		System.out.println("logic changed");
		contextButton.updateSide();
		buildContextList();
	}

	@EventHandler
	public void onHighlightSuppress(final HighlightSuppressEvent e) {		
		if (e.getPlayer().equals(player)) {
			System.out.println("highlight suppress state changed");
			
			highlightButton.updateSide();
		}
	}

	@EventHandler
	public void onContextSwitch(final LogicSelectionEvent e) {
		if (e.getPlayer().equals(player)) {
			System.out.println("switched context");
			contextButton.updateSide();
			buildContextList();
		}
	}

	/**
	 * updates the context list, preserves the selection
	 */
	private void buildContextList() {

		contextList.clear();
		contextList.addItem(new ListWidgetItem(NO_CONTEXT, ""));
		contextList.forceSelection(0);

		for (TriggerContext context : facade.getAllTriggerContexts()) {

			String subtext = "";

			if (!context.isComplete()) {
				subtext = "Context not complete - cannot save!";
			}

			contextList.add(new ListWidgetItem(context.getLabel(), subtext),
					context);

			if (context == facade.getActiveContext(player)) {
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

		TriggerContext selected = facade.getActiveContext(player);
		YamlSerializable selectedItem = facade.getSelectedItem(player);

		itemList.clear();
		itemList.addItem(new ListWidgetItem(NO_ITEM, ""));
		itemList.clearSelection();

		if (selected != null) {

			for (YamlSerializable item : selected.getAllItems()) {
				itemList.add(new ListWidgetItem(item.getLabel(), ""), item);

				if (item == selectedItem) {
					int position = itemList.getItems().length - 1;
					itemList.setSelection(position);
				}
			}
		}
	}

	private void setupGui() {
		// setup context list
		contextList.setWidth(200).setHeight(200)
				.shiftXPos(-(contextList.getWidth() + MARGIN_X))
				.shiftYPos(MARGIN_Y);

		contextList.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(contextList);

		// setup item list
		itemList.setWidth(200).setHeight(200).shiftXPos(MARGIN_X)
				.shiftYPos(MARGIN_Y);

		itemList.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(itemList);

		// left 1
		renameContextButton.setWidth(200).setHeight(20)
				.shiftXPos(-(renameContextButton.getWidth() + MARGIN_X))
				.shiftYPos(contextList.getHeight() + 2 * MARGIN_Y);

		renameContextButton.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(renameContextButton);

		// left 2
		contextButton
				.setWidth(200)
				.setHeight(20)
				.shiftXPos(-(contextButton.getWidth() + MARGIN_X))
				.shiftYPos(
						contextList.getHeight() + renameContextButton.getHeight() + 3
								* MARGIN_Y);

		contextButton.setAnchor(WidgetAnchor.TOP_CENTER);
		contextButton.updateSide();
		attachWidget(contextButton);

		// left 3a
		loadButton
				.setWidth(100 - MARGIN_X)
				.setHeight(20)
				.shiftXPos(-(renameContextButton.getWidth() + MARGIN_X ))
				.shiftYPos(
						contextList.getHeight() + renameContextButton.getHeight()
								+ contextButton.getHeight() + 4 * MARGIN_Y);

		loadButton.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(loadButton);
		
		
		// left 3b
		saveButton
				.setWidth(100 - MARGIN_X)
				.setHeight(20)
				.shiftXPos(-(saveButton.getWidth() + MARGIN_X))
				.shiftYPos(
						contextList.getHeight() + renameContextButton.getHeight()
								+ contextButton.getHeight() + 4 * MARGIN_Y);

		saveButton.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(saveButton);

		// right 1
		renameItemButton.setWidth(200).setHeight(20).shiftXPos(MARGIN_X)
				.shiftYPos(itemList.getHeight() + 2 * MARGIN_Y);

		renameItemButton.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(renameItemButton);

		// right 2
		highlightButton.setWidth(200)
				.setHeight(20)
				.shiftXPos(MARGIN_X)
				.shiftYPos(
						itemList.getHeight() + renameItemButton.getHeight() + 3
								* MARGIN_Y);

		highlightButton.setAnchor(WidgetAnchor.TOP_CENTER);
		highlightButton.updateSide();
		attachWidget(highlightButton);

		// right 3
		right3.setWidth(200)
				.setHeight(20)
				.shiftXPos(MARGIN_X)
				.shiftYPos(
						itemList.getHeight() + renameItemButton.getHeight()
								+ highlightButton.getHeight() + 4 * MARGIN_Y);

		right3.setAnchor(WidgetAnchor.TOP_CENTER);
		attachWidget(right3);
	}

	@Override
	public boolean closeWithHotkey() {
		return true;
	}
}
