package gt.general.trigger;

import static org.junit.Assert.*;

import org.bukkit.Location;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 * Class to Test Area Trigger
 */
public class AreaTriggerTest {
	TriggerManager tm, tmMock;
	AreaTrigger trigger1, trigger2;
	Location loc1, loc2, loc3, loc4;
	Location[] cube;

	/**
	 * Setup
	 */
	@Before
	public void setup() {
		World mockWorld = mock(World.class);
		loc1 = new Location(mockWorld, 0.0, 0.0, 0.0);
		loc2 = new Location(mockWorld, 2.0, 2.0, 2.0);
		loc3 = new Location(mockWorld, 1.0, 1.0, 1.0);
		loc4 = new Location(mockWorld, 3.0, 3.0, 3.0);

		cube = new Location[]{loc1, loc2};
		tm = new TriggerManager();
		tmMock = mock(TriggerManager.class);
	}

	/**
	 * checks the success of the Area Trigger
	 */
	@Test
	public void checkTriggerSuccess() {
		trigger1 = new AreaTrigger(cube, loc3, false, tmMock, tm);

		assertEquals(1, tm.getTriggers().size());
		trigger1.checkTrigger();	//trigger is deregistered here
		assertEquals(0, tm.getTriggers().size());
	}

	/**
	 * checks fail of Area Trigger
	 */
	@Test
	public void checkTriggerFail() {
		trigger1 = new AreaTrigger(cube, loc4, false, tmMock, tm);
		assertEquals(1, tm.getTriggers().size());
		trigger1.checkTrigger();
		assertEquals(1, tm.getTriggers().size());
	}
}
