package com.github.pocketkid2.jailplusplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.JailObject;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class SwapSubCommand extends AbstractSubCommand {

	public SwapSubCommand(JailBaseCommand jc, JailPlugin pl) {
		super(jc, pl);
		description = "You can take someone else's place in jail!";
		usage = "/jpp swap <player>";
		permission = "jailplusplus.command.swap";
		aliases = new String[] { "swap" };
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		// Check for player
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
			return true;
		}

		// Check for argument
		if (args.length < 2) {
			sender.sendMessage(Messages.NOT_ENOUGH_ARGUMENTS);
			return true;
		}

		// Get target
		Player target = Bukkit.getPlayer(args[1]);
		if (target == null) {
			sender.sendMessage(Messages.INVALID_PLAYERNAME);
			return true;
		}

		// Check if the player is self
		if (sender.equals(target)) {
			sender.sendMessage(Messages.CANT_SWAP_SELF);
			return true;
		}

		// Check if player has the special permission
		if (target.hasPermission("jailplusplus.prevent.swap")) {
			sender.sendMessage(Messages.CANT_SWAP_PLAYER);
			return true;
		}

		swapOut((Player) sender, target);

		return true;
	}

	private void swapOut(Player sender, Player target) {
		// Get the object
		JailObject object = plugin.getPlayerObject(target);
		// Teleport the target out
		target.teleport(object.getPreviousLocation());
		// Remove the object
		plugin.removePlayerObject(target);
		// Teleport the sender into the jail
		sender.teleport(plugin.getJailLocation(object.getJailName()));
		// Create the new object
		plugin.addPlayerObject(object, sender);
		// Notify the sender and target
		sender.sendMessage(Messages.YOU_WERE_SWAPPED);
		target.sendMessage(Messages.YOU_WERE_SWAPPED);
	}
}
