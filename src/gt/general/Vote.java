/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general;

import static com.google.common.collect.Sets.newHashSet;
import gt.general.character.Hero;

import java.util.Set;

import org.bukkit.ChatColor;

import com.google.common.collect.Iterables;

public abstract class Vote {
	
	// just for counting unique objects
	private final Set<Hero> accepting = newHashSet();
	private final Set<Hero> rejecting = newHashSet();
	
	private final int acceptThreshold;
	private final int rejectThreshold;
	
	private boolean open = true;
	
	/**
	 * Construct a new Vote
	 * @param acceptThreshold Threshold which accepts the vote
	 * @param rejectThreshold Threshold which rejects the vote
	 */
	public Vote(final int acceptThreshold, final int rejectThreshold) {
		this.acceptThreshold = acceptThreshold;
		this.rejectThreshold = rejectThreshold;
	}
	
	/**
	 * hero has accepted the vote
	 * @param h the hero that accepted
	 */
	public void voteAccept(final Hero h) {
		rejecting.remove(h);
		accepting.add(h);
		
		evaluateVote();
	}
	
	/**
	 * hero has rejected the vote
	 * @param h the hero that rejected
	 */
	public void voteReject(final Hero h) {
		accepting.remove(h);
		rejecting.add(h);
		
		evaluateVote();
	}
	
	/**
	 * send a message about the vote status to a hero
	 * @param h hero that receives the message
	 */
	private void sendMessage(final Hero h) {
		h.getPlayer().sendMessage(ChatColor.GREEN + "" + accepting.size() + "/" + acceptThreshold + " accepting, " + ChatColor.RED + rejecting.size() + "/" + rejectThreshold + " rejecting");
	}

	/**
	 * vote evaluation
	 */
	private void evaluateVote() {
		
		for(Hero h : Iterables.concat(accepting, rejecting)) {
			sendMessage(h);
		}
		
		
		if(open) {
			if(accepting.size() >= acceptThreshold) {
				open = false;
				onAccept();
				
			} else if( rejecting.size() >= rejectThreshold) {
				open = false;
				onReject();
			}
		}
	}
	
	
	
	/** vote has been accepted */
	public abstract void onAccept();
	/** vote has been rejected */
	public abstract void onReject();
	
}
