package gt.general.trigger;

import java.util.HashSet;
import java.util.Set;

public class TriggerContext {

	public enum InputFunction {
		AND, OR
	}
	
	private final Set<Trigger> trigger;
	private final Set<Response> response;
	
	private InputFunction inputFunction;

	/**
	 * Generates a new TriggerContext
	 * 
	 */
	public TriggerContext() {
		trigger = new HashSet<Trigger>();
		response = new HashSet<Response>();
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
	 * @return true if the TriggerContext has a trigger and a response
	 */
	public boolean isComplete() {
		return !trigger.isEmpty() && !response.isEmpty();
	}
	
}
