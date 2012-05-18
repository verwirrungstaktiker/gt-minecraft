package gt.general.aura;

import static org.junit.Assert.*;

import gt.general.Character;
import gt.general.CharacterAttributes;
import gt.general.Hero;

import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class GnomeCarrierEffectTest {
	private GnomeCarrierEffect effect;
	private Hero hero;
	private Player playerMock;
	private double delta = 1e-20;

	@Before
	public void setup() {
		playerMock = mock(Player.class);
		hero = new Hero(playerMock);
		effect = new GnomeCarrierEffect();
	}
	
	@Test
	public void initialisationTest() {
		assertEquals(Hero.DEFAULT_HERO_SPEED, hero.getDefaultSpeed(), delta);
		assertTrue(hero.getEffects().isEmpty());
		assertTrue(hero.getAuras().isEmpty());
		
		hero.applyEffects();
	}
	
	@Test
	public void effectTest() {
		hero.addEffect(effect);
		assertTrue(hero.getEffects().contains(effect));
		
		double currentSpeed = hero.getCurrentSpeed();
		while (hero.getCurrentSpeed() >= 0) {
			hero.applyEffects();
			currentSpeed -= currentSpeed*GnomeCarrierEffect.SLOWRATE;
			assertEquals(currentSpeed, hero.getCurrentSpeed(), delta);
		}
	}

}
