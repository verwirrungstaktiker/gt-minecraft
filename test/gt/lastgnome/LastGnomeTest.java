package gt.lastgnome;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import gt.BaseTest;
import gt.general.Hero;
import gt.general.PortableItem;
import gt.general.Team;

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
public class LastGnomeTest extends BaseTest {
	private Player mockPlayer1, mockPlayer2;
	private Hero hero1, hero2;

	private Team team;
	private LastGnomeGame game;

	/**
	 * Setup
	 */
	@Before
	public void setup() {
		mockPlayer1 = mock(Player.class);
		mockPlayer2 = mock(Player.class);

		hero1 = new Hero(mockPlayer1);
		hero2 = new Hero(mockPlayer2);

		Hero[] heroes = {hero1,hero2};

		team = new Team(heroes);
		game = new LastGnomeGame(team);
	}

	/**
	 * Test for giving and taking Gnome
	 */
	@Test
	public void simpleGnomeSwitchingTest() {
		//Set (and get) GnomeBearer of the Team TODO gnomeBearer should be set automatically later
		game.setGnomeBearer(hero1);
		assertEquals(hero1,game.getGnomeBearer());

		game.giveGnomeTo(hero2);
		assertEquals(hero2,game.getGnomeBearer());
	}

	@Test
	public void complexGnomeSwitchingTest(){
		/* TODO check if this is still valid
		//Now we give hero2 a Tool-Item and see if it works then
		PortableItem item1 = new PortableItem(mock(ItemStack.class));
		item1.setTool(true);
		assertTrue(hero2.getInventory().setActiveItem(item1));
		assertTrue(hero2.takeGnomeFrom(hero1));
		assertFalse(hero1.isGnomeBearer());
		assertTrue(hero2.isGnomeBearer());
		assertEquals(hero2,team.getGnomeBearer());

		//Now we give hero1 a dropable Item
		PortableItem item2 = new PortableItem(mock(ItemStack.class));
		item2.setDropable(true);
		assertTrue(hero1.getInventory().setActiveItem(item2));
		assertTrue(hero2.giveGnomeTo(hero1));
		assertTrue(hero1.isGnomeBearer());
		assertFalse(hero2.isGnomeBearer());
		assertEquals(hero1,team.getGnomeBearer());

		//Now we give hero2 an undropbable Item
		PortableItem item3 = new PortableItem(mock(ItemStack.class));
		item3.setTool(false);
		item3.setDropable(false);
		assertTrue(hero2.getInventory().setActiveItem(item3));
		assertFalse(hero2.takeGnomeFrom(hero1));
		assertTrue(hero1.isGnomeBearer());
		assertFalse(hero2.isGnomeBearer());
		assertEquals(hero1,team.getGnomeBearer());
		*/
	}
}
