/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor.gui;

public interface PromptCallback {
	
	enum Action {
		SUBMIT, ABORT;
	}
	
	/**
	 * @param action what led to the close
	 * @param text what contained the textField at last
	 */
	void onClose(Action action, String text);
}
