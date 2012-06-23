package gt.general.trigger;

import static gt.general.trigger.persistance.TriggerManagerPersistance.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import gt.general.trigger.persistance.TriggerManagerPersistance;
import gt.general.trigger.response.DoorResponse;
import gt.general.trigger.response.Response;
import gt.plugin.listener.MultiListener;

import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.material.Door;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MultiListener.class})
public class SerializationTest {

	TriggerManagerPersistance sut;
	TriggerManager tm;
	
	
	@Before
	public void setup() {
		tm = new TriggerManager();
		sut = new TriggerManagerPersistance(tm);
		
		PowerMockito.mockStatic(MultiListener.class);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void triggerSerializes() {
		
		Trigger trigger = mock(Trigger.class);
		when(trigger.getLabel()).thenReturn("trigger_1");
		
		TriggerContext context = new TriggerContext();
		context.addTrigger(trigger);
		
		tm.addTriggerContext(context);	
				
		Map<String, Object> yaml = sut.asYaml();
		
		assertTrue(((Map<String, Object>) yaml.get(KEY_GLOBAL_CONTEXTS)).containsKey(context.getLabel()));
		assertTrue(((Map<String, Object>) yaml.get(KEY_GLOBAL_TRIGGERS)).containsKey(trigger.getLabel()));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void responseSerializes() {
		
		Response response = mock(Response.class);
		when(response.getLabel()).thenReturn("response_1");
		
		TriggerContext context = new TriggerContext();
		context.addResponse(response);
		
		tm.addTriggerContext(context);	
				
		Map<String, Object> yaml = sut.asYaml();

		assertTrue(((Map<String, Object>) yaml.get(KEY_GLOBAL_CONTEXTS)).containsKey(context.getLabel()));
		assertTrue(((Map<String, Object>) yaml.get(KEY_GLOBAL_RESPONSES)).containsKey(response.getLabel()));
		
	}
}