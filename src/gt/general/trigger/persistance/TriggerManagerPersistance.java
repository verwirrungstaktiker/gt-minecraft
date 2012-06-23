package gt.general.trigger.persistance;

import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Maps.*;
import gt.general.trigger.InputFunction;
import gt.general.trigger.Trigger;
import gt.general.trigger.TriggerContext;
import gt.general.trigger.TriggerManager;
import gt.general.trigger.response.Response;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

public class TriggerManagerPersistance {
	
	private final TriggerManager triggerManager;
	private final World world;
	
	private final Yaml yaml;
	
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
	
	/**
	 * @param triggerManager the TriggerManager to be persisted
	 * @param world the to which the TriggerManager is associated
	 */
	public TriggerManagerPersistance(final TriggerManager triggerManager, final World world) {
		this.triggerManager = triggerManager;
		this.world = world;
		

		DumperOptions opts = new DumperOptions();
		opts.setDefaultFlowStyle(FlowStyle.BLOCK);
		opts.setPrettyFlow(true);
		
		yaml = new Yaml(opts);
	}

	/**
	 * @param writer where the TriggerManager should be dumped
	 */
	public void serializeTo(final Writer writer) {
		yaml.dump(asYaml(), writer);
	}

	/*
	 * TODO convert classcast into something checked
	 */
	public void deserializeFrom(final Reader reader) {
		try {
			loadTriggerFile(reader);
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
	private void loadTriggerFile(final Reader reader) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Map<String, ? extends Object> global = (Map<String, ? extends Object>) yaml.load(reader);
		
		// if empty then empty
		if(global == null) {
			return;
		}
		
		globalContexts = (Map<String, Object>) global.get(KEY_GLOBAL_CONTEXTS);
		globalResponses = (Map<String, Object>) global.get(KEY_GLOBAL_RESPONSES);
		globalTriggers = (Map<String, Object>) global.get(KEY_GLOBAL_TRIGGERS);
		
		for(String contextLabel : globalContexts.keySet()) {
			loadTriggerContext(contextLabel);
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadTriggerContext(final String contextLabel)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		
		Map<String, ? extends Object> contextMap = (Map<String, Object>) globalContexts.get(contextLabel);
		
		TriggerContext tc = new TriggerContext();
		tc.setInputFunction(InputFunction.valueOf((String) contextMap.get(KEY_INPUT_FUNCTION)));
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
	private <T extends YamlSerializable> T loadSerializable(String label, Map<String, Object> map) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		String className = (String) map.remove(KEY_CLASS);
		
		T serializable = (T) Class.forName(className).newInstance();
		serializable.setLabel(label);
		serializable.setup(map, world);
		
		return serializable;
	}

	/**
	 * @return yaml string of contained TriggerManager
	 */
	public String toYaml() {
		
		return yaml.dump(asYaml());
	}
	
	/**
	 * @return yamlable representation of the contained TriggerManager
	 */
	public Map<String, Object> asYaml() {
		
		globalTriggers = newHashMap(); 
		globalResponses = newHashMap();
		globalContexts = newHashMap();
		
		for(TriggerContext triggerContext : triggerManager.getTriggerContexts()) {
			serializeTriggerContext(triggerContext);
		}
		
		return finalizeYaml();
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
		
		Map<String, Object> ret = serializable.dump();
		ret.put(KEY_CLASS, serializable.getClass().getName());
		
		return ret;
	}

	private Map<String, Object> finalizeYaml() {
		Map<String, Object> global = newHashMap();
		global.put(KEY_GLOBAL_TRIGGERS, globalTriggers);
		global.put(KEY_GLOBAL_RESPONSES, globalResponses);
		global.put(KEY_GLOBAL_CONTEXTS, globalContexts);
		
		return global;
	}
}
