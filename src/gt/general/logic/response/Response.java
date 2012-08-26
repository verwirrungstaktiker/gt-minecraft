package gt.general.logic.response;

import gt.general.RespawnManager;
import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.YamlSerializable;

public abstract class Response extends YamlSerializable {
	
	/**
	 * don't delete this anonymous constructor
	 */
	public Response() {
		super();
	}
	
	/**
	 * @param labelPrefix prefix of the label
	 */
	public Response(final String labelPrefix) {
		super(labelPrefix);
	}
	
	/**
	 * will be called, when the triggerFunction in the TriggerContexts changes its state
	 * 
	 * @param active iff true, the function is now true
	 */
	public abstract void triggered(TriggerEvent triggerEvent);
}
