package gt.editor;

import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;

import gt.general.logic.TriggerContext;
import gt.general.logic.response.BlockAppearResponse;
import gt.general.logic.response.DoorResponse;
import gt.general.logic.response.RedstoneTorchResponse;
import gt.general.logic.response.Response;
import gt.general.logic.response.SignResponse;
import gt.general.logic.trigger.ButtonRedstoneTrigger;
import gt.general.logic.trigger.LeverRedstoneTrigger;
import gt.general.logic.trigger.PressurePlateRedstoneTrigger;
import gt.general.logic.trigger.QuestionTrigger;
import gt.general.logic.trigger.Trigger;
import gt.general.world.ObservableCustomBlock;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.getspout.spout.block.SpoutCraftBlock;


public class BuildManager implements Listener {	
	
	private final PlayerManager playerManager;
	
	/**
	 * @param playerManager manages the player states
	 */
	public BuildManager(final PlayerManager playerManager) {
		this.playerManager = playerManager;
	}
	
	/**
	 * register Triggers and Responses to playerTriggerContexts
	 * @param event player places a block
	 */
	@EventHandler
	public void onBlockPlaced(final BlockPlaceEvent event) {
		String name = event.getPlayer().getName();
		
		switch(playerManager.getState(name)) {
			case TRIGGER: 
				addTrigger(event);
				break;
			case RESPONSE:
				addResponse(event);
				break;
			default:
		}
	}
	
	/**
	 * adds a new trigger to the currently active context
	 * 
	 * @param event the trigger(event) for the trigger to be placed
	 */
	private void addTrigger(final BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();
		TriggerContext activeContext = playerManager.getContext(player.getName());
		
		Trigger newTrigger = null;
		
		if(block instanceof SpoutCraftBlock) {
			SpoutCraftBlock sBlock = (SpoutCraftBlock) block;
			// custom blocks
			if(sBlock.getCustomBlock() instanceof ObservableCustomBlock) {
				ObservableCustomBlock oBlock = (ObservableCustomBlock) sBlock.getCustomBlock();

				switch (oBlock.getCustomBlockType()) {
				case QUESTION_BLOCK:
					newTrigger = new QuestionTrigger(block);
					break;

				default:
					// fail feedback
					player.sendMessage(RED + "This CustomBlock can't be used as Trigger.");
					System.out.println("This CustomBlock can't be used as Trigger. --> " + sBlock.getName());
					return;
				}
				
			// vanilla minecraft block
			} else {
				switch(block.getType()) {
					case WOOD_PLATE:
					case STONE_PLATE:
						newTrigger = new PressurePlateRedstoneTrigger(block);
						break;
						
					case LEVER:
						newTrigger = new LeverRedstoneTrigger(block, event.getBlockAgainst());
						break;
					case STONE_BUTTON:
						newTrigger = new ButtonRedstoneTrigger(block, event.getBlockAgainst());
						break;
						
					default: 
						// fail feedback
						player.sendMessage(RED + "This Block can't be used as Trigger.");
						System.out.println("This Block can't be used as Trigger. --> " + block.getType());
						return;
				}
			}
		}
		
		// success
		playerManager.addTrigger(newTrigger, activeContext, player);
		player.sendMessage(GREEN + "added Trigger: " + newTrigger.getLabel());
	}

	/**
	 * adds a new response to the currently active context
	 * 
	 * @param event the trigger(event) for the response to be placed
	 */
	private void addResponse(final BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();
		TriggerContext activeContext = playerManager.getContext(player.getName());
		
		Response newResponse = null;
		
		switch(block.getType()) {
			case WOOD_DOOR:
			case WOODEN_DOOR:
			case IRON_DOOR:
			case IRON_DOOR_BLOCK:
				newResponse = new DoorResponse(block);
				break;
			case DIAMOND_BLOCK:
				newResponse = new BlockAppearResponse(block);
				break;
			case REDSTONE_LAMP_OFF:
			case REDSTONE_TORCH_ON:
				newResponse = new RedstoneTorchResponse(block, event.getBlockAgainst());
				break;
			case WALL_SIGN:
			case SIGN_POST:
				newResponse = new SignResponse(block);
				break;
			default:
				// fail feedback
				player.sendMessage(RED + "This Block can't be used as Response.");
				System.out.println("This Block can't be used as Response. --> " + block.getType());
				return;
		}
		
		// success message
		playerManager.addResponse(newResponse, activeContext, player);
		player.sendMessage(GREEN + "added Response: " + newResponse.getLabel());
	}
}
