package gt.general.trigger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TriggerContext {

	public enum InputFunction {
		AND, OR
	}
	
	private final Set<Trigger> trigger;
	private final Set<Response> response;
	
	private InputFunction inputFunction;

	private String label;
	
	/**
	 * Generates a new TriggerContext
	 * 
	 */
	public TriggerContext() {
		trigger = new HashSet<Trigger>();
		response = new HashSet<Response>();
		
		inputFunction = InputFunction.OR;
	}

	/**
	 * @return the inputFunction
	 */
	public InputFunction getInputFunction() {
		return inputFunction;
	}

	/**
	 * @param inputFunction the inputFunction to set
	 */
	public void setInputFunction(final InputFunction inputFunction) {
		this.inputFunction = inputFunction;
	}
	
	/**
	 * toggle (AND / OR) input function
	 */
	public void toggleInputFunction() {
		if(inputFunction == InputFunction.OR) {
			inputFunction = InputFunction.AND;
		} else {
			inputFunction = InputFunction.OR;
		}
	}
	
	/**
	 * @return true if the TriggerContext has a trigger and a response
	 */
	public boolean isComplete() {
		return !trigger.isEmpty() && !response.isEmpty();
	}

	public Collection<Trigger> getTriggers() {
		return trigger;
	}

	public Collection<Response> getResponses() {
		return response;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(final String label) {
		this.label = label;
	}
	
}
