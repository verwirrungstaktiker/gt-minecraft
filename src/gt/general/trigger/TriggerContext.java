package gt.general.trigger;

import gt.general.character.TeamManager;

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
	 * @param tm Where the context lives.
	 */
	public TriggerContext(final TeamManager tm) {
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
	
}
