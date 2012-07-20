package gt.editor.gui;

import static com.google.common.collect.Maps.*;

import java.util.Map;

import org.getspout.spoutapi.gui.GenericListWidget;
import org.getspout.spoutapi.gui.ListWidgetItem;

/**
 * inner class to keep track of selected contexts
 */
class SelectionListWidget <T> extends GenericListWidget {
	private Map<ListWidgetItem, T> map = newHashMap();
	
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
	
	@Override
	public void clear() {
		super.clear();
		map.clear();
	}
}