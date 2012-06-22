package gt.general.trigger.response;


public abstract class AbstractResponse implements Response {

	private String label;
	
	public AbstractResponse() {
		setLabel(this.getClass().getName() + "_"+ hashCode());
	}
	
	public AbstractResponse(final String labelPrefix) {
		setLabel(labelPrefix + "_" + hashCode());
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}
}
