package gt.plugin.meta;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label,
			final String[] args) {
		
		if(isPlayer(sender)) {
			return onPlayerCommand((Player) sender, cmd, label, args);
		}
		return false;
	}
	
	
	/**
	 * optional override
	 * 
	 * @param player the player who called the command
	 * @param cmd the command itself
	 * @param label the label of the command
	 * @param args passed arguments
	 * @return true on success
	 */
	public abstract boolean onPlayerCommand(final Player player, final Command cmd, final String label, final String[] args);

	/**
	 * @param commandSender the commandSender to be tested
	 * @return true if commandSender is a subclass of Player
	 */
	public static boolean isPlayer(final CommandSender commandSender) {
		return commandSender instanceof Player;
	}
}
