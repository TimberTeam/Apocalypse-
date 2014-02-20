package me.TimberTeam.Apocalypse;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class Guns implements Listener {
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Action a = e.getAction();
		if (a.equals(Action.RIGHT_CLICK_AIR)
				|| a.equals(Action.RIGHT_CLICK_BLOCK)) {
			Player p = e.getPlayer();
			Location l = p.getLocation();
			ItemStack i = p.getItemInHand();
			if (i.equals(Material.WOOD_HOE)) {
				Snowball ball = p.getWorld().spawn(p.getEyeLocation(),
						Snowball.class);
				ball.setMetadata(
						"WOOD_HOE",
						new FixedMetadataValue(Apocalypse
								.getPlugin(Apocalypse.class), true));
				p.playNote(l, Instrument.SNARE_DRUM, Note.flat(1, Tone.A));
				ball.setShooter(p);
				ball.setVelocity(p.getLocation().getDirection().multiply(1));
			} else if (i.equals(Material.IRON_HOE)) {
				Snowball ball = p.getWorld().spawn(p.getEyeLocation(),
						Snowball.class);
				ball.setMetadata(
						"IRON_HOE",
						new FixedMetadataValue(Apocalypse
								.getPlugin(Apocalypse.class), true));
				p.playNote(l, Instrument.SNARE_DRUM, Note.flat(1, Tone.A));
				ball.setShooter(p);
				ball.setVelocity(p.getLocation().getDirection().multiply(1.3));
			} else if (i.equals(Material.GOLD_HOE)) {
				Snowball ball = p.getWorld().spawn(p.getEyeLocation(),
						Snowball.class);
				ball.setMetadata(
						"GOLD_HOE",
						new FixedMetadataValue(Apocalypse
								.getPlugin(Apocalypse.class), true));
				ball.setShooter(p);
				p.playNote(l, Instrument.SNARE_DRUM, Note.flat(1, Tone.A));
				ball.setVelocity(p.getLocation().getDirection().multiply(1.5));
			} else if (i.equals(Material.DIAMOND_HOE)) {
				Snowball ball = p.getWorld().spawn(p.getEyeLocation(),
						Snowball.class);
				ball.setMetadata("DIAMOND_HOE", new FixedMetadataValue(
						Apocalypse.getPlugin(Apocalypse.class), true));
				ball.setShooter(p);
				p.playNote(l, Instrument.SNARE_DRUM, Note.flat(1, Tone.A));
				ball.setVelocity(p.getLocation().getDirection().multiply(2));
			}
			if (i.equals(Material.BLAZE_ROD)) {
				if (i.hasItemMeta()) {// has meta data
					if (i.getItemMeta().hasLore()) {// item has lore
						Egg egg = p.getWorld().spawn(p.getEyeLocation(),
								Egg.class);
						egg.setShooter(p);
						egg.setMetadata("Explosive", new FixedMetadataValue(
								Apocalypse.getPlugin(Apocalypse.class), true));
						egg.setVelocity(p.getLocation().getDirection()
								.multiply(1));
					}
				}
			}
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile entity = (Projectile) event.getEntity();
		Entity thrower = entity.getShooter();
		Player p = (Player) entity;
		if (thrower instanceof Player) {
			if (entity instanceof Egg) {
				if (entity.hasMetadata("Explosive")) {
					double x = event.getEntity().getLocation().getX();
					double y = event.getEntity().getLocation().getY();
					double z = event.getEntity().getLocation().getZ();
					event.getEntity().getWorld()
							.createExplosion(x, y, z, 3, false, true);
				}
			}
			if (entity instanceof Snowball) {
				if (entity.hasMetadata("WOOD_HOE")) {
					p.damage(3);
				}
				if (entity.hasMetadata("IRON_HOE")) {
					p.damage(5);
				}
				if (entity.hasMetadata("GOLD_HOE")) {
					p.damage(6);
				}
				if (entity.hasMetadata("DIAMOND_HOE")) {
					p.damage(8);
				}
			}
		}
	}
}
