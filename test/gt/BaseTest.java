package gt;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import gt.general.Hero;
import gt.plugin.helloworld.HelloWorld;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.getspout.spoutapi.Spout;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.SpoutServer;
import org.getspout.spoutapi.inventory.MaterialManager;
import org.getspout.spoutapi.material.item.GenericCustomItem;
import org.getspout.spoutapi.player.FileManager;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Basic Tests
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({SpoutManager.class, Spout.class, HelloWorld.class})
public class BaseTest {

	/**
	 * Setup
	 */
	@Before
	public void setup() {
		BukkitScheduler scheduler = mock(BukkitScheduler.class);
		PowerMockito.mockStatic(Bukkit.class);
		PowerMockito.when(Bukkit.getScheduler()).thenReturn(scheduler);
	}
	
	/**
	 * Initialization method for Tests which use Items
	 */
	protected void setupForItems() {
		MaterialManager mm = mock(MaterialManager.class);
		when(mm.registerCustomItemName(any(Plugin.class), anyString())).thenReturn(1);
		
		FileManager fm = mock(FileManager.class);
		when(fm.addToCache(any(Plugin.class), anyString())).thenReturn(true);

		PowerMockito.mockStatic(SpoutManager.class);
		when(SpoutManager.getMaterialManager()).thenReturn(mm);
		when(SpoutManager.getFileManager()).thenReturn(fm);

		//pseudo-initialize our plugin 
		PowerMockito.mockStatic(HelloWorld.class);
		HelloWorld hw = mock(HelloWorld.class);
		HelloWorld.setPlugin(hw);
		PluginDescriptionFile description = null;
		try {
			description = new PluginDescriptionFile(new FileInputStream("plugin.yml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		when(HelloWorld.getPlugin()).thenReturn(hw);
		when(hw.getDescription()).thenReturn(description);
		

		//mock Spoutserver which returns an empty Player-Array
		SpoutPlayer array[] = {};
		SpoutServer spoutserver = mock(SpoutServer.class);
		when(spoutserver.getOnlinePlayers()).thenReturn(array);
		PowerMockito.mockStatic(Spout.class);
		when(Spout.getServer()).thenReturn(spoutserver);
		
		
		// test the mock behaviour for Exceptions
		GenericCustomItem test = new GenericCustomItem(HelloWorld.getPlugin(), "asdf");
	}

}
