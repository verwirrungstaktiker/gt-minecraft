package gt.general;

import static com.google.common.collect.Sets.*;

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
	
	public Vote(final int acceptThreshold, final int rejectThreshold) {
		this.acceptThreshold = acceptThreshold;
		this.rejectThreshold = rejectThreshold;
	}
	
	public void voteAccept(final Hero h) {
		rejecting.remove(h);
		accepting.add(h);
		
		evaluateVote();
	}
	
	public void voteReject(final Hero h) {
		accepting.remove(h);
		rejecting.add(h);
		
		evaluateVote();
	}
	
	private void sendMessage(Hero h) {
		h.getPlayer().sendMessage(ChatColor.GREEN + "" + accepting.size() + "/" + acceptThreshold + " accepting, " + ChatColor.RED + rejecting.size() + "/" + rejectThreshold + " rejecting");
	}

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
	
	
	
	
	public abstract void onAccept();
	
	public abstract void onReject();
	
}
