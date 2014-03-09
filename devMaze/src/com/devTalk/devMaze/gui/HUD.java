package com.devTalk.devMaze.gui;

import java.util.ArrayList;
import java.util.List;

public class HUD {

	List<HUDModule> modules;

	public HUD() {
		modules = new ArrayList<HUDModule>();
	}
	
	public void addModule(HUDModule module) {
		modules.add(module);
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
		for (HUDModule module : modules)
			module.dispose();
	}

	public void render() {
		for (HUDModule module : modules)
			module.render();
	}

	public void stopAction(int x, int y) {
		for (HUDModule module : modules)
			module.stopAction(x, y);
	}
}
