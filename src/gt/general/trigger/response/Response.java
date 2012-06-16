package gt.general.trigger.response;

import gt.general.trigger.persistance.YamlSerializable;

public interface Response extends YamlSerializable {

	/**
	 * will be called, when the triggerFunction in the TriggerContexts changes its state
	 * 
	 * @param active iff true, the function is now true
	 */
	void triggered(boolean active);
	
}