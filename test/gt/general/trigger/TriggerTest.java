package gt.general.trigger;

import static org.junit.Assert.*;
import gt.general.character.Hero;
import gt.lastgnome.LastGnomeGame;
import gt.lastgnome.TeamLostTrigger;

import javax.annotation.meta.When;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Class to Test Area Trigger
 */
public class TriggerTest {
	private TriggerManager tm;
	private Runnable runMock;
	
	private AreaTrigger areaTriggerNonRepeat, areaTriggerRepeat;
	private Location loc1, loc2, loc3, loc4;
	private Location[] cube;
	
	private TeamLostTrigger teamLostTrigger;
	private LastGnomeGame gameMock;
	private Hero gnomeBearer;
	private Player playerMock;

	/**
	 * Setup
	 */
	@Before
	public void setup() {
		World mockWorld = mock(World.class);
		loc1 = new Location(mockWorld, 0.0, 0.0, 0.0);
		loc2 = new Location(mockWorld, 2.0, 2.0, 2.0);
		loc3 = new Location(mockWorld, 1.0, 1.0, 1.0);
		loc4 = new Location(mockWorld, 3.0, 3.0, 3.0);

		cube = new Location[]{loc1, loc2};
		tm = new TriggerManager();
		
		// Mock behavior for TeamLostTest
		playerMock = mock(Player.class);
		when(playerMock.getHealth()).thenReturn(0);
		gnomeBearer = new Hero(playerMock);
		
		runMock = mock(Runnable.class);
		gameMock = mock(LastGnomeGame.class);
		when(gameMock.getGnomeBearer()).thenReturn(gnomeBearer);
	}

	/**
	 * checks the success of the Area Trigger
	 */
	@Test
	public void checkAreaTriggerSuccess() {
		//register 2 triggers
		areaTriggerNonRepeat = new AreaTrigger(cube, loc3, false, runMock, tm);
		areaTriggerRepeat = new AreaTrigger(cube, loc3, true, runMock, tm);

		assertEquals(2, tm.getTriggers().size());
		verify(runMock, times(0)).run();
		
		tm.run();	//only trigger1 is deregistered here
		assertEquals(1, tm.getTriggers().size());
		assertFalse(tm.getTriggers().contains(areaTriggerNonRepeat));
		verify(runMock, times(2)).run();
	}

	/**
	 * checks fail of Area Trigger
	 */
	@Test
	public void checkAreaTriggerFail() {
		//register 2 triggers
		areaTriggerNonRepeat = new AreaTrigger(cube, loc4, false, runMock, tm);
		areaTriggerRepeat = new AreaTrigger(cube, loc4, true, runMock, tm);
		
		assertEquals(2, tm.getTriggers().size());
		tm.run();
		assertEquals(2, tm.getTriggers().size());
		verify(runMock, times(0)).run();
	}
	
	/**
	 * checks Success of TeamLostTrigger
	 */
	@Test
	public void checkTeamListTriggerSuccess() {
		teamLostTrigger = new TeamLostTrigger(gameMock, runMock, tm);
		
		assertTrue(tm.getTriggers().contains(teamLostTrigger));
		//test if the mock is working
		assertEquals(0, gameMock.getGnomeBearer().getPlayer().getHealth());
		
		tm.run();

		assertTrue(tm.getTriggers().isEmpty());
		assertEquals(0, tm.getTriggers().size());
		
	}
	
	/**
	 * checks fail of TeamLostTrigger
	 * with an empty team
	 */
	@Test
	public void checkTeamListTriggerFail() {
		LastGnomeGame gameEmpty = mock(LastGnomeGame.class);
		teamLostTrigger = new TeamLostTrigger(gameEmpty, runMock, tm);
		
		assertEquals(1, tm.getTriggers().size());
		tm.run();
		assertEquals(1, tm.getTriggers().size());
		verify(runMock, times(0)).run();
		
	}
}
