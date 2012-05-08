package gt.general;

public class Inventory {

	protected Item activeItem;
	protected Item passivItem;

	
	public Item getActiveItem() {
		return activeItem;
	}
	
	public Item getPassivItem() {
		return passivItem;
	}

	public boolean setActiveItem(Item activeItem) {
		//always allow to override with null
		if (this.activeItem != null && activeItem != null){
			if ( this.activeItem.isTool())  {
				this.passivItem = this.activeItem;
			} else return false;
		}
		this.activeItem = activeItem;
		return true;
	}
	
	/**
	 * Method to drop the active Item
	 * @return true if Item can be dropped, otherwise false
	 */
	public boolean dropActiveItem() {
		//TODO: Dropping the Item
		return activeItemDropable();
	}
	
	
	public boolean activeItemDropable() {
		//TODO: Real dropping
		if (activeItem.isDropable()){
			activeItem = null;
			return true;
		}
		return false;
			
	}
	

}
