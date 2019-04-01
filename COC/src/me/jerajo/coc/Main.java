package me.jerajo.coc;

import org.bukkit.Bukkit;

public class Main {

	public void onEnabled() {
		Bukkit.getServer().getConsoleSender().sendMessage("Plugin has been enabled!");
	}
	
	public void onDisabled() {
		
	}
}
