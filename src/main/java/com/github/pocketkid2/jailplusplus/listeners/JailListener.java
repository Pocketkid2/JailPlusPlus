package com.github.pocketkid2.jailplusplus.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.github.pocketkid2.jailplusplus.JailPlugin;

public class JailListener implements Listener {

	private JailPlugin plugin;

	public JailListener(JailPlugin pl) {
		plugin = pl;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (plugin.isPlayerInJail(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (plugin.isPlayerInJail(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (plugin.isPlayerInJail(event.getPlayer())) {
			event.setRespawnLocation(plugin.getJailLocation(plugin.getPlayerObject(event.getPlayer()).getJailName()));
		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (plugin.isPlayerInJail(event.getPlayer())) {
			event.setCancelled(true);
		}
	}
}
