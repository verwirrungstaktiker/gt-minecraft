package gt.general;

import static com.google.common.collect.Maps.*;
import static com.google.common.collect.Sets.*;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.trigger.persistance.YamlSerializable;
import gt.general.world.WorldInstance;
import gt.plugin.Hello;
import gt.plugin.helloeditor.HelloEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;

public class Spawn extends YamlSerializable {
	
	private static SpawnBlock spawnBlock = null;
	
	private final Set<Block> spawnBlocks;
	
	public Spawn(final Plugin plugin) {
		if(spawnBlock == null) {
			spawnBlock = new SpawnBlock(plugin);
		}
		spawnBlocks = newHashSet();
	}
	
	public class SpawnBlock extends GenericCubeCustomBlock{
	
		public static final String TEXTURE = "http://img27.imageshack.us/img27/4669/spawnpv.png";  
		
		private SpawnBlock(final Plugin plugin) {
			super(plugin, "spawn", SpawnBlock.TEXTURE, 16);
			SpoutManager.getFileManager().addToPreLoginCache(plugin, TEXTURE);
		}
		
		public void onBlockPlace(World world, int x, int y, int z, final LivingEntity living) {
			System.out.println(living.toString());
			getSpawn(world).addBlock(world.getBlockAt(x, y, z));
		}
		
		public void onBlockDestroyed(World world, int x, int y, int z) {
			getSpawn(world).removeBlock(world.getBlockAt(x, y, z));
		}
		
		private Spawn getSpawn(final World world) {
			if(Hello.getPlugin() instanceof HelloEditor) {
				WorldInstance worldInstance = HelloEditor.getPlugin().getWorldInstance();
				
				if(worldInstance.getWorld().equals(world)) {
					return worldInstance.getSpawn();
				}
			}
			
			throw new RuntimeException("Illegal spawn modification!");
		}
	}
	
	public void addBlock(Block block) {
		spawnBlocks.add(block);
	}

	public void removeBlock(Block block) {
		spawnBlocks.remove(block);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setup(Map<String, Object> values, World world) {
		List<Map<String, Object>> blocks = (List<Map<String, Object>>) values.get(SpawnPersistance.KEY_SPAWN);
		
		for(Map<String, Object> coords : blocks) {
			Block block = blockFromCoordinates(coords, world);
			
			SpoutManager.getMaterialManager().overrideBlock(block, spawnBlock);
			
			addBlock(block);
		}
	}

	@Override
	public Map<String, Object> dump() {
		List<Object> blocks = new ArrayList<Object>();
		
		for(Block b : getBlocks()) {
			blocks.add(coordinatesFromBlock(b));
		}
		
		HashMap<String, Object> ret = newHashMap();
		ret.put(SpawnPersistance.KEY_SPAWN, blocks);
		return ret;
	}

	@Override
	public void dispose() {
		for(Block block : getBlocks()) {
			block.setType(Material.AIR);
		}
	}

	@Override
	public Set<Block> getBlocks() {
		return spawnBlocks;
	}

	/**
	 * @return the spawnBlock
	 */
	public static SpawnBlock getSpawnBlock() {
		return spawnBlock;
	}

	public void spawnTeam(final Team team) {
		Iterator<Block> blocks = spawnBlocks.iterator();
		Iterator<Hero> heros = team.getPlayers().iterator();
		
		while(blocks.hasNext() && heros.hasNext()) {
			Location location = blocks.next().getLocation();
			location.add(0.5, 0, 0.5); // center
			
			heros.next().getPlayer().teleport(location);
		}

		
		
		// TODO Auto-generated method stub
		
	}
}