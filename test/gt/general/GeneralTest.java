package gt.general;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


import gt.BaseTest;
import gt.lastgnome.GnomeItem;
import gt.plugin.helloworld.HelloWorld;
import junit.framework.Assert;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.SpoutManager;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

public class GeneralTest  extends BaseTest {

	private PortableItem getDummyPortableItem() {
		return new PortableItem(HelloWorld.getPlugin(), "", "") {
			
			@Override
			public void onDetachHero(Hero hero) {}
			
			@Override
			public void onAttachHero(Hero hero) {}
			
			@Override
			public ItemStack getItemStack() {return null;}
		};
	}
	
	/**
	 * Tests Inventory Mechanics of hero
	 */
	@Test
	public void simpleInventoryTest() {
		
		//Initialization		
		PortableItem item1 = getDummyPortableItem();
		item1.setDropable(false);
		
		PortableItem item2 = getDummyPortableItem();
		item2.setDropable(true);
		item2.setTool(true);
		
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
	
	/**
	 * A bit more complex set of Inventory Tests
	 */
	@Test
	public void complexInventoryTest() {
		PortableItem item1 = getDummyPortableItem();
		item1.setTool(true);
		item1.setName("Item1");
		
		PortableItem item2 = getDummyPortableItem();
		item2.setDropable(true);
		item2.setName("Item2");
		
		PortableItem item3 = getDummyPortableItem();
		item3.setName("Item3");
		
		Player player = mock(Player.class);
		Hero hero = new Hero(player);
		PlayerInventory inventory = mock(PlayerInventory.class);
		when(player.getInventory()).thenReturn(inventory);
		World world = mock(World.class);
		when(player.getWorld()).thenReturn(world);
		
		hero.setActiveItem(item1);
		assertEquals(item1, hero.getActiveItem());
		
		hero.setActiveItem(item2);
		assertEquals(item2, hero.getActiveItem());
		assertEquals(item1, hero.getPassivItem());
		
		hero.setActiveItem(item3);
		assertEquals(item3, hero.getActiveItem());
		assertEquals(item1, hero.getPassivItem());
		
	}


	/**
	 * Test of remaining Hero Mechanics
	 */
	@Test
	public void newHeroTest() {
		// Create hero and see if he has an inventory

		Player mockPlayer = mock(Player.class);

		Hero hero = new Hero(mockPlayer);
		assertEquals(1, hero.getDefaultSpeed(), 0);

		//TODO Aura Test
	}


}
