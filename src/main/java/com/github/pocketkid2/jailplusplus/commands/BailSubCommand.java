package com.github.pocketkid2.jailplusplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.JailObject;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class BailSubCommand extends AbstractSubCommand {

	public BailSubCommand(JailBaseCommand jc, JailPlugin pl) {
		super(jc, pl);
		description = "If you have enough money, you can bail yourself or someone else out of jail!";
		usage = "/jpp bail [player]";
		permission = "jailplusplus.command.bail";
		aliases = new String[] { "bail" };
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		// Check for player
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
			return true;
		}

		// Get the target
		Player target = null;
		if (args.length < 2) {
			target = (Player) sender;
		} else {
			// Check for name
			target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				sender.sendMessage(Messages.INVALID_PLAYERNAME);
				return true;
			}
		}

		// Check that the target is in jail
		if (!plugin.isPlayerInJail(target)) {
			sender.sendMessage(sender.equals(target) ? Messages.YOU_NOT_IN_JAIL : Messages.PLAYER_NOT_IN_JAIL);
			return true;
		}

		// Create the object
		JailObject object = plugin.getPlayerObject(target);

		// Check that the sender has money
		if (!JailPlugin.economy.has((Player) sender, object.getBailAmount())) {
			sender.sendMessage(Messages.NOT_ENOUGH_MONEY);
			return true;
		}

		bailOut((Player) sender, target, object);

		return true;

	}

	private void bailOut(Player sender, Player target, JailObject object) {
		// Withdraw the amount
		JailPlugin.economy.withdrawPlayer(sender, object.getBailAmount());
		// Teleport the target
		target.teleport(object.getPreviousLocation());
		// Remove object
		plugin.removePlayerObject(target);
		// Notify the sender and the target
		if (!sender.equals(target)) {
			sender.sendMessage(Messages.PLAYER_WAS_BAILED);
		}
		target.sendMessage(Messages.YOU_WERE_BAILED);
	}

}
