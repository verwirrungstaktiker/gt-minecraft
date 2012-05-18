package gt.general.aura;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

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
		fail("Not yet implemented");
	}

}
