package gt.general;

import org.bukkit.inventory.ItemStack;

public class Item {
	protected String name;
	protected ItemStack mcItem;  
	protected boolean dropable;
	protected boolean tool; 
	
	public Item(ItemStack itemstack) {
		mcItem = itemstack;
		//ToDo: block type dependend values 
		dropable = true;
		tool = false;
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
