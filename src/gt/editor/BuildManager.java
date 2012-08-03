package gt.editor;

import static org.bukkit.ChatColor.*;
import gt.editor.EditorPlayer.TriggerState;
import gt.general.logic.TriggerContext;
import gt.general.logic.response.BlockDisappearResponse;
import gt.general.logic.response.DoorResponse;
import gt.general.logic.response.RedstoneTorchResponse;
import gt.general.logic.response.Response;
import gt.general.logic.response.SignResponse;
import gt.general.logic.response.TeleportResponse;
import gt.general.logic.response.ZombieSpawnResponse;
import gt.general.logic.trigger.ButtonRedstoneTrigger;
import gt.general.logic.trigger.GnomeTrigger;
import gt.general.logic.trigger.LeverRedstoneTrigger;
import gt.general.logic.trigger.PressurePlateRedstoneTrigger;
import gt.general.logic.trigger.QuestionTrigger;
import gt.general.logic.trigger.StepOnTrigger;
import gt.general.logic.trigger.Trigger;
import gt.general.world.ObservableCustomBlock;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
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
		EditorPlayer p = playerManager.getEditorPlayer(event.getPlayer());
		
		switch(p.getTriggerState()) {
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
	 * Prevents to modification of the inventory if the player is in Trigger or Response TriggerState
	 * @param event player clicks something in his inventory
	 */
	@EventHandler
	public void preventInventoryModification(final InventoryClickEvent event) {
		
		HumanEntity human = event.getWhoClicked();
		if (human instanceof Player) {
			Player player = (Player) human;
			EditorPlayer ePlayer = playerManager.getEditorPlayer(player);
			
			TriggerState state = ePlayer.getTriggerState();
			if(state == TriggerState.RESPONSE || state == TriggerState.TRIGGER) {
				player.sendMessage(ChatColor.RED + "You can only modify your STANDYBY inventory.");
				
				event.setCancelled(true);
			}
		}

	}
	
	/**
	 * adds a new trigger to the currently active context
	 * 
	 * @param event the trigger(event) for the trigger to be placed
	 */
	private void addTrigger(final BlockPlaceEvent event) {
		EditorPlayer player = playerManager.getEditorPlayer(event.getPlayer());
		Block block = event.getBlockPlaced();
		TriggerContext activeContext = player.getActiveContext();
		
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
                case GNOME_TRIGGER_NEGATIVE:
                    newTrigger = new GnomeTrigger(block);
                    break;
                case STEP_ON_TRIGGER:
                	newTrigger = new StepOnTrigger(block);
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
		playerManager
			.getTriggerManager()
			.addTrigger(newTrigger, activeContext);
		player.sendMessage(GREEN + "added Trigger: " + newTrigger.getLabel());
	}

	/**
	 * adds a new response to the currently active context
	 * 
	 * @param event the trigger(event) for the response to be placed
	 */
	private void addResponse(final BlockPlaceEvent event) {
		EditorPlayer player = playerManager.getEditorPlayer(event.getPlayer());
		
		Block block = event.getBlockPlaced();
		TriggerContext activeContext = player.getActiveContext();
		
		Response newResponse = null;
		
		if(block instanceof SpoutCraftBlock) {
			SpoutCraftBlock sBlock = (SpoutCraftBlock) block;
			// custom blocks
			if(sBlock.getCustomBlock() instanceof ObservableCustomBlock) {
				ObservableCustomBlock oBlock = (ObservableCustomBlock) sBlock.getCustomBlock();

				switch (oBlock.getCustomBlockType()) {
				case ZOMBIESPAWN_BLOCK:
					newResponse = new ZombieSpawnResponse(null,block.getLocation(),1);
					event.setCancelled(true);
					break;
                case TELEPORT_EXIT:
                	newResponse = new TeleportResponse(block);
                	break;
					
				default:
					// fail feedback
					player.sendMessage(RED + "This CustomBlock can't be used as Response.");
					System.out.println("This CustomBlock can't be used as Response. --> " + sBlock.getName());
					return;
				}
			//	vanilla minecraft block
			} else {
				switch(block.getType()) {
				case WOOD_DOOR:
				case WOODEN_DOOR:
				case IRON_DOOR:
				case IRON_DOOR_BLOCK:
					newResponse = new DoorResponse(block);
					break;
				case DIAMOND_BLOCK:
				case IRON_FENCE:
					newResponse = new BlockDisappearResponse(block);
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
			}	
		}
		
		// success message
		playerManager
			.getTriggerManager()
			.addResponse(newResponse, activeContext);
		player.sendMessage(GREEN + "added Response: " + newResponse.getLabel());
	}
}
