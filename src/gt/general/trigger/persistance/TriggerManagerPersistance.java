package gt.general.trigger.persistance;

import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Maps.*;
import gt.general.trigger.TriggerContext;
import gt.general.trigger.TriggerManager;
import gt.general.trigger.response.Response;
import gt.general.trigger.trigger.Trigger;

import java.util.List;
import java.util.Map;

import org.bukkit.World;

public class TriggerManagerPersistance {
	
	private final TriggerManager triggerManager;
	
	public static final String KEY_INPUT_FUNCTION = "input-function"; 
	public static final String KEY_TRIGGERS = "triggers"; 
	public static final String KEY_RESPONSES = "responses";

	
	public static final String KEY_GLOBAL_TRIGGERS = "global-triggers";
	public static final String KEY_GLOBAL_RESPONSES = "global-responses";
	public static final String KEY_GLOBAL_CONTEXTS = "global-contexts";
	
	public static final String KEY_CLASS = "class";
	
	private Map<String, Object> globalTriggers;
	private Map<String, Object> globalResponses;
	private Map<String, Object> globalContexts;
	private World world;
	
	/**
	 * @param triggerManager the TriggerManager to be persisted
	 */
	TriggerManagerPersistance(final TriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}

	public TriggerManagerPersistance(TriggerManager triggerManager, World world) {
		this.triggerManager = triggerManager;
		this.world = world;
	}

	public static void setupTriggerManager(final TriggerManager triggerManager, final PersistanceMap values, final World world) {
		new TriggerManagerPersistance(triggerManager, world).setup(values);
	}
	
	public static Map<String, Object> dumpTriggerManager(final TriggerManager triggerManager) {
		return new TriggerManagerPersistance(triggerManager).dump();
	}
	
	@SuppressWarnings("unchecked")
	public void setup(final PersistanceMap values) {
		try {
			globalContexts = values.get(KEY_GLOBAL_CONTEXTS);
			globalResponses = values.get(KEY_GLOBAL_RESPONSES);
			globalTriggers = values.get(KEY_GLOBAL_TRIGGERS);
			
			for(String contextLabel : globalContexts.keySet()) {
				loadTriggerContext(contextLabel);
				
			}
		} catch (ClassCastException e) {
			throw new RuntimeException("bad file format", e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void loadTriggerContext(final String contextLabel)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		
		Map<String, ? extends Object> contextMap = (Map<String, Object>) globalContexts.get(contextLabel);
		
		TriggerContext tc = new TriggerContext();
		tc.setInputFunction(TriggerContext.InputFunction.valueOf((String) contextMap.get(KEY_INPUT_FUNCTION)));
		tc.setLabel(contextLabel);
		
		
			for(String triggerLabel : (List<String>) contextMap.get(KEY_TRIGGERS)) {
				
				Map<String, Object> triggerMap = (Map<String, Object>) globalTriggers.get(triggerLabel);
				Trigger t = loadSerializable(triggerLabel, triggerMap);
				t.setContext(tc);
				
				tc.addTrigger(t);
			}
			
			for(String responseLabel : (List<String>) contextMap.get(KEY_RESPONSES)) {
				Map<String, Object> responseMap = (Map<String, Object>) globalResponses.get(responseLabel);

				tc.addResponse((Response) loadSerializable(responseLabel, responseMap));
			}
			

		
		triggerManager.addTriggerContext(tc);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends YamlSerializable> T loadSerializable(final String label, final Map<String, Object> map) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		String className = (String) map.remove(KEY_CLASS);
		
		T serializable = (T) Class.forName(className).newInstance();
		serializable.setLabel(label);
		serializable.setup(new PersistanceMap(map), world);
		
		return serializable;
	}
	
	/**
	 * @return yamlable representation of the contained TriggerManager
	 */
	public Map<String, Object> dump() {
		
		globalTriggers = newHashMap(); 
		globalResponses = newHashMap();
		globalContexts = newHashMap();
		
		for(TriggerContext triggerContext : triggerManager.getTriggerContexts()) {
			serializeTriggerContext(triggerContext);
		}
		
		return finalizeMap();
	}


	private void serializeTriggerContext(final TriggerContext triggerContext) {
		List<String> itsTriggers = newArrayList();
		for(Trigger t : triggerContext.getTriggers()) {
			itsTriggers.add(t.getLabel());
			
			globalTriggers.put(t.getLabel(), prepareDump(t));				
		}
		
		List<String> itsResponses = newArrayList();
		for(Response r : triggerContext.getResponses()) {
			itsResponses.add(r.getLabel());

			globalResponses.put(r.getLabel(), prepareDump(r));	
		}			
		
		Map<String, Object> c = newHashMap();
		c.put(KEY_INPUT_FUNCTION, triggerContext.getInputFunction().toString());
		c.put(KEY_TRIGGERS, itsTriggers);
		c.put(KEY_RESPONSES, itsResponses);
		
		globalContexts.put(triggerContext.getLabel(), c);
	}


	private Map<String, Object> prepareDump(final YamlSerializable serializable) {
		
		Map<String, Object> ret = serializable.dump().getMap();
		ret.put(KEY_CLASS, serializable.getClass().getName());
		
		return ret;
	}

	private Map<String, Object> finalizeMap() {
		Map<String, Object> global = newHashMap();
		global.put(KEY_GLOBAL_TRIGGERS, globalTriggers);
		global.put(KEY_GLOBAL_RESPONSES, globalResponses);
		global.put(KEY_GLOBAL_CONTEXTS, globalContexts);
		
		return global;
	}
}
