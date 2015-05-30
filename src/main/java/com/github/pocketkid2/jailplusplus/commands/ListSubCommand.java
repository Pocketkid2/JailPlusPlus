package com.github.pocketkid2.jailplusplus.commands;

import org.bukkit.command.CommandSender;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class ListSubCommand extends AbstractSubCommand {

	public ListSubCommand(JailBaseCommand jc, JailPlugin pl) {
		super(jc, pl);
		description = "Lists all jails or players in the database";
		usage = "/jpp list [players]";
		permission = "jailplusplus.command.list";
		aliases = new String[] { "list" };
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		// Whether we are listing players or jails
		boolean listPlayers = false;

		// Check for argument
		if (args.length > 1) {
			// Check for jails or players argument
			if (args[1].equalsIgnoreCase("players")) {
				listPlayers = true;
			}
		}

		if (listPlayers) {
			listPlayers(sender);
		} else {
			listJails(sender);
		}

		return true;
	}

	private void listJails(CommandSender sender) {
		// Join the names
		String jails = String.join(", ", plugin.listJails());
		// If there are no jails
		if (jails == null || jails.equalsIgnoreCase("")) {
			sender.sendMessage(Messages.NO_JAILS);
		} else {
			// Send the message
			sender.sendMessage("Jail name list: " + jails);
		}
	}

	private void listPlayers(CommandSender sender) {
		// Join the names
		String players = String.join(", ", plugin.listPlayers());
		// If there are any players jailed
		if (players == null || players.equalsIgnoreCase("")) {
			sender.sendMessage(Messages.NO_PLAYERS);
		} else {
			// Send the message
			sender.sendMessage("Player name list: " + players);
		}
	}
}
