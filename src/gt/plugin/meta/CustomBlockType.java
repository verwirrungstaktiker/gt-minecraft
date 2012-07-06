package gt.plugin.meta;

import gt.general.world.ObservableCustomBlock;

import org.getspout.spoutapi.inventory.SpoutItemStack;

/**
 * The instantiator is needed to defer the instatiation to a point in time, when
 * the plugin is active (Blocks need a plugin to be abled to instatiate).
 * 
 * @author Sebastian Fahnenschreiber
 */
public enum CustomBlockType {

	/** used to show the state of a LeverRedstoneTrigger */
	GREEN_SIGNAL(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("green_signal",
					"http://img703.imageshack.us/img703/5993/signalgreen.png",
					16);
		}
	}),

	/** used to show the state of a LeverRedstoneTrigger */
	RED_SIGNAL(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("red_signal",
					"http://img213.imageshack.us/img213/5175/signalred.png", 16);
		}
	}),

	/** used in the QuestionTrigger */
	QUESTION_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("question_block",
					"http://img546.imageshack.us/img546/8513/questioncs.png",
					16);
		}
	}),

	/** used to build invisible obstacles */
	INVISIBLE_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			ObservableCustomBlock block = new ObservableCustomBlock(
					"invisibleBlock",
					"https://dl.dropbox.com/u/29386658/gt/textures/invisible.png",
					16);
			block.setOpaque(true);
			return block;
		}
	});

	private final CustomBlockInstatiator instatiator;
	private ObservableCustomBlock blockInstance = null;

	/**
	 * @param instatiator
	 *            instatiates the block
	 */
	CustomBlockType(final CustomBlockInstatiator instatiator) {
		this.instatiator = instatiator;
	}

	/**
	 * @param <T>
	 *            expected type of the block
	 * @return the singleton block
	 */
	@SuppressWarnings("unchecked")
	public <T> T getCustomBlock() {
		return (T) blockInstance;
	}

	/**
	 * @return a new Item stack of the block
	 */
	public SpoutItemStack getItemStack() {
		return new SpoutItemStack(blockInstance);
	}

	/**
	 * instatiates all custom blocks
	 */
	static void instantiate() {
		for (CustomBlockType block : values()) {
			block.instatiate();
		}
	}

	/**
	 * instantiates this
	 */
	private void instatiate() {
		blockInstance = instatiator.instantiate();
		blockInstance.setCustomBlockType(this);
	}

}