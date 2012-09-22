package gt.general.logic.response;

import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.YamlSerializable;

public abstract class Response extends YamlSerializable {
	
	public static final int EFFECT_RANGE = 75;
	
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
	 * @param triggerEvent special event when a trigger is activated
	 */
	public abstract void triggered(final TriggerEvent triggerEvent);
}
