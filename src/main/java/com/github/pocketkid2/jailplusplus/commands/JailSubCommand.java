package com.github.pocketkid2.jailplusplus.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.JailObject;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class JailSubCommand extends AbstractSubCommand {

	public JailSubCommand(JailBaseCommand jc, JailPlugin pl) {
		super(jc, pl);
		description = "Jail or unjail a player";
		usage = "/jpp jail <player> <jailname> [amount] [reason]";
		permission = "jailplusplus.command.jail";
		aliases = new String[] { "jail", "unjail" };
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		// Check for first argument
		if (args.length < 2) {
			sender.sendMessage(Messages.NOT_ENOUGH_ARGUMENTS);
			return true;
		}

		// Create the player object and get from argument
		Player player = Bukkit.getPlayer(args[1]);
		if (player == null) {
			sender.sendMessage(Messages.INVALID_PLAYERNAME);
			return true;
		}

		// Check if player is self
		if (sender.equals(player)) {
			sender.sendMessage(Messages.CANT_JAIL_SELF);
			return true;
		}

		// Check if this player has the special permission
		if (player.hasPermission("jailplusplus.prevent.jail")) {
			sender.sendMessage(Messages.CANT_JAIL_PLAYER);
			return true;
		}

		// Check if this player is jailed
		if (plugin.isPlayerInJail(player)) {
			unjail(sender, player);
			return true;
		} else {
			return jail(sender, player, args);
		}
	}

	private boolean jail(CommandSender sender, Player player, String[] args) {
		// If we don't have the required arguments, return
		if (args.length < 3) {
			sender.sendMessage(Messages.NOT_ENOUGH_ARGUMENTS);
			return false;
		}

		// Create the jailname object and get from argument
		String jailname = args[2];
		if (!plugin.getJails().containsKey(jailname)) {
			sender.sendMessage(Messages.INVALID_JAILNAME);
			return true;
		}

		// Create the bail amount and begin with default. If argument has one,
		// then set it to that
		double bailAmount = plugin.getConfig().getDouble("default-values.bail-amount");
		if (args.length > 3) {
			bailAmount = Double.parseDouble(args[3]);
		}

		// Create the reason with default. If arguments have one, then set it to
		// that
		String reason = plugin.getConfig().getString("default-values.jail-reason");
		if (args.length > 4) {
			reason = String.join(" ", Arrays.copyOfRange(args, 4, args.length));
		}

		// Create object and store data
		JailObject object = new JailObject(jailname, sender.getName(), bailAmount, reason, player.getLocation());
		plugin.addPlayerObject(object, player);

		// Get jail and teleport them there
		player.teleport(plugin.getJailLocation(jailname));

		// Notify player and sender
		sender.sendMessage(Messages.PLAYER_WAS_JAILED);
		player.sendMessage(Messages.YOU_WERE_JAILED);

		return true;
	}

	private void unjail(CommandSender sender, Player player) {
		// Get jail object and teleport them back
		JailObject object = plugin.getPlayerObject(player);
		player.teleport(object.getPreviousLocation());

		// Remove object
		plugin.removePlayerObject(player);

		// Notify player and sender
		sender.sendMessage(Messages.PLAYER_WAS_UNJAILED);
		player.sendMessage(Messages.YOU_WERE_UNJAILED);
	}

}
