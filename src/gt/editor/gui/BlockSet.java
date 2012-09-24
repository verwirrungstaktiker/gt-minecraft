/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor.gui;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericItemWidget;
import org.getspout.spoutapi.gui.GenericWidget;

public abstract  class BlockSet extends GenericContainer {

	/**
	 * @param name name of BlockSet
	 * @param inventory items that correspond to a BlockSet
	 */
	public BlockSet(final String name, final ItemStack[] inventory) {		
		setLayout(ContainerType.VERTICAL);
		
		
		GenericContainer blocks = new GenericContainer();
		blocks.setLayout(ContainerType.HORIZONTAL);
		
		for(int i = 0; i < 9; i++) {
			
			//GenericItemWidget icon;
			
			GenericWidget widget;
			
			if(i >= inventory.length || inventory[i] == null) {
				GenericGradient grad = new GenericGradient();
				grad.setTopColor(new Color(192, 192, 192, 32));
				grad.setBottomColor(new Color(128, 128, 128, 64));
				widget = grad;
				
			} else {			
				GenericItemWidget item = new GenericItemWidget(inventory[i]);
				item.setDepth(16);
				widget = item;
			}
						
			widget.setWidth(16);
			widget.setHeight(16);
			widget.setFixed(true);
			widget.setMargin(2);
			
			widget.setMaxHeight(16);
			widget.setMaxWidth(16);
			
			blocks.addChild(widget);
		}
		
		blocks.setHeight(20);
		blocks.setFixed(true);
		
		addChild(blocks);
		
		GenericButton button = new GenericButton("Take: " + name) {
			@Override
			public void onButtonClick(final ButtonClickEvent event) {
				super.onButtonClick(event);
				onSelect();
			}
		};
		
		button.setWidth(9*20);
		button.setHeight(20);
		button.setFixed(true);
		
		addChild(button);
		
		setMarginBottom(10);
		
	}
	
	/**
	 * is executed on selection of this BlockSet
	 */
	public abstract void onSelect();
}
