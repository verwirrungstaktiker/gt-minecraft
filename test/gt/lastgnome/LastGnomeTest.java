package gt.lastgnome;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gt.BaseTest;
import gt.general.PortableItem;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.ZombieManager;
import gt.lastgnome.game.LastGnomeGame;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class LastGnomeTest extends BaseTest {
	private Player mockPlayer1, mockPlayer2;
	private Hero hero1, hero2;

	private Team team;
	private LastGnomeGame game;
	private World world;
	private ZombieManager zm;

	/**
	 * Setup
	 */
	@Before
	public void setup() {
		super.setup();		
		
		mockPlayer1 = mock(Player.class);
		mockPlayer2 = mock(Player.class);

		hero1 = new Hero(mockPlayer1);		
		hero2 = new Hero(mockPlayer2);
		
		PlayerInventory mockInventory1 = mock(PlayerInventory.class);
		when(mockPlayer1.getInventory()).thenReturn(mockInventory1);
		PlayerInventory mockInventory2 = mock(PlayerInventory.class);
		when(mockPlayer2.getInventory()).thenReturn(mockInventory2);
		
		world = mock(World.class);
		when(mockPlayer1.getWorld()).thenReturn(world);
		when(mockPlayer2.getWorld()).thenReturn(world);
		
		zm = mock(ZombieManager.class);
		
		Set<Hero> heroes = new HashSet<Hero>();
		heroes.add(hero1);
		heroes.add(hero2);

		team = new Team(heroes);
		// start game
		game = new LastGnomeGame(team, zm);
		hero1.setActiveItem(game.getGnome());
		game.setGnomeBearer(hero1);
		

	}

	/**
	 * Test for giving and taking Gnome
	 */
	@Test
	public void simpleGnomeSwitchingTest() {

		assertEquals(hero1,game.getGnomeBearer());

		game.giveGnomeTo(hero2);
		assertEquals(hero2,game.getGnomeBearer());
	}

	@Test
	public void complexGnomeSwitchingTest(){

		PortableItem item1 = new PlaceHolderTool();
		item1.setName("Item1");
		item1.setTransferable(true);
		item1.setDropable(false);
		item1.setTool(true);
		
		PortableItem item2 = new PlaceHolderTool();
		item2.setName("Item2");
		item2.setTransferable(true);
		item2.setDropable(true);
		item2.setTool(false);

		PortableItem item3 = new PlaceHolderTool();
		item3.setName("Item3");
		item3.setTransferable(true);
		item3.setDropable(false);
		item3.setTool(false);
		
		//Now we give hero2 a Tool-Item and see if it works then
		hero2.setActiveItem(item1);
		game.giveGnomeTo(hero2);
		assertEquals(hero2,game.getGnomeBearer());
		assertEquals(item1,hero2.getPassivItem());
		
		//Now we give hero1 a dropable Item
		hero1.setActiveItem(item2);
		game.giveGnomeTo(hero1);
		assertEquals(hero1,game.getGnomeBearer());
		assertEquals(null,hero1.getPassivItem());
		
		//Now we give hero2 an undropbable Item
		hero2.setActiveItem(item3);
		game.giveGnomeTo(hero2);
		assertEquals(hero1,game.getGnomeBearer());
		assertEquals(item3,hero2.getActiveItem());

	}
}
