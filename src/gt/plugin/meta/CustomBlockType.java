/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
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
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/signal-green.png",
					16);
		}
	}),

	/** used to show the state of a LeverRedstoneTrigger */
	RED_SIGNAL(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("red_signal",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/signal-red.png", 16);
		}
	}),

	/** used in the QuestionTrigger */
	QUESTION_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("question_block",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/question.png",
					16);
		}
	}),

	/** used to build invisible obstacles */
	INVISIBLE_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			ObservableCustomBlock block = new ObservableCustomBlock(
					"invisibleBlock",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/invisible.png",
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
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/gnome_socket_end.png",
					16);
		}
	}),

	/** ends a gnome game */
	GNOME_START_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock(
					"start_socket",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/gnome_socket_start.png",
					16);

		}
	}),
	
    GNOME_TRIGGER_NEGATIVE(new CustomBlockInstatiator() {
        @Override
        public ObservableCustomBlock instantiate() {
            return new ObservableCustomBlock(
                    "gnome_trigger_negative",
                    "https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/gnome_socket_end.png",
                    16);

        }
    }),
    
    GNOME_TRIGGER_POSITIVE(new CustomBlockInstatiator() {
        @Override
        public ObservableCustomBlock instantiate() {
            return new ObservableCustomBlock(
                    "gnome_trigger_positive",
                    "https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/gnome_positive.png",
                    16);

        }
    }),

    GNOME_STORAGE_NEGATIVE(new CustomBlockInstatiator() {
        @Override
        public ObservableCustomBlock instantiate() {
            return new ObservableCustomBlock(
                    "gnome_storage_negative",
                    "https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/sleep_gnome_negative.png",
                    16);

        }
    }),
    
    GNOME_STORAGE_POSITIVE(new CustomBlockInstatiator() {
        @Override
        public ObservableCustomBlock instantiate() {
            return new ObservableCustomBlock(
                    "gnome_storage_positive",
                    "https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/sleep_gnome_positive.png",
                    16);

        }
    }),    
    
	/** spawns players */
	SPAWN_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("spawn",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/spawn.png", 16);

		}
	}),

	/** detects entities walking on it */
	STEP_ON_TRIGGER(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("tele_floor",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/teleporter-floor.png", 16);

		}
	}),

	/** detects entities walking on it, is disabled */
	STEP_ON_TRIGGER_DISABLED(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("tele_floor_disabled",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/teleporter-floor_disabled.png", 16);

		}
	}),
	
	/** spawns zombies */
	ZOMBIESPAWN_BLOCK(new CustomBlockInstatiator() {
		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("zombiespawn",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/spawn.png", 16);

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
		
	}),
	
	/** gives the blocktool */
	BLOCKTOOL_DISPENSER(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("blocktool_dispenser",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/block_tool_block.png", 16);
		}
		
	}),
	
	/** blue lock */
	BLUE_LOCK(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("blue_lock",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/lock_blue.png", 16);
		}
		
	}),
	
	/** red lock */
	RED_LOCK(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("red_lock",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/lock_red.png", 16);
		}
		
	}),
	
	/** green lock */
	GREEN_LOCK(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("green_lock",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/lock_green.png", 16);
		}
		
	}),
	
	/** yellow lock */
	YELLOW_LOCK(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("yellow_lock",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/lock_yellow.png", 16);
		}
		
	}),
	
	/** gives the blue key */
	BLUE_KEY_DISPENSER(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("blue_key_dispenser",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/key_dispenser_blue.png", 16);
		}
		
	}),
	
	/** gives the red key */
	RED_KEY_DISPENSER(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("red_key_dispenser",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/key_dispenser_red.png", 16);
		}
		
	}),
	
	/** gives the green key */
	GREEN_KEY_DISPENSER(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("green_key_dispenser",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/key_dispenser_green.png", 16);
		}
		
	}),
	
	/** gives the yellow key */
	YELLOW_KEY_DISPENSER(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("yellow_key_dispenser",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/key_dispenser_yellow.png", 16);
		}
		
	}),
	
	/** marks respawn locations */
	RESPAWN_BLOCK(new CustomBlockInstatiator() {

		@Override
		public ObservableCustomBlock instantiate() {
			return new ObservableCustomBlock("respawn_block",
					"https://raw.github.com/verwirrungstaktiker/gt-minecraft/master/res/texture/respawn.png", 16);
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
