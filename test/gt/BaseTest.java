package gt;

import static org.mockito.Mockito.mock;
import gt.general.Hero;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.powermock.api.mockito.PowerMockito;

/**
 * Basic Tests
 */
public class BaseTest {

	/**
	 * Setup
	 */
	@Before
	public void setup() {
		BukkitScheduler scheduler = mock(BukkitScheduler.class);
		PowerMockito.mockStatic(Bukkit.class);
		PowerMockito.when(Bukkit.getScheduler()).thenReturn(scheduler);
	}

}
