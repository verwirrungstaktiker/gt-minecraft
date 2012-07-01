package gt.plugin.helloeditor;

import gt.general.trigger.Trigger;
import gt.general.trigger.TriggerContext;
import gt.general.trigger.persistance.YamlSerializable;
import gt.general.trigger.response.Response;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.particle.Particle;
import org.getspout.spoutapi.particle.Particle.ParticleType;
import org.getspout.spoutapi.player.SpoutPlayer;


public class ParticleManager {
	
	public ParticleManager() {
		
	}
	
	/**
	 * highlight all blocks of a context
	 * @param context	TriggerContext
	 * @param type		Particle type
	 * @param player	player that can see particles
	 */
	public void addHighlight(final TriggerContext context, final ParticleType type, final Player player) {
		for(Trigger trigger : context.getTriggers()) {
			addHighlight(trigger, type, player);
		}
		for(Response response : context.getResponses()) {
			addHighlight(response, type, player);
		}
		player.sendMessage(ChatColor.YELLOW + "Highlighting " + context.getLabel());
	}
	
	/**
	 * highlight all blocks of a serializable object
	 * @param serializable	trigger or response
	 * @param type			particle type
	 * @param player		player that can see particles
	 */
	private void addHighlight(final YamlSerializable serializable, final ParticleType type, final Player player) {
		for(Block block: serializable.getBlocks()) {
			addHighlight(block.getLocation(), type, player);
		}
		
	}

	/**
	 * Highlight a single block with particles
	 * @param loc		Location of the block
	 * @param type		particle type
	 * @param player	player that can see particles
	 */
	public void addHighlight(final Location loc, final ParticleType type, final Player player) {

		paintParticle(type, loc, new Vector(0, 0, 0), player);
		paintParticle(type, loc, new Vector(0, 0, 1), player);
		paintParticle(type, loc, new Vector(0, 1, 0), player);
		paintParticle(type, loc, new Vector(0, 1, 1), player);
		paintParticle(type, loc, new Vector(1, 0, 0), player);
		paintParticle(type, loc, new Vector(1, 0, 1), player);
		paintParticle(type, loc, new Vector(1, 1, 0), player);
		paintParticle(type, loc, new Vector(1, 1, 1), player);
	}

	/**
	 * spawns a single particle
	 * @param type	particle type
	 * @param origin	location of the block that is highlighted
	 * @param offset	an offset that specifies which edge of the block is highlighted
	 * @param player	player that can see the particle
	 */
	private void paintParticle(final ParticleType type, final Location origin, final Vector offset, final Player player) {

		Location newLoc = origin.clone().add(offset);
		newLoc.subtract(0.04, 0.1, 0.04);	// looks much better with this additional offset
		Particle particle = new Particle(type, newLoc, new Vector(0,0,0));
		
		particle.setScale(7);		// particle size
		particle.setAmount(1);
		// view range: this one is overwritten by client settings!
		particle.setRange(50.0);	
		particle.setMaxAge(60);		// lifetime in ticks
		particle.setGravity(0);
		
		particle.spawn((SpoutPlayer) player);
//		return particle;
	}
}
