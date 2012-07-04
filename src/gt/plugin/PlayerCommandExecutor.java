package gt.plugin;

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
	 * @param player
	 * @param cmd
	 * @param label
	 * @param args
	 * @return
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
