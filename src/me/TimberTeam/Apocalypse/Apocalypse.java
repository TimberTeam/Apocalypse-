package me.TimberTeam.Apocalypse;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.TimberTeam.Apocalypse.Shop;

public class Apocalypse extends JavaPlugin implements Listener {
	public void onEnable() {
		getLogger().info("Enableing Apocalypse...");
		getCommand("shop").setExecutor(new Shop(this));
		getServer().getPluginManager().registerEvents(new Shop(this), this);
		getServer().getPluginManager().registerEvents(new Currency(this), this);
		getServer().getPluginManager().registerEvents(new Guns(), this);
	}

	public void onDisable() {
		getLogger().info("Disableing Apocalypse...");
	}


}
