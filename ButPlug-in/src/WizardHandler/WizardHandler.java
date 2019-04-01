package WizardHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import ButPlugMain.MainButPlug;
import net.md_5.bungee.api.ChatColor;

public class WizardHandler implements Listener {

	static private Plugin plugin = MainButPlug.getPlugin(MainButPlug.class);
	static private Random rand = new Random();

	@SuppressWarnings("deprecation")
	public static void scoreboardHandler(Player player) {
		System.out.println("4");

		if (!checkConfigBoolean(player.getName().toString() + ".wizard.set")) {
			System.out.println("1");
			return;

		}
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Team team = board.registerNewTeam(player.getName().toString());
		team.addPlayer(player);
		team.setPrefix(ChatColor.AQUA + " Wizard ");
		Objective objective = board.registerNewObjective("Wizard", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName(ChatColor.GOLD + "Wizard Menu");
		Score wandLevel = objective.getScore(ChatColor.DARK_BLUE + "Wand Level:");
		Score tokens = objective.getScore(ChatColor.DARK_BLUE + "Tokens:");
		if (checkConfig(player.getName().toString() + ".wizard.wandlevel")) {
			wandLevel.setScore(getWandLevel(player));
			tokens.setScore(getTokens(player));
			//dit is een veranderning
			//en dit ook
		}

		else {

			wandLevel.setScore(0);
			tokens.setScore(0);
			setTokens(player, 0);
			setWandLevel(player, 0);
			giveWand(player);
		}

		player.setScoreboard(board);

	}

	private static void writeConfig(String path, String value) {

		plugin.getConfig().set(path, value);
		plugin.saveConfig();

	}

	private static void writeConfigBoolean(String path, Boolean value) {

		plugin.getConfig().set(path, value);
		plugin.saveConfig();

	}

	private static boolean checkConfigBoolean(String path) {

		return plugin.getConfig().getBoolean(path);

	}

	private static boolean checkConfig(String path) {

		return plugin.getConfig().isSet(path);

	}

	private static String readConfig(String path) {

		return (String) plugin.getConfig().get(path);

	}

	public static String setWizard(Player player) {

		if (checkConfig(player.getName().toString() + ".wizard.set")) {
			return (ChatColor.GREEN + "You already are a Wizard!");
		}

		else {
			writeConfigBoolean(player.getName().toString() + ".wizard.set", false);
			writeConfigBoolean(player.getName().toString() + ".wizard.gravity.set", false);
			writeConfigBoolean(player.getName().toString() + ".wizard.fire.set", false);
			scoreboardHandler(player);

			return (ChatColor.GREEN + "You now are a Wizard!");
		}

	}

	public static void setWandLevel(Player player, int level) {

		String path = player.getName().toString() + ".wizard.wandlevel";
		plugin.getConfig().set(path, level);
		plugin.saveConfig();

	}

	public static void setTokens(Player player, int tokens) {

		String path = player.getName().toString() + ".wizard.tokens";
		plugin.getConfig().set(path, tokens);
		plugin.saveConfig();

	}

	public static int getTokens(Player player) {

		String path = player.getName().toString() + ".wizard.tokens";
		return plugin.getConfig().getInt(path);

	}

	public static int getWandLevel(Player player) {

		String path = player.getName().toString() + ".wizard.wandlevel";
		return plugin.getConfig().getInt(path);

	}

	public static void giveWand(Player player) {

		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName());
		lore.add(ChatColor.AQUA + "level: " + Integer.toString(WizardHandler.getWandLevel(player)));

		if (checkConfigBoolean(player.getName().toString() + ".wizard.gravity.set")) {

			lore.add(org.bukkit.ChatColor.LIGHT_PURPLE + "Force");

		}
		if (checkConfigBoolean(player.getName().toString() + ".wizard.fire.set")) {

			lore.add(org.bukkit.ChatColor.LIGHT_PURPLE + "FireThrow");

		}

		ItemStack wand = new ItemStack(Material.GOLDEN_HOE);
		ItemMeta wandMeta = wand.getItemMeta();
		wandMeta.setDisplayName(ChatColor.MAGIC + "ww " + ChatColor.RESET + ChatColor.AQUA + player.getName().toString()
				+ "'s Wand" + ChatColor.RESET + ChatColor.MAGIC + " ww");
		wandMeta.setLore(lore);
		wandMeta.setUnbreakable(true);
		wand.setItemMeta(wandMeta);
		player.getInventory().addItem(wand);

	}

	public static boolean getTokenMaybe(Player player) {
		int likehood = rand.nextInt(2);
		if (likehood == 1) {
			player.sendMessage(ChatColor.GOLD + "You have found a token!");
			giveToken(player, 1);
			player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 15, 1);

			return true;
		}

		return false;

	}

	public static void giveToken(Player player, int value) {

		setTokens(player, (getTokens(player) + value));

	}

	public static void wandLevelUp(Player player, int value) {

		if (getTokens(player) >= value) {

			setTokens(player, (getTokens(player) - value));
			setWandLevel(player, (getWandLevel(player) + 1));
			player.sendMessage(ChatColor.GREEN + "You have upgraded your wand!");
			player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 15, 1);
			thunderEffect(player, 8);

		}

		else {

			player.sendMessage(ChatColor.RED + "You do not have enough tokens, get tokens by killing cows!");

		}

	}

	public static void thunderEffect(Player player, double locationValue) {

		player.getWorld().strikeLightning((player.getLocation().add(locationValue, 0, 0)));
		player.getWorld().strikeLightning((player.getLocation().add(-locationValue, 0, 0)));
		player.getWorld().strikeLightning((player.getLocation().add(0, 0, locationValue)));
		player.getWorld().strikeLightning((player.getLocation().add(0, 0, -locationValue)));

	}

	public static void powerThunder(Player player, int level) {

		List<Entity> list = new ArrayList<Entity>();
		level = level*3;
		list = player.getNearbyEntities(level, level, level);

		for (Entity ent : list) {
			if (!ent.getType().equals(EntityType.DROPPED_ITEM)) {
				ent.getLocation().getWorld().strikeLightning(ent.getLocation());
			}
		}

	}

	public static void powerTeleport(Player player, int level) {

		List<Entity> entities = new ArrayList<Entity>();
		level = level * 3;
		entities = player.getNearbyEntities(level, 2, level);
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 10, 1);
		int shiftnumber;

		for (Entity ent : entities) {

			Location entLoc = ent.getLocation();
			shiftnumber = rand.nextInt(level * 2) - level;
			entLoc.setX(entLoc.getX() + shiftnumber);
			shiftnumber = rand.nextInt(level * 2) - level;
			entLoc.setZ(entLoc.getZ() + shiftnumber);
			entLoc.setY(entLoc.getY() + 2);
			ent.teleport(entLoc);

			if (ent instanceof Player) {

				((Player) ent).playSound(entLoc, Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 10, 1);

			}

		}

	}

	public static void powerOrientation(Player player, int level) {
		
		List<Entity> entities = new ArrayList<Entity>();
		List<PotionEffect> effects = new ArrayList<PotionEffect>();
		level = level * 3;
		effects.add(new PotionEffect(PotionEffectType.BLINDNESS ,level * 20, level));
		effects.add(new PotionEffect(PotionEffectType.SLOW ,level * 20, level));
		effects.add(new PotionEffect(PotionEffectType.WEAKNESS ,level * 20, level));
		effects.add(new PotionEffect(PotionEffectType.CONFUSION ,level * 20, level));
		entities = player.getNearbyEntities(level, 2, level);
		player.playSound(player.getLocation(), Sound.ENTITY_DOLPHIN_DEATH, 10, 1);
		
		
		

		for (Entity ent : entities) {
			if (ent instanceof Player) {
				
				((Player) ent).addPotionEffects(effects);
				((Player) ent).playSound(ent.getLocation(), Sound.ENTITY_DOLPHIN_DEATH, 10, 1);

			}

		}

	}
	
	@SuppressWarnings("deprecation")
	public static void powerChecker(Player player){
		
		
		if (player.getInventory().getItemInHand().getType().equals(Material.GOLDEN_HOE)){
			
			if (checkConfigBoolean(player.getName().toString() + ".wizard.fire.set")){
				powerThunder(player, 10);
			}
			
		}
		
		
	}

}
