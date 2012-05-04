package gt.general;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class GeneralTest {
	
	// Test of Inventory Mechanics
	@Test
	public void InventoryTest() {
		//Initialization
		Item item1 = new Item();
		item1.dropable = false;
		
		Item item2 = new Item();
		item2.dropable = true;
		
		Inventory inventory = new Inventory();
		
		inventory.setActiveItem(item2);
		Assert.assertEquals(item2, inventory.getActiveItem());
		Assert.assertEquals(true, inventory.activeItemDropable());
		
		inventory.setActiveItem(item1);
		Assert.assertEquals(item1, inventory.getActiveItem());
		Assert.assertEquals(false, inventory.activeItemDropable());
	}
	

	//Test of remaining Player Mechanics
	@Test
	public void newPlayerTest() {
		// Create player and see if he has an inventory
		
		Player player = new Player();
		assertNotNull(player.inventory);
		assertEquals(100, player.defaultSpeed, 0);
		
		//ToDo: Aura Test
	}

}
