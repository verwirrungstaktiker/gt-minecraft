/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.aura;


public abstract class AbstractEffect implements Effect {

	@Override
	public int compareTo(final Effect o) {
		return Integer.valueOf(getPriorityIndex()).compareTo(Integer.valueOf(o.getPriorityIndex()));
	}
}
