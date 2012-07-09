package gt.lastgnome.game;

import static com.google.common.collect.Sets.*;
import gt.general.character.Team;
import gt.general.logic.persistance.YamlSerializable;
import gt.general.world.WorldInstance;
import gt.lastgnome.GnomeSocketEnd;
import gt.lastgnome.GnomeSocketStart;

import java.util.Set;

import org.bukkit.entity.Player;

public class EditorLastGnomeGame extends AbstractLastGnomeGame {
	
	public EditorLastGnomeGame() {
		super(new Team());
	}

	@Override
	public void onStartSocketInteract(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEndSocketInteract(Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEnd() {
		// TODO Auto-generated method stub
	}
	
	public void save() {
		WorldInstance worldInstance = getWorldInstance();
		
		worldInstance.save();
		worldInstance.saveMeta(GnomeSocketStart.FILENAME, getStartSocket());
		worldInstance.saveMeta(GnomeSocketEnd.FILENAME, getEndSocket());
	}

}
