package gt.general.trigger.persistance;

import gt.general.trigger.Trigger;
import gt.general.trigger.TriggerContext;
import gt.general.trigger.TriggerManager;
import gt.general.trigger.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class TriggerManagerPersistance {
	
	private final TriggerManager triggerManager;
	
	public TriggerManagerPersistance(final TriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}
	
	
	public TriggerManager getTriggerManager() {
		return triggerManager;
	}
	
	private final static String KEY_INPUT_FUNCTION = "input-function"; 
	private final static String KEY_TRIGGERS = "triggers"; 
	private final static String KEY_RESPONSES = "responses";

	
	private final static String KEY_GLOBAL_TRIGGERS = "global-responses";
	private final static String KEY_GLOBAL_RESPONSES = "global-responses";
	private final static String KEY_GLOBAL_CONTEXTS = "global-contexts";
	
	public synchronized String persistTriggerManager() {
		
		Map<String, Object> globalTriggers = new HashMap<String, Object>();
		Map<String, Object> globalResponses = new HashMap<String, Object>();
		Map<String, Object> globalContexts = new HashMap<String, Object>();

		for(TriggerContext triggerContext : triggerManager.getTriggerContexts()) {
			
			List<String> itsTriggers = new ArrayList<String>();
			for(Trigger t : triggerContext.getTriggers()) {
				persist(t, globalTriggers, itsTriggers);				
			}
			
			List<String> itsResponses = new ArrayList<String>();
			for(Response r : triggerContext.getResponses()) {
				persist(r, globalResponses, itsResponses);				
			}			
			
			Map<String, Object> c = new HashMap<String, Object>();
			c.put(KEY_INPUT_FUNCTION, triggerContext.getInputFunction()); // .toString() maybe ?
			c.put(KEY_TRIGGERS, itsTriggers);
			c.put(KEY_RESPONSES, itsResponses);
			
			globalContexts.put(triggerContext.getLabel(), c);
		}
		
		Map<String, Object> global = new HashMap<String, Object>();
		global.put(KEY_GLOBAL_TRIGGERS, globalTriggers);
		global.put(KEY_GLOBAL_RESPONSES, globalResponses);
		global.put(KEY_GLOBAL_CONTEXTS, globalContexts);
		
		Yaml yml = new Yaml();
		return yml.dump(global);
	}


	private void persist(YamlSerializable serializable,
			Map<String, Object> map, List<String> list) {
		list.add(serializable.getLabel());
		map.put(serializable.getLabel(), serializable.teardown());
	}

	public static String toYaml(final TriggerManager triggerManager) {
		return new TriggerManagerPersistance(triggerManager).persistTriggerManager();
	}
	
	public static TriggerManager load(String yaml) {
		
		return null;
	}
	
}
