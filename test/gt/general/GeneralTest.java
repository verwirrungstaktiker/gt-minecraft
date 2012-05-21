package gt.general;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


import gt.BaseTest;
import gt.lastgnome.GnomeItem;
import junit.framework.Assert;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.SpoutManager;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

public class GeneralTest  extends BaseTest {

	@Before
	public void setup() {
		setupForItems();
	}

	/**
	 * Tests Inventory Mechanics of hero
	 */
	@Test
	public void simpleGnomeInventoryTest() {

		//Initialization
		GnomeItem item1 = new GnomeItem();

		GnomeItem item2 = new GnomeItem();

		Player player = mock(Player.class);
		Hero hero = new Hero(player);
		PlayerInventory inventory = mock(PlayerInventory.class);
		when(player.getInventory()).thenReturn(inventory);

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
