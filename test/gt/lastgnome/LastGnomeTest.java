package gt.lastgnome;
import gt.general.Item;

import java.awt.Point;
import static org.junit.Assert.*;
import junit.framework.Assert;
import org.junit.Test;

public class LastGnomeTest {
	//Some Testing of Stamina Mechanics
	@Test
	public void StaminaTest() {
		//Initialisation
		LastGnomePlayer lgp = new LastGnomePlayer();
		//Getter and Setter of Maximal Stamina
		lgp.setMaxStamina(200);
		Assert.assertEquals(200,lgp.getMaxStamina());
		//Getter and Setter of Current Stamina
		lgp.setCurrentStamina(200);
		Assert.assertEquals(200,lgp.getCurrentStamina());
		//Current may never exceed Maximum
		lgp.setCurrentStamina(250);		
		Assert.assertEquals(200,lgp.getCurrentStamina());
		//If Stamina is 0, Speed should be 0 too
		lgp.setCurrentStamina(0);
		Assert.assertEquals(0,lgp.getCurrentSpeed());
		//After Stamina refill, Speed should get back to "normal"
		lgp.setCurrentStamina(200);
		Assert.assertEquals(1,lgp.getCurrentSpeed());
		
	}
	
	//Test for giving and taking Gnome
	@Test
	public void GnomeSwitchingTest() {
		//Some initialization
		LastGnomePlayer player1 = new LastGnomePlayer();
		LastGnomePlayer player2 = new LastGnomePlayer();
		LastGnomePlayer[] players = {player1,player2};
		LastGnomeTeam team = new LastGnomeTeam(players,null);

		Gnome gnome = new Gnome();
		
		//give Gnome to player1 and check current status
		player1.inventory.setActiveItem(gnome);
		Assert.assertEquals(true, player1.isGnomeBearer());
		Assert.assertEquals(false, player2.isGnomeBearer());
		//Set (and get) GnomeBearer of the Team (could be obsolete)
		team.setGnomeBearer(player1);
		Assert.assertEquals(player1,team.getGnomeBearer());
		
		//Let player2 try to give Gnome to player1, which should fail, as player2 has no Gnome
		Assert.assertEquals(false, player2.giveGnomeTo(player1));
		Assert.assertEquals(true, player1.isGnomeBearer());
		Assert.assertEquals(false, player2.isGnomeBearer());
		
		//Let player1 try to take Gnome from player2, which should fail, as player2 has no Gnome
		Assert.assertEquals(false, player1.takeGnomeFrom(player2));
		Assert.assertEquals(true, player1.isGnomeBearer());
		Assert.assertEquals(false, player2.isGnomeBearer());
		
		//Let player2 take Gnome from player1, which should work
		Assert.assertEquals(true, player2.takeGnomeFrom(player1));
		Assert.assertEquals(false, player1.isGnomeBearer());
		Assert.assertEquals(true, player2.isGnomeBearer());
		Assert.assertEquals(player2,team.getGnomeBearer());
		
		//Let player2 give Gnome to player1, to see if it works in reverse, which it should
		Assert.assertEquals(true, player2.giveGnomeTo(player1));
		Assert.assertEquals(true, player1.isGnomeBearer());
		Assert.assertEquals(false, player2.isGnomeBearer());
		Assert.assertEquals(player1,team.getGnomeBearer());
		
		//Now we give player2 a Tool-Item and see if it works then
		Item item1 = new Item();
		item1.setTool(true);
		Assert.assertEquals(true,player2.inventory.setActiveItem(item1));
		Assert.assertEquals(true, player2.takeGnomeFrom(player1));
		Assert.assertEquals(false, player1.isGnomeBearer());
		Assert.assertEquals(true, player2.isGnomeBearer());
		Assert.assertEquals(player2,team.getGnomeBearer());
		
		//Now we give player1 a dropable Item
		Item item2 = new Item();
		item2.setDropable(true);
		Assert.assertEquals(true,player1.inventory.setActiveItem(item2));
		Assert.assertEquals(true, player2.giveGnomeTo(player1));
		Assert.assertEquals(true, player1.isGnomeBearer());
		Assert.assertEquals(false, player2.isGnomeBearer());
		Assert.assertEquals(player1,team.getGnomeBearer());
		
		//Now we give player2 an undropbable Item
		Item item3 = new Item();
		item3.setTool(false);
		item3.setDropable(false);
		Assert.assertEquals(true,player2.inventory.setActiveItem(item3));
		Assert.assertEquals(false, player2.takeGnomeFrom(player1));
		Assert.assertEquals(true, player1.isGnomeBearer());
		Assert.assertEquals(false, player2.isGnomeBearer());		
		Assert.assertEquals(player1,team.getGnomeBearer());
	}
}
