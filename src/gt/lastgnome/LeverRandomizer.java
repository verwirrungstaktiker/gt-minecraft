package gt.lastgnome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gt.general.logic.TriggerContext;
import gt.general.logic.TriggerManager;
import gt.general.logic.trigger.RandomLeverTrigger;
import gt.general.logic.trigger.Trigger;

public class LeverRandomizer {
	
	private final TriggerManager triggerManager;
	private final Random rand = new Random();
	
	/**
	 * @param triggerManager where to look for riddles
	 */
	public LeverRandomizer(final TriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}
	
	/**
	 * randomizes the riddles in the trigger manager
	 */
	public void randomizeLevers() {
		for(TriggerContext context : triggerManager.getTriggerContexts()) {
			if(context.getLabel().contains("random_lever")) {
				randomizeLevers(context);
			}
		}
	}

	private void randomizeLevers(TriggerContext context) {
		List<List<RandomLeverTrigger>> groups = getLeversInGroups(context);
		
		for (List<RandomLeverTrigger> group : groups) {
			if (group.size() == 0) {
				continue;
			}
			for (int i=0;i<2;++i) {
				int random_index = rand.nextInt(group.size());
				RandomLeverTrigger lever = group.get(random_index);
				lever.setGreen(false);
				context.setupInverted(lever);
			}
			
		}
	}	
	
	private List<List<RandomLeverTrigger>> getLeversInGroups(TriggerContext context) {
		List<List<RandomLeverTrigger>> groups = new ArrayList<List<RandomLeverTrigger>>();
		
		for (Trigger trigger : context.getTriggers()) {
			if (trigger instanceof RandomLeverTrigger) {
				String label = trigger.getLabel();
				//Index where the group-number should be
				int index = label.indexOf("group")+5;
				//if the trigger label does not contain group, ignore it
				if (index == 4) {
					continue;
				}
				int groupNumber = Integer.parseInt(label.substring(index));
				//Fill up as much as necessary
				if (groups.size() <= groupNumber) {
					for (int i=groups.size();i<=groupNumber; ++i)
					groups.add(i, new ArrayList<RandomLeverTrigger>());
				}
				groups.get(groupNumber).add((RandomLeverTrigger) trigger);				
			}
		}
		
		return groups;
	}
}
