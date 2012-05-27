package gt.gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;
import gt.general.Hero;
import gt.general.gui.GuiElement;

import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

public class GuiElementTest {

	Hero h;
	
	@Before
	public void setup() {
		h = new Hero(mock(Player.class));
	}
	
	@Test
	public void addAndRemoveGuiElementsTest() {
		
		GuiElement ge = mock(GuiElement.class);
		
		h.getGui().addGuiElement(ge);
		assertTrue(h.getGui().hasGuiElement(ge));
		
		h.getGui().removeGuiElement(ge);
		assertFalse(h.getGui().hasGuiElement(ge));
		
	}
	
	@Test
	public void notificationTest() {
		
		GuiElement ge1 = mock(GuiElement.class);
		GuiElement ge2 = mock(GuiElement.class);
		
		// notifies ge1 and ge2
		h.getGui().addGuiElement(ge1);
		h.getGui().addGuiElement(ge2);
		
		h.notifyChanged(Hero.Notification.UNSPECIFIED);
		
		verify(ge1, times(1)).update(h, Hero.Notification.UNSPECIFIED);
		verify(ge2, times(1)).update(h, Hero.Notification.UNSPECIFIED);
		
		reset(ge1);
		reset(ge2);
		
		// notifies only ge1
		h.getGui().removeGuiElement(ge2);
		h.notifyChanged(Hero.Notification.UNSPECIFIED);
		
		verify(ge1, times(1)).update(h, Hero.Notification.UNSPECIFIED);
		verify(ge2, never()).update(h, Hero.Notification.UNSPECIFIED);
	}

}
