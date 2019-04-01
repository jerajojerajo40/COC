package WizardHandler;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandHandler implements Listener {

	public static void giveWand(Player player) {
		
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName());
		lore.add(ChatColor.AQUA + "level: " + Integer.toString(WizardHandler.getWandLevel(player)));

		ItemStack wand = new ItemStack(Material.GOLDEN_HOE);
		ItemMeta wandMeta = wand.getItemMeta();
		wandMeta.setDisplayName(ChatColor.MAGIC + "ww " + ChatColor.RESET + ChatColor.AQUA + "Gravity" + ChatColor.RESET + ChatColor.MAGIC + " ww" );
		wandMeta.setLore(lore);
		wand.setItemMeta(wandMeta);
		player.getInventory().addItem(wand);

	}

}
