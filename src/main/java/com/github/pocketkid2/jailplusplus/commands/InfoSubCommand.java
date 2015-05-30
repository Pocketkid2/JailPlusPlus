package com.github.pocketkid2.jailplusplus.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.JailObject;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class InfoSubCommand extends AbstractSubCommand {

	public InfoSubCommand(JailBaseCommand jc, JailPlugin pl) {
		super(jc, pl);
		description = "See jail information about you or another player";
		usage = "/jpp info [player]";
		permission = "jailplusplus.command.info";
		aliases = new String[] { "info" };
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		// Check for next argument
		if (args.length > 1) {
			// We are specifying a player
			OfflinePlayer target = plugin.lookup(args[1]);
			if (target == null) {
				sender.sendMessage(Messages.INVALID_PLAYERNAME);
				return true;
			}

			sendInfo(sender, target);
			return true;
		} else {
			// We are trying to get info on ourself, must be a player for that
			if (!(sender instanceof Player)) {
				sender.sendMessage(Messages.MUST_BE_PLAYER);
				return true;
			}

			sendInfo(sender, (OfflinePlayer) sender);
			return true;
		}
	}

	private void sendInfo(CommandSender sender, OfflinePlayer player) {
		// Check if player is in jail
		if (!plugin.isPlayerInJail(player)) {
			sender.sendMessage((sender.equals(player)) ? (Messages.YOU_NOT_IN_JAIL) : (Messages.PLAYER_NOT_IN_JAIL));
			return;
		}

		// Get object and send info
		JailObject object = plugin.getPlayerObject(player);
		sender.sendMessage(new String[] {
				"----- Info for " + player.getName() + " -----",
				"Jail name: " + object.getJailName(),
				"Jailer: " + object.getJailer(),
				"Reason: " + object.getReason(),
				"Bail amount: " + object.getBailAmount(),
		});
	}
}
