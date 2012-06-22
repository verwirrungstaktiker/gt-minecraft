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
	
	private final Yaml yaml = new Yaml();
	
	public TriggerManagerPersistance(final TriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}
	
	
	public TriggerManager getTriggerManager() {
		return triggerManager;
	}
	
	public final static String KEY_INPUT_FUNCTION = "input-function"; 
	public final static String KEY_TRIGGERS = "triggers"; 
	public final static String KEY_RESPONSES = "responses";

	
	public final static String KEY_GLOBAL_TRIGGERS = "global-triggers";
	public final static String KEY_GLOBAL_RESPONSES = "global-responses";
	public final static String KEY_GLOBAL_CONTEXTS = "global-contexts";
	
	public final static String KEY_CLASS = "class";
	
	public String toYaml() {
		return yaml.dump(asYaml());
	}

	public synchronized Map<String, Object> asYaml() {
		Map<String, Object> globalTriggers = new HashMap<String, Object>();
		Map<String, Object> globalResponses = new HashMap<String, Object>();
		Map<String, Object> globalContexts = new HashMap<String, Object>();

		for(TriggerContext triggerContext : triggerManager.getTriggerContexts()) {
			
			List<String> itsTriggers = new ArrayList<String>();
			for(Trigger t : triggerContext.getTriggers()) {
				itsTriggers.add(t.getLabel());
				
				globalTriggers.put(t.getLabel(), prepareDump(t));				
			}
			
			List<String> itsResponses = new ArrayList<String>();
			for(Response r : triggerContext.getResponses()) {
				itsResponses.add(r.getLabel());

				globalResponses.put(r.getLabel(), prepareDump(r));	
			}			
			
			Map<String, Object> c = new HashMap<String, Object>();
			c.put(KEY_INPUT_FUNCTION, triggerContext.getInputFunction().toString()); // .toString() maybe ?
			c.put(KEY_TRIGGERS, itsTriggers);
			c.put(KEY_RESPONSES, itsResponses);
			
			globalContexts.put(triggerContext.getLabel(), c);
		}
		
		Map<String, Object> global = new HashMap<String, Object>();
		global.put(KEY_GLOBAL_TRIGGERS, globalTriggers);
		global.put(KEY_GLOBAL_RESPONSES, globalResponses);
		global.put(KEY_GLOBAL_CONTEXTS, globalContexts);
		
		global.put("foo", "bar");
		
		return global;
	}


	private Map<String, Object> prepareDump(final YamlSerializable serializable) {
		
		Map<String, Object> ret = serializable.dump();
		ret.put(KEY_CLASS, serializable.getClass().getName());
		
		return ret;
	}

	public static String toYaml(final TriggerManager triggerManager) {
		return new TriggerManagerPersistance(triggerManager).toYaml();
	}
	
	@SuppressWarnings("unchecked")
	public List<TriggerContext> fromYaml(String s) {
		Map<String, ? extends Object> global = (Map<String, ? extends Object>) yaml.load(s);
		
		
		
		
		return null;
	}
	
	public static TriggerManager load(String yaml) {
		
		return null;
	}
	
}
