package com.devtalk.gui;

import java.util.ArrayList;
import java.util.List;

import com.devtalk.maze.DevMaze;

public class HUD {

	List<HUDModule> modules;

	public HUD(DevMaze g) {
		this.modules = new ArrayList<HUDModule>();
		this.modules.add(new AButtonModule(g));
		this.modules.add(new BButtonModule(g));
		this.modules.add(new DPadModule(g));
		this.modules.add(new PauseModule(g));
	}

	public boolean actionedAt(int x, int y) {
		for (HUDModule module : this.modules)
			if (module.actionedAt(x, y))
				return true;

		return false;
	}

	public void dispose() {
		for (HUDModule module : this.modules)
			module.dispose();
	}

	public void render() {
		for (HUDModule module : modules)
			module.render();
	}

	public void stopAction(int x, int y) {
		for (HUDModule module : this.modules)
			module.stopAction(x, y);
	}
}
