package gt.lastgnome;

import gt.general.trigger.QuestionTrigger;
import gt.general.trigger.Trigger;
import gt.general.trigger.TriggerContext;
import gt.general.trigger.TriggerManager;
import gt.general.trigger.response.Response;
import gt.general.trigger.response.SignResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MathRiddleRandomizer {

	private final TriggerManager triggerManager;
	private final Random rand = new Random();
	
	
	public MathRiddleRandomizer(final TriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}
	
	/**
	 * randomizes the riddles in the trigger manager
	 */
	public void randomizeMathRiddles() {
		for(TriggerContext context : triggerManager.getTriggerContexts()) {
			if(context.getLabel().contains("math_riddle")) {
				randomizeRiddle(context);
			}
		}
	}

	private void randomizeRiddle(final TriggerContext context) {
		
		QuestionTrigger question = getQuestionTrigger(context);
		List<SignResponse> signs = getSignResponses(context);
		
		String variables = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";		
		
		int intermediate = 0;
		
		int i = 0;
		for(; i < signs.size(); i++) {
			// always plus one to eliminate the zero
			int number = rand.nextInt(9) + 1;
			char var = variables.charAt(i);
			
			SignResponse sign = signs.get(i);
			
			if(i == 0) {
				sign.setUntriggeredMessage(var + " = " + number);
				intermediate = number;
			} else {
				
				if( intermediate + number <= 20) {
					sign.setUntriggeredMessage(var + " = " + number + " + " + variables.charAt(i-1));
					intermediate += number;
				} else {
					sign.setUntriggeredMessage(var + " = " + number + " - " + variables.charAt(i-1));
					intermediate -= number;
				}
			}
		}
		
		question.setQuestion("What value has " + variables.charAt(i-1) + " ?");
		question.setAnswer(Integer.toString(intermediate));
		
	}

	private List<SignResponse> getSignResponses(TriggerContext context) {
		
		List<SignResponse> signs = new ArrayList<SignResponse>();
		
		for(Response r : context.getResponses()) {
			if(r instanceof SignResponse) {
				signs.add((SignResponse) r);
			}
		}
		
		if(signs.size() < 1) {
			throw new RuntimeException("a math riddle must have at least one sign response");
		}
		
		Collections.sort(signs, new Comparator<SignResponse>() {
			@Override
			public int compare(final SignResponse o1, final SignResponse o2) {
				return o1.getLabel().compareToIgnoreCase(o2.getLabel());
			}
		});
		
		return signs;
	}

	/**
	 * ensures there is only one question in the context
	 * 
	 * @param context where to look
	 * @return the question
	 */
	private QuestionTrigger getQuestionTrigger(final TriggerContext context) {
		
		QuestionTrigger question = null;
		
		for(Trigger t : context.getTriggers()) {
			if( t instanceof QuestionTrigger) {
				if(question == null) {
					question = (QuestionTrigger) t;
				} else {
					throw new RuntimeException("a math riddle must have exactly one question trigger");
				}
			}
		}
		
		if (question == null) {
			throw new RuntimeException("a math riddle must have exactly one question trigger");
		}
		
		return question;
	}
	
}
