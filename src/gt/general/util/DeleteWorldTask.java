package gt.general.util;

import java.io.File;

public class DeleteWorldTask implements Runnable{

	private File world;
	
	
	public DeleteWorldTask(File world){
		this.world = world;
	}
	
	@Override
	public void run() {
		
		//File worldfolder = world.getWorldFolder();
		CopyUtil.deleteDirectory(world);

	}

}
