package me.TimberTeam.Apocalypse;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Currency implements Listener {
	public Apocalypse plugin;

	public Currency(Apocalypse plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (!plugin.getConfig().contains(p.getName())) {
			plugin.getConfig().set(p.getName() + ".Money", 0);
		}
	}

	ChatColor r = ChatColor.RED;
	ChatColor dg = ChatColor.DARK_GREEN;
	ChatColor w = ChatColor.WHITE;

	String pre = r + "[" + dg + "Apocalypse" + r + "] " + w;

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if (e.getEntity() instanceof Monster) {
			Monster m = (Monster) e.getEntity();
			if (m.getKiller() instanceof Player) {
				Player p = m.getKiller();
				giveMoney(p, 20);
			}
		}

		if (e.getEntity() instanceof Villager) {
			Villager v = (Villager) e.getEntity();
			if (v.getKiller() instanceof Player) {
				Player p = v.getKiller();
				takeMoney(p, 20);
				p.sendMessage(ChatColor.RED + "You killed an innocent cilvillan)
			}
		} else if (e.getEntity() instanceof HumanEntity) {
			Player p = (Player) e.getEntity();
			if (p.getKiller() instanceof Player) {
				Player killer = p.getKiller();
				killer.sendMessage(pre + "You Killed " + p.getName()
						+ " and recived $200");
				giveMoney(killer, 20);
				takeMoney(p, 10);
			}
		}
	}

	public void giveMoney(Player p, int i) {
		plugin.getConfig().set(p.getName() + ".Money",
				plugin.getConfig().getInt(p.getName() + ".Money", 0) + i);
		plugin.saveConfig();
		p.sendMessage(pre + +i + " Money received!");
	}

	public void takeMoney(Player p, int i) {
		plugin.getConfig().set(p.getName() + ".Money",
				plugin.getConfig().getInt(p.getName() + ".Money", 0) - i);
		plugin.saveConfig();
		p.sendMessage(pre + "�c�l$" + i + " Money taken!");
	}
}
