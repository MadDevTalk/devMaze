package com.devTalk.devMaze.gui;

import java.util.ArrayList;
import java.util.List;

import com.devTalk.devMaze.maze.DevMaze;

public class HUD {

	List<HUDModule> modules;

	public HUD(DevMaze g) {
		this.modules = new ArrayList<HUDModule>();
		this.modules.add(new PauseModule(g));
		this.modules.add(new DirectionalModule(g));
	}

	public boolean actionedAt(int x, int y) {
		for (HUDModule module : modules)
			if (module.actionedAt(x, y))
				return true;

		return false;
	}
	
	public boolean draggedAt(int x, int y) {
		for (HUDModule module : modules)
			if (module.draggedAt(x, y))
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
