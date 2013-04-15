package org._127001.frymaster.gbp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventProcessor implements Listener {
	
	private Gbp plugin;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		boolean b = event.getPlayer().hasPermission("group.Contrib");
		if (b==true) {
			plugin.getLogger().info("Pre-permissions calculation: Player has permission");
		} else {
			plugin.getLogger().info("Pre-permissions calculation: Player has not permission");
		}
		plugin.addPermissions(event.getPlayer());
		b = event.getPlayer().hasPermission("group.Contrib");
		if (b==true) {
			plugin.getLogger().info("Post-permissions calculation: Player has permission");
		} else {
			plugin.getLogger().info("Post-permissions calculation: Player has not permission");
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.removePermissions(event.getPlayer());
	}
	
	public EventProcessor(Gbp plugin) {
		this.plugin = plugin;
		this.plugin.getLogger().info("Event processor instantiated");
	}
	

}
