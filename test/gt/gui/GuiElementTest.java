package gt.gui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;
import gt.general.character.Hero;
import gt.general.gui.GuiElement;
import gt.general.gui.GuiElementType;

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
		GuiElementType get = GuiElementType.SPEEDBAR;
		
		h.getGui().addGuiElement(get, ge);
		assertTrue(h.getGui().hasGuiElement(get));
		
		h.getGui().removeGuiElement(get);
		assertFalse(h.getGui().hasGuiElement(get));		
	}

}
