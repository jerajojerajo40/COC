package me.jerajo.coc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class COCEventClass implements Listener {

	@EventHandler
	public void onFirstJoin(PlayerJoinEvent e) {
		Player joiner = e.getPlayer();
		if(!joiner.hasPlayedBefore()) {
			joiner.sendMessage("hello");
		}
	}
}
