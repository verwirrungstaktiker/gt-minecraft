package gt.plugin.helloeditor;

import gt.general.trigger.Trigger;
import gt.general.trigger.TriggerContext;
import gt.general.trigger.persistance.YamlSerializable;
import gt.general.trigger.response.Response;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.particle.Particle;
import org.getspout.spoutapi.particle.Particle.ParticleType;


public class ParticleManager {
	
	private Map<Location, HashSet<Particle>> highlightedLocs = new HashMap<Location, HashSet<Particle>>();
	
	public ParticleManager() {
		
	}
	
	public void addHighlight(TriggerContext context, ParticleType type) {
		for(Trigger trigger : context.getTriggers()) {
			addHighlight(trigger, type);
		}
		for(Response response : context.getResponses()) {
			addHighlight(response, type);
		}
	}
	
	private void addHighlight(YamlSerializable serializable, ParticleType type) {
		for(Block block: serializable.getBlocks()) {
			addHighlight(block.getLocation(), type);
		}
		
	}

	public void addHighlight(Location loc, ParticleType type) {

		 HashSet<Particle> particles = getEdgeParticles(loc, type);
		 
		 highlightedLocs.put(loc, particles);
	}

	private HashSet<Particle> getEdgeParticles(Location loc, ParticleType type) {
		HashSet<Particle> particles = new HashSet<Particle>();
		
		Particle particle = paintParticle(type, loc);
//		particles.add(particle);
		
		particle = paintParticle(type, loc.add(0, 0, 1));
		particle = paintParticle(type, loc.add(0, 1, 0));
		particle = paintParticle(type, loc.add(0, 1, 1));
		particle = paintParticle(type, loc.add(1, 0, 0));
		particle = paintParticle(type, loc.add(1, 0, 1));
		particle = paintParticle(type, loc.add(1, 1, 0));
		particle = paintParticle(type, loc.add(1, 1, 1));
		
//		particles.add(particle);
		
		return particles;
	}

	private Particle paintParticle(ParticleType type, Location loc) {

		Particle particle = new Particle(type, loc, new Vector(0,0,0));
		
		particle.setScale(5);
		particle.setAmount(1);
		particle.setRange(20.0);
		particle.spawn();
		
		
		return particle;
	}
}
