package gt;

import gt.general.GeneralTest;
import gt.lastgnome.LastGnomeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { GeneralTest.class, LastGnomeTest.class })
public class AllTests {
}