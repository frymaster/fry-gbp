package org._127001.frymaster.gbp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

class EventProcessor implements Listener {
	
	private PermissionCoordinator pc;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		pc.addPermissions(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		pc.removePermissions(event.getPlayer());
	}
	
	EventProcessor(PermissionCoordinator pc) {
		this.pc = pc;
	}
	

}
