package gt.general;

import java.awt.Point;

import junit.framework.Assert;
import org.junit.Test;

public class GeneralTest {
	
	//Testing of the Agent
	@Test
	public void AgentTest() {
		//Agent Creation
		Point point1 = new Point(3,7);
		Point point2 = new Point(3,8);
		Agent a = new Agent(point1, point2, 1);
		Assert.assertEquals(point1, a.getPosition());
		Assert.assertEquals(point2, a.getOrientation());
		Assert.assertEquals(1, a.getCurrentSpeed());
		Assert.assertEquals(1, a.getDefaultSpeed());
		
		//Agent getter and setter
		Point point3 = new Point(0,0);
		Point point4 = new Point(1,0);
		
		a.setPosition(point3);
		Assert.assertEquals(point3, a.getPosition());
		
		a.setOrientation(point4);
		Assert.assertEquals(point4, a.getOrientation());
		
		a.setCurrentSpeed(2);
		Assert.assertEquals(2, a.getCurrentSpeed());
		
		a.setDefaultSpeed(0.5f);
		Assert.assertEquals(0.5f, a.getDefaultSpeed());

	}	

	//Test class for InventoryTest
	class TestItem1 extends Item {
		public TestItem1() {
			name = "TestItem1";
			dropable = false;
		}
	}
	//Test class for InventoryTest
	class TestItem2 extends Item {
		public TestItem2() {
			name = "TestItem2";
			dropable = true;
		}
	}	
	
	// Test of Inventory Mechanics
	@Test
	public void InventoryTest() {
		//Initialization
		TestItem1 item1 = new TestItem1();
		TestItem2 item2 = new TestItem2();
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
	public void PlayerTest() {
		// Create player and see if he has an inventory
		Player player = new Player(new Point(0,0), new Point(1,1), 2);
		Assert.assertNotNull(player.inventory);
		//ToDo: Aura Test
	}

}
