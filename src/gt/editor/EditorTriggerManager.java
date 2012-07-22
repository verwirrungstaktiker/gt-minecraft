package gt.editor;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import gt.editor.LogicObserver.Observee;
import gt.general.logic.TriggerContext;
import gt.general.logic.TriggerManager;
import gt.general.logic.persistance.YamlSerializable;
import gt.general.logic.response.Response;
import gt.general.logic.trigger.Trigger;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.bukkit.block.Block;

/**
 * Manages adding and deleting of TriggerContexts
 * keeps the blockToXXX maps in a consistent state
 */
public class EditorTriggerManager extends TriggerManager {
	
	private Map<Block, TriggerContext> blockToContext = newHashMap();
	private Map<Block, YamlSerializable> blockToSerializable = newHashMap();
	private Set<LogicObserver> observers = newHashSet();

	/**
	 * Do not delete this anonymous constructor
	 */
	public EditorTriggerManager() {
		super();
	}
	
	/**
	 * @param triggerContexts active contexts
	 */
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
		
		notifyTriggerContextChanged();
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
	 * Adds all blocks of a trigger to the maps
	 * @param trigger the added trigger
	 * @param context a triggerContext
	 */
	public void addTrigger(final Trigger trigger, final TriggerContext context) {
		for(Block block : trigger.getBlocks()) {
			context.addTrigger(trigger);
			
			blockToContext.put(block, context);
			blockToSerializable.put(block, trigger);
		}
	}
	
	/**
	 * Adds all blocks of a response to the maps
	 * @param response the added response
	 * @param context a triggerContext
	 */
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
		
		notifyTriggerContextChanged();
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
	 * @param serializable YamlSerializable
	 * @param context a triggerContext
	 */
	private void deleteSerializable(final YamlSerializable serializable, final TriggerContext context) {
		for(Block block : serializable.getBlocks()) {
			blockToContext.remove(block);
			blockToSerializable.remove(block);
		}
	}
	
	/**
	 * Delete a single block from the maps
	 * @param block bukkit block
	 */
	public void deleteBlock(final Block block) {
		TriggerContext context = getContext(block);
		YamlSerializable serializable = getSerializable(block);
		
		context.removeSerializable(serializable);
		
		blockToContext.remove(block);
		blockToSerializable.remove(block);
	}
	
	/**
	 * @param block bukkit block
	 * @return true if a block is part of a serializable
	 */
	public boolean isSerializable(final Block block) {
		return blockToContext.containsKey(block);
	}
	
	/**
	 * @param block bukkit block
	 * @return The context that corresponds to the block, null if it isn't part of a context
	 */
	public TriggerContext getContext(final Block block) {
		return blockToContext.get(block);
	}
	
	/**
	 * @param block bukkit block
	 * @return The serializable that corresponds to the block, null if it isn't part of a serializable
	 */
	public YamlSerializable getSerializable(final Block block) {
		return blockToSerializable.get(block);
	}
	
	/**
	 * Adds an Observer
	 * @param observer the Observer that is added
	 */
	public void addTriggerContextObserver(final LogicObserver observer) {
		observers.add(observer);
	}
	
	/**
	 * Deletes and Observer
	 * @param observer the Observer that is deleted
	 */
	public void removeTriggerContextObserver(final LogicObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Notify the Observers on context modification
	 */
	private void notifyTriggerContextChanged() {
		for(LogicObserver observer : observers) {
			observer.update(Observee.TRIGGER_MANAGER, this);
		}
	}
	
}
