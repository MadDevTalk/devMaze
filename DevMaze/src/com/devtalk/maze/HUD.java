package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

public class HUD {
	
	List<HUDModule> modules;
	
	public HUD(DevMaze g) {
		this.modules = new ArrayList<HUDModule>();
		this.modules.add(new PauseModule(g));
		this.modules.add(new AButtonModule(g));
		this.modules.add(new BButtonModule(g));
	}
	
	public void render() {
		for (HUDModule module : modules)
			module.render();
	}
	
	public boolean actionedAt(int x, int y) {
		for (HUDModule module : this.modules)
			if (module.rectangle().contains(x, y)) {
				module.action();
				return true;
			}
		
		return false;
	}
}
