package gt.general.trigger;

import static com.google.common.collect.Maps.*;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.gui.Prompt;
import gt.general.gui.Prompt.PromptCallback;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.plugin.meta.CustomBlockType;

import java.util.Map;

import org.bukkit.World;
import org.bukkit.block.Block;


/** 
 * @author Sebastian Fahnenschreiber
 */
public class QuestionTrigger extends BlockTrigger implements BlockObserver{
	
	private String question = "no question";
	private String answer = "no answer";
	
	private boolean solved = false;
	
	private static final String KEY_QUESTION = "question";
	private static final String KEY_ANSWER = "answer";
	
	private final  Map<Hero, Prompt> openPrompts = newHashMap();
	
	/**
	 * @param block the newly placed block
	 */
	public QuestionTrigger(final Block block) {
		super("question", block);
		registerWithSubject();
	}
	
	/**
	 * required for yaml persistance
	 */
	public QuestionTrigger() {}

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
		
		unregisterFromSubject();
		closeAllPrompts();
	}

	/**
	 * hopefully self explaining
	 */
	private void closeAllPrompts() {
		for(Hero h : openPrompts.keySet()) {
			h.getGui().closePopup(openPrompts.get(h));
		}
		openPrompts.clear();
	}
	
	/**
	 * registers this trigger with the question block
	 */
	private void registerWithSubject() {
		ObservableCustomBlock questionBlock = CustomBlockType.QUESTION_BLOCK.getCustomBlock();
		questionBlock.addObserver(this, getBlock().getWorld());
	}
	
	/**
	 * unregisters this trigger from the question block
	 */
	private void unregisterFromSubject() {
		ObservableCustomBlock questionBlock = CustomBlockType.QUESTION_BLOCK.getCustomBlock();
		questionBlock.removeObserver(this, getBlock().getWorld());

	}

	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {

		if(blockEvent.blockEventType == BlockEventType.BLOCK_INTERACT && blockEvent.block.equals(getBlock()) && !solved) {
			
			final Hero hero = HeroManager.getHero(blockEvent.player);			
			Prompt prompt = new Prompt(question, new PromptCallback() {
				
				@Override
				public void onClose(final Action action, final String text) {
					openPrompts.remove(hero);
					
					if(action == Action.SUBMIT && text.equalsIgnoreCase(answer)) {
						getContext().updateTriggerState(QuestionTrigger.this, true);
						closeAllPrompts();
						solved = true;
					}
				}
			});
			
			openPrompts.put(hero, prompt);
			hero.getGui().prompt(prompt);
		}
	}
}
