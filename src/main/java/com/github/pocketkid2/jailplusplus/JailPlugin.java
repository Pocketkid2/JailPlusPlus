package com.github.pocketkid2.jailplusplus;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.pocketkid2.jailplusplus.commands.JailBaseCommand;
import com.github.pocketkid2.jailplusplus.listeners.JailListener;
import com.github.pocketkid2.jailplusplus.utils.ConfigAccessor;
import com.github.pocketkid2.jailplusplus.utils.CooldownManager;
import com.github.pocketkid2.jailplusplus.utils.JailObject;

import net.milkbowl.vault.economy.Economy;

public class JailPlugin extends JavaPlugin {

	private ConfigAccessor jails;
	private ConfigAccessor players;

	public static Economy economy = null;

	public CooldownManager cm;

	public Metrics metrics;

	@Override
	public void onEnable() {
		// Create new file accessors
		jails = new ConfigAccessor(this, "jails.yml");
		players = new ConfigAccessor(this, "players.yml");

		// Save default configs if they don't exist
		saveDefaultConfig();
		jails.saveDefaultConfig();
		players.saveDefaultConfig();

		// Register Commands
		getCommand("jailplusplus").setExecutor(new JailBaseCommand(this));

		// Register Listener
		getServer().getPluginManager().registerEvents(new JailListener(this), this);

		// Register Configuration Serializable Object
		ConfigurationSerialization.registerClass(JailObject.class);

		// Setup economy if Vault is found & enabled
		if (!setupEconomy()) {
			getLogger().severe("Couldn't find or hook into Vault! Do you have Vault and an economy plugin installed?");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		// Create a new cooldown manager
		cm = new CooldownManager();

		// Initialize bstats metrics
		metrics = new Metrics(this);

		// Log status
		getLogger().info("Done!");
	}

	@Override
	public void onDisable() {

		// Save configs to file
		jails.saveConfig();
		players.saveConfig();

		// Log status
		getLogger().info("Done!");
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Location> getJails() {
		return (Map) jails.getConfig().getConfigurationSection("jails").getValues(false);
	}

	public void setJails(Map<String, Location> map) {
		jails.getConfig().createSection("jails", map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, JailObject> getPlayers() {
		return (Map) players.getConfig().getConfigurationSection("players").getValues(false);
	}

	public void setPlayers(Map<String, JailObject> map) {
		players.getConfig().createSection("players", map);
	}

	public Location getJailLocation(String name) {
		if (getJails().containsKey(name)) {
			return getJails().get(name);
		} else {
			return null;
		}
	}

	public void setJailLocation(Location loc, String name) {
		Map<String, Location> map = getJails();
		map.put(name, loc);
		setJails(map);
	}

	public Set<String> listJails() {
		return getJails().keySet();
	}

	public JailObject getPlayerObject(OfflinePlayer player) {
		if (getPlayers().containsKey(player.getUniqueId().toString())) {
			return getPlayers().get(player.getUniqueId().toString());
		} else {
			return null;
		}
	}

	public void addPlayerObject(JailObject object, OfflinePlayer player) {
		Map<String, JailObject> map = getPlayers();
		map.put(player.getUniqueId().toString(), object);
		setPlayers(map);
	}

	public void removePlayerObject(OfflinePlayer player) {
		Map<String, JailObject> map = getPlayers();
		map.remove(player.getUniqueId().toString());
		setPlayers(map);
	}

	public Set<String> listPlayers() {
		Set<String> names = new HashSet<String>();
		for (String uuid : getPlayers().keySet()) {
			OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
			names.add(player.getName());
		}
		return names;
	}

	public boolean isPlayerInJail(OfflinePlayer player) {
		if (getPlayers().containsKey(player.getUniqueId().toString())) {
			return true;
		} else {
			return false;
		}
	}

	public OfflinePlayer lookup(String name) {
		for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		return null;
	}
}
