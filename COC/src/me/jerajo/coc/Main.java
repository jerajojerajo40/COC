package me.jerajo.coc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public String prefix = ChatColor.GOLD + "[COC] ";

	public void onEnabled() {
		Bukkit.getServer().getConsoleSender().sendMessage(prefix + ChatColor.GREEN + "Plugin has been enabled!");
		Bukkit.getPluginManager().registerEvents(new COCEventClass(), this);
	}
	
	public void onDisabled() {
		Bukkit.getServer().getConsoleSender().sendMessage(prefix + ChatColor.RED + "Plugin has been disabled!");
	}
}
//test