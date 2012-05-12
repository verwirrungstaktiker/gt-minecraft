package gt.lastgnome;

import gt.general.Team;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class LastGnomeTeam extends Team implements Listener {

	LastGnomeHero[] players;
	LastGnomeHero gnomeBearer; // so that e.g. Zombies know who the Gnome-Bearer
								// is

	private final Gnome gnome;

	public LastGnomeTeam(LastGnomeHero[] players, LastGnomeHero gnomeBearer) {
		super(players);

		/*
		 * TODO initialze the ItemStack Correctly
		 */
		gnome = new Gnome(new ItemStack(Material.CAKE, 1));

		for (int i = 0; i < players.length; ++i) {
			players[i].setTeam(this);
		}
		this.gnomeBearer = gnomeBearer;
	}

	public void setGnomeBearer(LastGnomeHero gnomeBearer) {
		this.gnomeBearer = gnomeBearer;
	}

	public LastGnomeHero getGnomeBearer() {
		return gnomeBearer;
	}

	private LastGnomeHero playerToHero(Player player) {
		for (int i = 0; i < players.length; ++i) {
			if (players[i].getPlayer().equals(player)) {
				return players[i];
			}
		}
		return null;
	}

	@EventHandler
	public void handleGnomGiving(PlayerInteractEntityEvent event) {
		if (!gnomeBearer.getPlayer().equals(event.getPlayer())) {
			return;
		}
		if (event.getRightClicked() instanceof Player) {
			LastGnomeHero newBearer = playerToHero((Player) event
					.getRightClicked());
			// If Player does not belong to the Team, stop here.
			if (newBearer == null) {
				return;
			}
			// Try to give Gnome, on success sync with MineCraft
			if (gnomeBearer.giveGnomeTo(newBearer)) {
				newBearer.getPlayer().setItemInHand(
						gnomeBearer.getPlayer().getItemInHand());
				gnomeBearer.getPlayer().setItemInHand(null);
				gnomeBearer = newBearer;
				// ToDo: Auras
			}
		}
	}

	/**
	 * @return the gnome
	 */
	public Gnome getGnome() {
		return gnome;
	}

}
