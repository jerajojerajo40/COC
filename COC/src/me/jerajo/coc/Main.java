package me.jerajo.coc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Main {
	
	public String prefix = ChatColor.GOLD + "[COC] ";

	public void onEnabled() {
		Bukkit.getServer().getConsoleSender().sendMessage(prefix + ChatColor.GREEN + " Plugin has been enabled!");
	}
	
	public void onDisabled() {
		Bukkit.getServer().getConsoleSender().sendMessage(prefix + ChatColor.RED + " Plugin has been disabled!");
	}
}
