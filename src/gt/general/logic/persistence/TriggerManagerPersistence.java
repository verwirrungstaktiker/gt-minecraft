/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.persistence;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import gt.general.logic.TriggerContext;
import gt.general.logic.TriggerManager;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.logic.response.Response;
import gt.general.logic.trigger.Trigger;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.bukkit.World;

public class TriggerManagerPersistence {
	
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
	TriggerManagerPersistence(final TriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}

	/**
	 * @param triggerManager the TriggerManager to be persisted
	 * @param world the World in which the TriggerManager operates
	 */
	public TriggerManagerPersistence(final TriggerManager triggerManager, final World world) {
		this.triggerManager = triggerManager;
		this.world = world;
	}

	/**
	 * Make the TriggerManager read for work
	 * @param triggerManager the TriggerManager that is set up
	 * @param values the data for the TriggerManager
	 * @param world the world in which the TriggerManager operates
	 */
	public static void setupTriggerManager(final TriggerManager triggerManager, final PersistenceMap values, final World world) {
		new TriggerManagerPersistence(triggerManager, world).setup(values);
	}
	
	/**
	 * Save the TriggerManagers Data in a Serializable Object
	 * @param triggerManager the TriggerManager to be saved
	 * @return the data in a Serializable Object
	 */
	public static Map<String, Object> dumpTriggerManager(final TriggerManager triggerManager) {
		return new TriggerManagerPersistence(triggerManager).dump();
	}
	
	/**
	 * Load data
	 * @param values PersistenceMap that contains the data that is loaded
	 */
	public void setup(final PersistenceMap values) {
		try {
			globalContexts = values.get(KEY_GLOBAL_CONTEXTS);
			globalResponses = values.get(KEY_GLOBAL_RESPONSES);
			globalTriggers = values.get(KEY_GLOBAL_TRIGGERS);				
		} catch (PersistenceException e) {
			throw new RuntimeErrorException(null,"Error loading "+e.getKey());
		}
			 
			
			
		for(String contextLabel : globalContexts.keySet()) {
				loadTriggerContext(contextLabel);
		}
	}

	/**
	 * load a TriggerContext
	 * @param contextLabel name/label of the TriggerContext
	 */
	@SuppressWarnings("unchecked")
	private void loadTriggerContext(final String contextLabel) {
		
		Map<String, ? extends Object> contextMap = (Map<String, Object>) globalContexts.get(contextLabel);
		
		TriggerContext tc = new TriggerContext();
		tc.setInputFunction(TriggerContext.InputFunction.valueOf((String) contextMap.get(KEY_INPUT_FUNCTION)));
		tc.setLabel(contextLabel);
		
		
			for(String triggerLabel : (List<String>) contextMap.get(KEY_TRIGGERS)) {
				
				Map<String, Object> map =(Map<String, Object>) globalTriggers.get(triggerLabel);
				
				if (map == null) {
					throw new RuntimeErrorException(null,"Error loading Trigger "
							+triggerLabel+" in Context "+contextLabel+": Trigger cannot be found");					
				}
				
				PersistenceMap triggerMap = new PersistenceMap(map);
				
				Trigger t = null;
				try {
					t = loadSerializable(triggerLabel, triggerMap);
				} catch (ClassCastException e) {
					throw new RuntimeErrorException(null,"Error loading field '"+triggerMap.getLastKey()
							+"' in Trigger "+triggerLabel+" in Context "+contextLabel+": Wrong Type");
				} catch (PersistenceException e) {
					throw new RuntimeErrorException(null,"Error loading field '"+e.getKey()+"' in Trigger "
							+triggerLabel+" in Context "+contextLabel+": field is null or missing");
				} catch (InstantiationException e) {
					throw new RuntimeErrorException(null,"Error loading Trigger "
							+triggerLabel+" in Context "+contextLabel+": InstantiationException");
				} catch (IllegalAccessException e) {
					throw new RuntimeErrorException(null,"Error loading Trigger "
							+triggerLabel+" in Context "+contextLabel+": IllegalAccessException");
				} catch (ClassNotFoundException e) {
					throw new RuntimeErrorException(null,"Error loading Trigger "
							+triggerLabel+" in Context "+contextLabel+": Class not found");
				}
				t.setContext(tc);
				
				tc.addTrigger(t);
			}
			
			for(String responseLabel : (List<String>) contextMap.get(KEY_RESPONSES)) {
				Map<String, Object> map =(Map<String, Object>) globalResponses.get(responseLabel);
				
				if (map == null) {
					throw new RuntimeErrorException(null,"Error loading Response "
							+responseLabel+" in Context "+contextLabel+": Response cannot be found");					
				}
				
				PersistenceMap responseMap = new PersistenceMap(map);
				
				try {
					tc.addResponse((Response) loadSerializable(responseLabel, responseMap));
				} catch (ClassCastException e) {
					throw new RuntimeErrorException(null,"Error loading field '"+responseMap.getLastKey()
							+"' in Response "+responseLabel+" in Context "+contextLabel+": Wrong Type ("+ e.getMessage() +")");
				} catch (PersistenceException e) {
					throw new RuntimeErrorException(null, "Error loading field '"+e.getKey()+"' in Response "
							+responseLabel+" in Context "+contextLabel+": field is null or missing");					
				} catch (InstantiationException e) {
					throw new RuntimeErrorException(null,"Error loading Response "
							+responseLabel+" in Context "+contextLabel+": InstantiationException");
				} catch (IllegalAccessException e) {
					throw new RuntimeErrorException(null,"Error loading Response "
							+responseLabel+" in Context "+contextLabel+": IllegalAccessException");
				} catch (ClassNotFoundException e) {
					throw new RuntimeErrorException(null,"Error loading Response "
							+responseLabel+" in Context "+contextLabel+": Class not found");
				}
			}
			

		
		triggerManager.addTriggerContext(tc);
	}
	
	/**
	 * Load Serializable Object
	 * @param label name of the field to be loaded
	 * @param map data is loaded from here
	 * @param <T> type of the loaded Serializable Object
	 * @return a Serializable Object
	 * @throws InstantiationException thrown if Serializable Object can't be instantiated
	 * @throws IllegalAccessException thrown if Serializable Object can't be instantiated
	 * @throws ClassNotFoundException thrown for unknown class Type <T>
	 * @throws PersistenceException 
	 */
	@SuppressWarnings("unchecked")
	private <T extends YamlSerializable> T loadSerializable(final String label, final PersistenceMap map) throws InstantiationException, IllegalAccessException, ClassNotFoundException, PersistenceException {
		
		String className = (String) map.remove(KEY_CLASS);
		
		if (className == null) {
			throw new PersistenceException(KEY_CLASS);
		}
		
		T serializable = (T) Class.forName(className).newInstance();
		serializable.setLabel(label);
		serializable.setup(map, world);
		
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

	/**
	 * Save a TriggerContext
	 * @param triggerContext TriggerContext to be saved
	 */
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

	/**
	 * Get the data that is necessary for a dump
	 * @param serializable the YamlSerializable that is dumped
	 * @return data for dump
	 */
	private Map<String, Object> prepareDump(final YamlSerializable serializable) {
		
		Map<String, Object> ret = serializable.dump().getMap();
		ret.put(KEY_CLASS, serializable.getClass().getName());
		
		return ret;
	}

	/**
	 * @return the whole data as a Map
	 */
	private Map<String, Object> finalizeMap() {
		Map<String, Object> global = newHashMap();
		global.put(KEY_GLOBAL_TRIGGERS, globalTriggers);
		global.put(KEY_GLOBAL_RESPONSES, globalResponses);
		global.put(KEY_GLOBAL_CONTEXTS, globalContexts);
		
		return global;
	}
}
