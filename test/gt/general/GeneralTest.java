package gt.general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import junit.framework.Assert;

import org.bukkit.entity.Player;
import org.junit.Test;

public class GeneralTest {
	
	// Test of Inventory Mechanics
	@Test
	public void InventoryTest() {
		//Initialization
		Item item1 = new Item();
		item1.setDropable(false);
		
		Item item2 = new Item();
		item2.setTool(true);
		
		Inventory inventory = new Inventory();
		
		inventory.setActiveItem(item2);
		Assert.assertEquals(item2, inventory.getActiveItem());
		Assert.assertEquals(true, inventory.activeItemDropable());
		
		inventory.setActiveItem(item1);
		Assert.assertEquals(item1, inventory.getActiveItem());
		Assert.assertEquals(item2, inventory.getPassivItem());
		Assert.assertEquals(false, inventory.activeItemDropable());
		
	}
	

	//Test of remaining Player Mechanics
	@Test
	public void newPlayerTest() {
		// Create player and see if he has an inventory
		
		Player mockPlayer = mock(Player.class);
		
		Hero player = new Hero(mockPlayer);
		assertNotNull(player.inventory);
		assertEquals(100, player.defaultSpeed, 0);
		
		//TODO Aura Test
	}

}
