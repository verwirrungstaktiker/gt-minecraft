package gt.general;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import gt.BaseTest;
import gt.lastgnome.GnomeItem;
import gt.plugin.helloworld.HelloWorld;
import junit.framework.Assert;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.MaterialManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpoutManager.class)
public class GeneralTest  extends BaseTest {
	
	@Before
	public void setup() {
		MaterialManager mm = mock(MaterialManager.class);
		when(mm.registerCustomItemName(any(Plugin.class), anyString())).thenReturn(0);
		
		PowerMockito.mockStatic(SpoutManager.class);
		when(SpoutManager.getMaterialManager()).thenReturn(mm);
		
		// test the mock behaviour
		assertEquals(0, SpoutManager.getMaterialManager().registerCustomItemName(HelloWorld.getPlugin(), "asdf"));
	}

	/**
	 * Tests Inventory Mechanics of hero
	 */
	@Test
	public void simpleGnoemInventoryTest() {
		
		//Initialization
		GnomeItem item1 = new GnomeItem();

		GnomeItem item2 = new GnomeItem();

		Hero hero = new Hero(mock(Player.class));

		hero.setActiveItem(item2);
		Assert.assertEquals(item2, hero.getActiveItem());
		Assert.assertEquals(true, hero.activeItemDropable());

		hero.setActiveItem(item1);
		Assert.assertEquals(item1, hero.getActiveItem());

		Assert.assertEquals(item2, hero.getPassivItem());
		Assert.assertEquals(false, hero.activeItemDropable());

	}


	//Test of remaining Hero Mechanics
	@Test
	public void newHeroTest() {
		// Create hero and see if he has an inventory

		Player mockPlayer = mock(Player.class);

		Hero hero = new Hero(mockPlayer);
		assertEquals(1, hero.getDefaultSpeed(), 0);

		//TODO Aura Test
	}

}
