package gt.general;

public class Inventory {

	private Item activeItem;
	private Item passivItem;
	
	/**
	 * @return the current Item in the active slot
	 */
	public Item getActiveItem() {
		return activeItem;
	}
	
	/**
	 * @return the current Item in the passive slot
	 */
	public Item getPassivItem() {
		return passivItem;
	}

	/**
	 * picks up a new item
	 * 
	 * @param newItem the new item
	 * @return TODO explain why this returns boolean
	 */
	public boolean setActiveItem(final Item newItem) {
		//always allow to override with null
		
		// TODO clarify this
		if (activeItem != null && newItem != null){
			if ( activeItem.isTool())  {
				// TODO redo swapping properly
				passivItem = activeItem;
			} else {
				return false;
			}
		}
		this.activeItem = newItem;
		return true;
	}
	
	/**
	 * Method to drop the active Item
	 * TODO real dropping
	 */
	public void dropActiveItem() {
		if (activeItemDropable()) {
			activeItem = null;
		}
		// TODO debug in else branch
	}
	
	/**
	 * @return true if the currently active item is droppable
	 */
	public boolean activeItemDropable() {
		return activeItem.isDropable();
	}

}
