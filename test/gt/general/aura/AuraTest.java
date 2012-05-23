package gt.general.aura;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class AuraTest {
	private Aura aura;
	private EffectFactory efMock;
	private GnomeCarrierEffect effect;
	
	@Before
	public void setup() {
		effect = new GnomeCarrierEffect();
		efMock = mock(EffectFactory.class);
		when(efMock.getEffect()).thenReturn(effect);
		aura = new Aura(efMock, 3, Aura.INFINITE_DURATION, Aura.EACH_TICK);
	}

	@Test
	public void test() {
		// TODO implement this?
		fail("Not yet implemented");
	}

}
