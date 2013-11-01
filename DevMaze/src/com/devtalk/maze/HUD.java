package com.devtalk.maze;

import java.util.ArrayList;
import java.util.List;

public class HUD {
	
	List<HUDModule> modules;
	
	public HUD(DevMaze g) {
		this.modules = new ArrayList<HUDModule>();
		this.modules.add(new PauseModule(g));
	}
	
	public void render() {
		for (HUDModule module : modules)
			module.render();
	}
}
