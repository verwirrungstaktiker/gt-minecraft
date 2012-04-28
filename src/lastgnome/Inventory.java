package lastgnome;

public class Inventory {

	protected Item activeItem;

	
	public Item getActiveItem() {
		return activeItem;
	}


	public void setActiveItem(Item activeItem) {
		this.activeItem = activeItem;
	}
	
	public boolean dropActiveItem() {
		return activeItem.dropable;
	}
	

}
