package gt.general;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.material.item.GenericCustomItem;

public class PortableItem extends GenericCustomItem{

	private boolean dropable;
	
	/** tools can be put in the secondary slot */
	private boolean tool;
	
	/** if true the item can be transferred directly into another inventory */
	private boolean transferable;

	public PortableItem(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
		
		//ToDo: block type dependend values 
		dropable = true;
		tool = false;
	}

	public boolean isDropable() {
		return dropable;
	}

	public void setDropable(final boolean dropable) {
		this.dropable = dropable;
	}
	
	public boolean isTool() {
		return tool;
	}

	//primary for tests
	public void setTool(final boolean tool) {
		this.tool = tool;
	}

	/**
	 * @return the transferable
	 */
	public boolean isTransferable() {
		return transferable;
	}

	/**
	 * @param transferable the transferable to set
	 */
	public void setTransferable(final boolean transferable) {
		this.transferable = transferable;
	}
	
}
