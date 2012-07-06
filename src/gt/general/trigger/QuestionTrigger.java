package gt.general.trigger;

import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.plugin.meta.CustomBlockType;

import java.util.Map;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.getspout.spoutapi.SpoutManager;


public class QuestionTrigger extends BlockTrigger implements BlockObserver{
	
	private String question = "no question";
	private String answer = "no answer";
	
	private static final String KEY_QUESTION = "question";
	private static final String KEY_ANSWER = "answer";
	
	public QuestionTrigger(final Block block) {
		super("question", block);
		registerWithSubject();
	}
	
	public QuestionTrigger() {
		
	}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		question = (String) values.get(KEY_QUESTION);
		answer = (String) values.get(KEY_ANSWER);
		
		
		CustomBlockType.QUESTION_BLOCK.place(getBlock());
		
		registerWithSubject();
	}

	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = super.dump();
		
		map.put(KEY_QUESTION, question);
		map.put(KEY_ANSWER, answer);
		
		return map;
	}

	@Override
	public void dispose() {
		super.dispose();
		unregisterWithSubject();
	}
	
	private void  registerWithSubject() {
		ObservableCustomBlock questionBlock = CustomBlockType.QUESTION_BLOCK.getCustomBlock();
		questionBlock.addObserver(this, getBlock().getWorld());
	}
	
	private void unregisterWithSubject() {
		ObservableCustomBlock questionBlock = CustomBlockType.QUESTION_BLOCK.getCustomBlock();
		questionBlock.removeObserver(this, getBlock().getWorld());

	}

	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		System.out.println("w00t");
		
		if(blockEvent.blockEventType == BlockEventType.BLOCK_INTERACT && blockEvent.block.equals(getBlock())) {
			
			System.out.println("clicked question");
			
			Hero hero = HeroManager.getHero(blockEvent.player);
			hero.getGui().prompt(question);
			
		}
	}

}
