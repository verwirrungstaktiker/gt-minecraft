package gt.general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import junit.framework.Assert;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

public class GeneralTest {
	
	// Test of Inventory Mechanics
	@Test
	public void InventoryTest() {
		//Initialization
		Item item1 = new Item(mock(ItemStack.class));
		item1.setDropable(false);
		
		Item item2 = new Item(mock(ItemStack.class));
		item2.setTool(true);
		item2.setDropable(true);
		
		Inventory inventory = new Inventory();
		
		inventory.setActiveItem(item2);
		Assert.assertEquals(item2, inventory.getActiveItem());
		Assert.assertEquals(true, inventory.activeItemDropable());
		
		inventory.setActiveItem(item1);
		Assert.assertEquals(item1, inventory.getActiveItem());
		//TODO: Item Swap is not possible in current version
		Assert.assertEquals(item2, inventory.getPassivItem());
		Assert.assertEquals(false, inventory.activeItemDropable());
		
	}
	

	//Test of remaining Hero Mechanics
	@Test
	public void newHeroTest() {
		// Create hero and see if he has an inventory
		
		Player mockPlayer = mock(Player.class);
		
		Hero hero = new Hero(mockPlayer);
		assertNotNull(hero.getInventory());
		assertEquals(100, hero.defaultSpeed, 0);
		
		//TODO Aura Test
	}

}
