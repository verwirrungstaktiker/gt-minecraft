/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor;

import gt.general.logic.persistence.YamlSerializable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.particle.Particle;
import org.getspout.spoutapi.particle.Particle.ParticleType;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Manages particles that are used to highlight Logic
 * @author Roman
 *
 */
public class ParticleManager implements Runnable, Listener {	
	private final PlayerManager playerManager;
	
	private enum HighlightType {
		STANDARD, SPECIAL
	}
	
	/**
	 * Construct new ParticleManager
	 * @param playerManager the Manager of Logic Building
	 */
	public ParticleManager(final PlayerManager playerManager) {
		this.playerManager = playerManager;
	}
	
	/**
	 * highlights the edges of a block
	 * @param block the block to highlight
	 * @param player a bukkit player
	 * @param type 
	 */
	private void highlight(final Block block, final Player player, final HighlightType type) {
		Location loc = block.getLocation();
		
		// yep, that's the 8 edges
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
	 * @return the particle
	 */
	private Particle paintParticle(final HighlightType type, final Location origin, final Vector offset, final Player player) {
		
		Location newLoc = origin.clone().add(offset);
		newLoc.subtract(0.04, 0.1, 0.04);	// looks much better with this additional offset
		
		Particle particle;
		
		switch (type) {
		case SPECIAL:
			particle = new Particle(ParticleType.DRIPLAVA, newLoc, new Vector(0,0,0));
			particle.setParticleRed(0.0f);
			particle.setParticleGreen(0.0f);
			particle.setParticleBlue(0.0f);
			break;
		default:
			particle = new Particle(ParticleType.DRIPWATER, newLoc, new Vector(0,0,0));
			break;
		}
		
		particle.setScale(7);		// particle size
		particle.setAmount(1);
		// view range: this one is overwritten by client settings!
		particle.setRange(100.0);	
		particle.setMaxAge(20);		// lifetime in ticks
		particle.setGravity(0);
		
		particle.spawn((SpoutPlayer) player);
		
		return particle;
	}

	@Override
	public void run() {
		
		for(EditorPlayer ePlayer : playerManager.getEditorPlayers()) {
			if(!ePlayer.isSuppressHighlight() && ePlayer.getActiveContext() != null) {
				for(YamlSerializable item : ePlayer.getActiveContext().getAllItems()) {
					
					HighlightType type;
					
					if(ePlayer.getSelectedItem() == item) {
						type = HighlightType.SPECIAL;
					} else {
						type = HighlightType.STANDARD;
					}
					
					for(Block block : item.getBlocks()) {
						highlight(block, ePlayer.getPlayer(), type);
					}
				}
			}
		}
	}
}
