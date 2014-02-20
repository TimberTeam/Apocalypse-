package me.TimberTeam.Apocalypse;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Shop implements Listener, CommandExecutor {
	public Apocalypse plugin;

	public Shop(Apocalypse plugin) {
		this.plugin = plugin;
	}

	static ChatColor y = ChatColor.YELLOW;
	static ChatColor g = ChatColor.GREEN;
	static ChatColor b = ChatColor.BLUE;
	static ChatColor dg = ChatColor.DARK_GREEN;
	static ChatColor gr = ChatColor.GRAY;

	public static Inventory shop = Bukkit
			.createInventory(null, 18, y + "Shop!");

	public static void createDisplay(Material material, Inventory inv,
			int Slot, String name, String lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(lore);
		meta.setLore(Lore);
		item.setItemMeta(meta);
		inv.setItem(Slot, item);
	}

	public static void updateDisplay() {
		createDisplay(Material.BOOK, shop, 0, g + "Guns",
				"Weapons of mass murder!");
		createDisplay(Material.STONE_HOE, shop, 1, dg + "Shotgun", g
				+ "$1,000 " + gr + "Lv. 2 GUN");
		createDisplay(Material.IRON_HOE, shop, 2, dg + "AK47", g + "$1,500 "
				+ gr + "Lv. 3 GUN");
		createDisplay(Material.GOLD_HOE, shop, 3, dg + "Gold Digger", g
				+ "$5,000 " + gr + "Lv. 4 GUN");
		createDisplay(Material.DIAMOND_HOE, shop, 4, dg + "Machine Gun", g
				+ "$10,000 " + gr + "Lv. 5 GUN");
		
		createDisplay(Material.BOOK, shop, 10, g + "Special Weapons",
				"Explosive Joy!");
		createDisplay(Material.BLAZE_ROD, shop, 11, g + "Grenade Launcher",
				"Shoots Grenades!");
		
		createDisplay(Material.BOOK, shop, 20, g + "Power Ups",
				"Release your inner Beast!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("shop")) {
			Player p = (Player) sender;
			p.openInventory(shop);
			updateDisplay();
			return true;
		}
		return false;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals(shop.getName())) {
			event.setCancelled(true);//put item back
			player.closeInventory();//close menu
			int bal = plugin.getConfig().getInt(player.getName() + ".Money");
			if (clicked.getType() == Material.STONE_HOE) {
				if (bal >= 1000) {
					player.getInventory().addItem(
							new ItemStack(Material.STONE_HOE, 1));
					player.sendMessage("Purchased!");
					takeMoney(player, 1000);
				} else if (bal <= 100) {
					player.sendMessage("You are to poor for that :(");
				}
			} else if (clicked.getType() == Material.IRON_HOE) {
				if (bal >= 1500) {
					player.getInventory().addItem(
							new ItemStack(Material.IRON_HOE, 1));
					player.sendMessage("Purchased!");
				} else if (bal < 1500) {
					player.sendMessage("You are to poor for that :(");
				}
			} else if (clicked.getType() == Material.GOLD_HOE) {
				if (bal >= 5000) {
					player.getInventory().addItem(
							new ItemStack(Material.GOLD_HOE, 1));
					player.sendMessage("Purchased!");
				} else if (plugin.getConfig().getInt(
						player.getName() + ".Money") <= 5000) {
					player.sendMessage("You are to poor for that :(");
				}
			} else if (clicked.getType() == Material.DIAMOND_HOE) {
				if (bal >= 5000) {
					player.getInventory().addItem(
							new ItemStack(Material.DIAMOND_HOE, 1));
					player.sendMessage(g + "Purchased: " + b + "Machine Gun");
				} else if (bal < 5000) {
					player.sendMessage("You are to poor for that :(");
				}
			} else if (clicked.getType() == Material.POTION) {
				player.sendMessage("Grenades coming soon!");

			} else if (clicked.getType() == Material.BOOK) {
				player.sendMessage("You cant click that...");
			} else {
				player.sendMessage("Error: Invalid Selection...");
			}
		}
	}

	public void takeMoney(Player p, int i) {
		plugin.getConfig().set(p.getName() + ".Money",
				plugin.getConfig().getInt(p.getName() + ".Money", 0) - i);
		plugin.saveConfig();
	}
}
