package gt.lastgnome;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import gt.general.Item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Bukkit.class)
public class LastGnomeTest {
	private Player mockPlayer1, mockPlayer2;
	private LastGnomeHero hero1, hero2;
	private final double delta = 1e-20;
	
	/**
	 * Setup
	 */
	@Before
	public void setup() {
		mockPlayer1 = mock(Player.class);
		mockPlayer2 = mock(Player.class);
		
		hero1 = new LastGnomeHero(mockPlayer1);
		hero2 = new LastGnomeHero(mockPlayer2);
		
		BukkitScheduler scheduler = mock(BukkitScheduler.class);
		PowerMockito.mockStatic(Bukkit.class);
		PowerMockito.when(Bukkit.getScheduler()).thenReturn(scheduler);
	}
	
	/**
	 * tests the  stamina Functionality
	 * XXX is this really up to date?
	 */
	@Test
	public void staminaTest() {
		//Getter and Setter of Maximal Stamina
		hero1.setMaxStamina(LastGnomeHero.DEFAULT_HERO_STAMINA);
		assertEquals(LastGnomeHero.DEFAULT_HERO_STAMINA, hero1.getMaxStamina(), delta);
		//Getter and Setter of Current Stamina
		hero1.setCurrentStamina(100);
		assertEquals(100.0, hero1.getCurrentStamina(), delta);
		//Current may never exceed Maximum
		hero1.setCurrentStamina(LastGnomeHero.DEFAULT_HERO_STAMINA + 1.0);
		
		//TODO Stamina - Speed mechanic is not implemented yet
		//If Stamina is 0, Speed should be 0 too
//		lgh.setCurrentStamina(0.0);
//		assertEquals(0.0, lgh.getCurrentSpeed(), delta);
		//After Stamina refill, Speed should get back to "normal"
//		lgh.setCurrentStamina(LastGnomeHero.DEFAULT_HERO_STAMINA);
//		assertEquals(LastGnomeHero.DEFAULT_HERO_SPEED, lgh.getCurrentSpeed(), delta);
		
	}
	
	/**
	 * Test for giving and taking Gnome
	 */
	@Test
	public void gnomeSwitchingTest() {				
		LastGnomeHero[] heroes = {hero1,hero2};
		LastGnomeTeam team = new LastGnomeTeam(heroes,null);

		Gnome gnome = new Gnome(mock(ItemStack.class));
		
		//give Gnome to hero1 and check current status
		hero1.getInventory().setActiveItem(gnome);
		assertTrue(hero1.isGnomeBearer());
		assertFalse(hero2.isGnomeBearer());
		//Set (and get) GnomeBearer of the Team TODO gnomeBearer should be set automatically later
		team.setGnomeBearer(hero1);
		assertEquals(hero1,team.getGnomeBearer());
		
		//Let hero2 try to give Gnome to hero1, which should fail, as hero2 has no Gnome
		assertFalse(hero2.giveGnomeTo(hero1));
		assertTrue(hero1.isGnomeBearer());
		assertFalse(hero2.isGnomeBearer());
		
		//Let hero1 try to take Gnome from hero2, which should fail, as hero2 has no Gnome
		assertFalse(hero1.takeGnomeFrom(hero2));
		assertTrue(hero1.isGnomeBearer());
		assertFalse(hero2.isGnomeBearer());
		
		//Let hero2 take Gnome from hero1, which should work
		assertTrue(hero2.takeGnomeFrom(hero1));
		assertFalse(hero1.isGnomeBearer());
		assertTrue(hero2.isGnomeBearer());
		assertEquals(hero2,team.getGnomeBearer());
		
		//Let hero2 give Gnome to hero1, to see if it works in reverse, which it should
		assertTrue(hero2.giveGnomeTo(hero1));
		assertTrue(hero1.isGnomeBearer());
		assertFalse(hero2.isGnomeBearer());
		assertEquals(hero1,team.getGnomeBearer());
		
		//Now we give hero2 a Tool-Item and see if it works then
		Item item1 = new Item(mock(ItemStack.class));
		item1.setTool(true);
		assertTrue(hero2.getInventory().setActiveItem(item1));
		assertTrue(hero2.takeGnomeFrom(hero1));
		assertFalse(hero1.isGnomeBearer());
		assertTrue(hero2.isGnomeBearer());
		assertEquals(hero2,team.getGnomeBearer());
		
		//Now we give hero1 a dropable Item
		Item item2 = new Item(mock(ItemStack.class));
		item2.setDropable(true);
		assertTrue(hero1.getInventory().setActiveItem(item2));
		assertTrue(hero2.giveGnomeTo(hero1));
		assertTrue(hero1.isGnomeBearer());
		assertFalse(hero2.isGnomeBearer());
		assertEquals(hero1,team.getGnomeBearer());
		
		//Now we give hero2 an undropbable Item
		Item item3 = new Item(mock(ItemStack.class));
		item3.setTool(false);
		item3.setDropable(false);
		assertTrue(hero2.getInventory().setActiveItem(item3));
		assertFalse(hero2.takeGnomeFrom(hero1));
		assertTrue(hero1.isGnomeBearer());
		assertFalse(hero2.isGnomeBearer());		
		assertEquals(hero1,team.getGnomeBearer());
	}
}
