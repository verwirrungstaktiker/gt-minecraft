package gt;

import gt.general.GeneralTest;
import gt.lastgnome.LastGnomeTest;
import gt.general.aura.AuraTest;
import gt.general.aura.GnomeCarrierEffectTest;
import gt.general.trigger.TriggerTest;
import gt.gui.GuiElementTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * runs all available tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ GeneralTest.class,
					  LastGnomeTest.class,
					  AuraTest.class,
					  GnomeCarrierEffectTest.class,
					  TriggerTest.class,
					  GuiElementTest.class})
public class AllTests {
}
