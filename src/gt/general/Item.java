package gt.general;

import org.bukkit.inventory.ItemStack;

public class Item {
	protected String name;
	protected ItemStack mcItem;  
	protected boolean dropable;
	protected boolean tool;
	protected boolean placable;
	
	public boolean isPlacable() {
		return placable;
	}

	public void setPlacable(boolean placable) {
		this.placable = placable;
	}

	public Item(ItemStack itemstack) {
		mcItem = itemstack;
		//ToDo: block type dependend values 
		dropable = true;
		tool = false;
		placable = true;
	}
	
	public String getName() {
		return name;
	}
	

	public boolean isDropable() {
		return dropable;
	}
	//primary for tests
	public boolean isTool() {
		return tool;
	}

	//primary for tests
	public void setTool(boolean tool) {
		this.tool = tool;
	}


	public void setDropable(boolean dropable) {
		this.dropable = dropable;
	}
	
	
}
