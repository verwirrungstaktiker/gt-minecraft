package gt.general.trigger.response;

import gt.general.trigger.persistance.YamlSerializable;

public abstract class Response extends YamlSerializable {

	
	public Response() {
		super();
	}
	
	public Response(final String labelPrefix) {
		super(labelPrefix);
	}
	
	/**
	 * will be called, when the triggerFunction in the TriggerContexts changes its state
	 * 
	 * @param active iff true, the function is now true
	 */
	public abstract void triggered(boolean active);

	/**
	 * highlight the blocks that are connected to this response with particles
	 */
	public abstract void highlight();
}
