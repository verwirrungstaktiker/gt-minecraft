package gt.editor.gui;

import gt.editor.EditorFacade;
import gt.editor.EditorPlayer.TriggerState;

import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class BlocksPage extends OverlayPage {

	/**
	 * @param overlay the visual Overlay Box
	 * @param player the corresponding SpoutPlayer
	 * @param facade the corresponding EditorFacade
	 */
	protected BlocksPage(final EditorOverlay overlay, final SpoutPlayer player,
			final EditorFacade facade) {
		super(overlay, player, facade);
	}

	@Override
	protected void setup() {
		Map<TriggerState, ItemStack[]> inventories = facade.getInventories(player);	
		
		GenericContainer list = new GenericContainer();
		list.setLayout(ContainerType.VERTICAL);
		
		for(final TriggerState triggerState : inventories.keySet()) {			
			list.addChild(new BlockSet(triggerState.toString(), inventories.get(triggerState)){
				@Override
				public void onSelect() {
					facade.setTriggerState(player, triggerState);
				}
			});
		}
		
		GenericButton toTriggerPageButton = new GenericButton("Trigger Overview ...") {
			@Override
			public void onButtonClick(final ButtonClickEvent event) {
				super.onButtonClick(event);
				overlay.switchToPage(overlay.getMainPage());
			}
		};
		
		toTriggerPageButton.setWidth(9 * 20);
		toTriggerPageButton.setHeight(20);
		toTriggerPageButton.setMarginTop(10);
		toTriggerPageButton.setFixed(true);
		
		list.addChild(toTriggerPageButton);
		
		
		list.setAnchor(WidgetAnchor.TOP_CENTER);
		list.shiftXPos(- 9 * 20 / 2 );
		list.shiftYPos(5);

		attachWidget(list);
		
	}

	@Override
	protected void dispose() {
	}

	@Override
	public boolean closeWithHotkey() {
		return true;
	}

}
