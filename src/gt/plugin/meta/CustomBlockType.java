package gt.plugin.meta;

import gt.general.world.ObservableCustomBlock;

import org.bukkit.block.Block;
import org.getspout.spoutapi.SpoutManager;
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
	}),

	/** starts a gnome game */
	GNOME_END_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock(
					"end_socket",
					"http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_end_16x16.png",
					16);
		}
	}),

	/** ends a gnome game */
	GNOME_START_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock(
					"start_socket",
					"http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_start_16x16.png",
					16);

		}
	}),
	
    GNOME_TRIGGER_NEGATIVE(new CustomBlockInstatiator() {
        @Override
        public ObservableCustomBlock instantiate() {
            return new ObservableCustomBlock(
                    "gnome_trigger_negative",
                    "https://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_end_16x16.png",
                    16);

        }
    }),
    
    GNOME_TRIGGER_POSITIVE(new CustomBlockInstatiator() {
        @Override
        public ObservableCustomBlock instantiate() {
            return new ObservableCustomBlock(
                    "gnome_trigger_positive",
                    "https://dl.dropbox.com/u/29386658/gt/textures/gnome_16x16.png",
                    16);

        }
    }),

	/** spawns players */
	SPAWN_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("spawn",
					"http://img27.imageshack.us/img27/4669/spawnpv.png", 16);

		}
	}),
	
	/** spawns zombies */
	ZOMBIESPAWN_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("zombiespawn",
					"http://img27.imageshack.us/img27/4669/spawnpv.png", 16);

		}
	}),
	
	/** target for teleport responses */
	TELEPORT_EXIT(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("teleport_exit",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/teleporter-exit.png", 16);
		}
		
	}),
	
	/** decorative arrow up  for teleporters*/
	TELEPORTER_UP(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("teleport_up",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/teleporter-up.png", 16);
		}
		
	}),
	
	/** decorative arrow down for teleporters*/
	TELEPORTER_DOWN(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("teleport_down",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/teleporter-down.png", 16);
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
	 * @return the singleton block
	 */
	public ObservableCustomBlock getObservableCustomBlock() {
		return blockInstance;
	}

	/**
	 * @return a new Item stack of the block
	 */
	public SpoutItemStack getItemStack() {
		return new SpoutItemStack(blockInstance);
	}

	/**
	 * @param block
	 *            the block to be replaced
	 */
	public void place(final Block block) {
		SpoutManager.getMaterialManager().overrideBlock(block, blockInstance);
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

	public abstract static class CustomBlockInstatiator {
		/**
		 * @return a newly instantiated block
		 */
		public abstract ObservableCustomBlock instantiate();
	}

}
