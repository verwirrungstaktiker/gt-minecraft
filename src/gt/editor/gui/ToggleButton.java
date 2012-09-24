/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor.gui;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public abstract class ToggleButton extends GenericButton {

	enum Side{A, B};
	
	private Side side;
	
	/**
	 * updateds the text of the button
	 */
	public void updateSide() {
		side = determineSide();
		
		switch (side) {
		case A:
			setText(getASideText());
			break;
		case B:
			setText(getBSideText());
			break;
		default:
			break;
		}
		
		setDirty(true);
	}

	@Override
	public void onButtonClick(final ButtonClickEvent event) {		
		switch (side) {
		case A:
			onASideClick(event);
			break;
		case B:
			onBSideClick(event);
			break;
		default:
			break;
		}
		
		
	};	
	
	/**
	 * @return text of side A
	 */
	protected abstract String getASideText();
	
	/**
	 * @return text of side B
	 */
	protected abstract String getBSideText();	
	
	/**
	 * @param event called when the A side is active and the button is clicked
	 */
	protected abstract void onASideClick(ButtonClickEvent event);
	
	/**
	 * @param event called when the B side is active and the button is clicked
	 */
	protected abstract void onBSideClick(ButtonClickEvent event);
	
	/**
	 * @return the currently active side
	 */
	protected abstract Side determineSide();
}
