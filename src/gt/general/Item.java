package gt.general;

public class Item {
	protected String name;

	private boolean dropable;
	private boolean tool; 
	
	
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
