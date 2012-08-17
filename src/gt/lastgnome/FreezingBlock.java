package gt.lastgnome;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.SpoutBlock;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import gt.general.PortableItem;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.plugin.meta.Hello;

public class FreezingBlock extends PortableItem{
	
	private ItemStack itemStack;

	public FreezingBlock(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);

		itemStack = new SpoutItemStack(this);
		
		setTool(false);
		setDropable(false);
		setTransferable(true);
		
	}
	
	public FreezingBlock() {
		this(Hello.getPlugin(), "freezingBlock", "http://www.mariowiki.com/images/9/95/QuestionMarkBlockNSMB.png");
	}

	@Override
	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public void onAttachHero(final Hero hero) {
		hero.pause();
	}

	@Override
	public void onDetachHero(final Hero hero) {
		hero.resume();
	}
	
	@Override
	public boolean onItemInteract(final SpoutPlayer player, final SpoutBlock block, final BlockFace face) {

		if(block!=null) {
			Block relBlock = block.getRelative(face);
			relBlock.setType(Material.COBBLESTONE);
			
			HeroManager.getHero(player).removeActiveItem();
			return true;
		}
		return false;
	}

}
