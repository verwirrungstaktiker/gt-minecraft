package gt.plugin.helloeditor;

import gt.general.trigger.Trigger;
import gt.general.trigger.TriggerContext;
import gt.general.trigger.persistance.YamlSerializable;
import gt.general.trigger.response.Response;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.particle.Particle;
import org.getspout.spoutapi.particle.Particle.ParticleType;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;


public class ParticleManager {
	
	private Multimap<YamlSerializable, Particle> activeParticles;
	
	public ParticleManager() {
		activeParticles = HashMultimap.create();
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
	 * despawns all particles of a context
	 * @param context	a TriggerContext
	 */
	public void removeHighlight(final TriggerContext context, final Player player) {
		for(Trigger trigger : context.getTriggers()) {
			removeHighlight(trigger, player);
		}
		for(Response response : context.getResponses()) {
			removeHighlight(response, player);
		}
	}
	
	/**
	 * despawns all particles of a trigger/response
	 * @param serializable	a trigger or response
	 */
	public void removeHighlight(final YamlSerializable serializable, final Player player) {
		for(Particle particle : activeParticles.get(serializable)) {
			//TODO: this doesn't work!
			particle.setMaxAge(5);
			particle.spawn((SpoutPlayer) player);
		}
		activeParticles.removeAll(serializable);
	}
	
	/**
	 * highlight all blocks of a serializable object
	 * @param serializable	trigger or response
	 * @param type			particle type
	 * @param player		player that can see particles
	 */
	public void addHighlight(final YamlSerializable serializable, final ParticleType type, final Player player) {
		Set<Particle> particles = new HashSet<Particle>();
		
		for(Block block: serializable.getBlocks()) {
			particles.addAll(addHighlight(block.getLocation(), type, player));
		}
		
		activeParticles.putAll(serializable, particles);
		
	}

	/**
	 * Highlight a single block with particles
	 * @param loc		Location of the block
	 * @param type		particle type
	 * @param player	player that can see particles
	 * @return Set of all particles that highlight the location
	 */
	public Set<Particle> addHighlight(final Location loc, final ParticleType type, final Player player) {
		Set<Particle> particles = new HashSet<Particle>();
		// yep, that's the 8 edges
		particles.add(paintParticle(type, loc, new Vector(0, 0, 0), player));
		particles.add(paintParticle(type, loc, new Vector(0, 0, 1), player));
		particles.add(paintParticle(type, loc, new Vector(0, 1, 0), player));
		particles.add(paintParticle(type, loc, new Vector(0, 1, 1), player));
		particles.add(paintParticle(type, loc, new Vector(1, 0, 0), player));
		particles.add(paintParticle(type, loc, new Vector(1, 0, 1), player));
		particles.add(paintParticle(type, loc, new Vector(1, 1, 0), player));
		particles.add(paintParticle(type, loc, new Vector(1, 1, 1), player));
		
		return particles;
	}

	/**
	 * spawns a single particle
	 * @param type	particle type
	 * @param origin	location of the block that is highlighted
	 * @param offset	an offset that specifies which edge of the block is highlighted
	 * @param player	player that can see the particle
	 * @return the particle
	 */
	private Particle paintParticle(final ParticleType type, final Location origin, final Vector offset, final Player player) {

		Location newLoc = origin.clone().add(offset);
		newLoc.subtract(0.04, 0.1, 0.04);	// looks much better with this additional offset
		Particle particle = new Particle(type, newLoc, new Vector(0,0,0));
		
		particle.setScale(7);		// particle size
		particle.setAmount(1);
		// view range: this one is overwritten by client settings!
		particle.setRange(100.0);	
		particle.setMaxAge(Integer.MAX_VALUE);		// lifetime in ticks
		particle.setGravity(0);
		
		particle.spawn((SpoutPlayer) player);
		
		return particle;
	}
}
