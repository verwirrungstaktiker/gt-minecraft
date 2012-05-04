package gt.general;

public class Inventory {

	protected Item activeItem;

	
	public Item getActiveItem() {
		return activeItem;
	}

	public void setActiveItem(Item activeItem) {
		this.activeItem = activeItem;
	}
	
	
	public boolean activeItemDropable() {
		return activeItem.isDropable();
	}
	

}
