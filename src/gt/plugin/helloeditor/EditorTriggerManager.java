package gt.plugin.helloeditor;

import gt.general.trigger.Trigger;
import gt.general.trigger.TriggerContext;
import gt.general.trigger.TriggerManager;
import gt.general.trigger.persistance.YamlSerializable;
import gt.general.trigger.response.Response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.block.Block;

/**
 * Manages adding and deleting of TriggerContexts
 * keeps the blockToXXX maps in a consistent state
 */
public class EditorTriggerManager extends TriggerManager {
	
	Map<Block, TriggerContext> blockToContext = new HashMap<Block, TriggerContext>();
	
	Map<Block, YamlSerializable> blockToSerializable = new HashMap<Block, YamlSerializable>();

	
	public EditorTriggerManager() {
		super();
	}
	
	public EditorTriggerManager(final Set<TriggerContext> triggerContexts) {
		super(triggerContexts);
		for(TriggerContext context : triggerContexts) {
			addBlockContextMapping(context);
		}
	}
	
	/**
	 * @param context the TriggerContext that is added
	 */
	public void addTriggerContext(final TriggerContext context) {
		super.addTriggerContext(context);
		addBlockContextMapping(context);
	}
	
	/**
	 * Adds all blocks of a context to the maps
	 * @param context a triggerContext
	 */
	private void addBlockContextMapping(final TriggerContext context) {
		
		Collection<Trigger> triggers = context.getTriggers();
		for(Trigger trigger : triggers) {
			addTrigger(trigger, context);
		}
		
		Collection<Response> responses = context.getResponses();
		for(Response response : responses) {
			addResponse(response, context);
		}
	}
	
	/**
	 * Adds all blocks of a trigger object to the maps
	 * @param serializable trigger
	 * @param context a triggerContext
	 */
	public void addTrigger(final Trigger trigger, final TriggerContext context) {
		for(Block block : trigger.getBlocks()) {
			context.addTrigger(trigger);
			
			blockToContext.put(block, context);
			blockToSerializable.put(block, trigger);
		}
	}
	
	public void addResponse(final Response response, final TriggerContext context) {
		for(Block block : response.getBlocks()) {
			context.addResponse(response);
			
			blockToContext.put(block, context);
			blockToSerializable.put(block, response);
		}
	}
	
	/**
	 * Deletes a TriggerContext
	 * @param context a triggerContext
	 */
	public void deleteTriggerContext(final TriggerContext context) {
		super.deleteTriggerContext(context);
		deleteBlockContextMapping(context);
	}
	
	/**
	 * Delete all blocks of a context from the maps
	 * @param context a triggerContext
	 */
	private void deleteBlockContextMapping(final TriggerContext context) {

		Collection<Trigger> triggers = context.getTriggers();
		for(Trigger trigger : triggers) {
			deleteSerializable(trigger, context);
		}
		
		Collection<Response> responses = context.getResponses();
		for(Response response : responses) {
			deleteSerializable(response, context);
		}
	}

	/**
	 * Delete all blocks of a serializable object from the maps
	 * @param context a triggerContext
	 */
	private void deleteSerializable(YamlSerializable serializable, TriggerContext context) {
		for(Block block : serializable.getBlocks()) {
			blockToContext.remove(block);
			blockToSerializable.remove(block);
		}
	}
	
	public void deleteBlock(Block block) {
		TriggerContext context = getContext(block);
		YamlSerializable serializable = getSerializable(block);
		
		context.removeSerializable(serializable);
		
		blockToContext.remove(block);
		blockToSerializable.remove(block);
	}
	
	public boolean isSerializable(Block block) {
		return blockToContext.containsKey(block);
	}
	
	public TriggerContext getContext(Block block) {
		return blockToContext.get(block);
	}
	
	public YamlSerializable getSerializable(Block block) {
		return blockToSerializable.get(block);
	}
}
