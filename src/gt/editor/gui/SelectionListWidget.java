/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor.gui;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.ListWidgetItem;

/**
 * inner class to keep track of selected contexts
 */
abstract class SelectionListWidget <T> extends GenericListWidget {
	private Map<ListWidgetItem, T> map = newHashMap();
	private int oldSelectedIndex = 0;
	
	
	/**
	 * @param item the item to be displayed in the list
	 * @param object the object which is represented by the item
	 */
	public void add(final ListWidgetItem item, final T object) {
		super.addItem(item);
		map.put(item, object);
	}
	
	/**
	 * @return the currently selected trigger context
	 */
	public T getSelectedObject() {
		return map.get(getSelectedItem());
	}
	
	/**
	 * @param item the list entry
	 * @return the represented object
	 */
	public T getObject(final ListWidgetItem item) {
		return map.get(item);
	}
	
	@Override
	public void clear() {
		super.clear();
		map.clear();
	}
	
	
	/**
	 * resets overrides the context constraint
	 * @param n the new selection
	 */
	public void forceSelection(final int n) {
		oldSelectedIndex = n;
		super.setSelection(n);
	}
	
	/**
	 * @return the old selctedIndex
	 */
	public int getOldSelectedIndex() {
		return oldSelectedIndex;
	}
	
	@Override
	public void onSelected(final int selectedIndex, final boolean doubleClick) {			
		// current is reselected catches forced selections
		// - cant really distinguish between updates from the code and updates form the user
		if (selectedIndex == oldSelectedIndex) {
			return;
		}
		
		T oldSelected = getObject(getItem(oldSelectedIndex));

		// only allow to change if empty element, context is complete
		if(canLeaveOldSelected(oldSelected)) {
			super.onSelected(selectedIndex, doubleClick);

			oldSelectedIndex = selectedIndex;
			onAllowedSelectionChange();
		} else {
			setSelection(oldSelectedIndex);
		}
	}

	/**
	 * @param oldSelected the old selected object
	 * @return true if the selection can be changed 
	 */
	protected abstract boolean canLeaveOldSelected(T oldSelected);
	
	/**
	 * called when it is allowed to change the selection
	 */
	protected abstract void onAllowedSelectionChange();
	
}
