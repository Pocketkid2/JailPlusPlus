package com.github.pocketkid2.jailplusplus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class HelpSubCommand extends AbstractSubCommand {

	public HelpSubCommand(JailBaseCommand jc, JailPlugin pl) {
		super(jc, pl);
		description = "Get information about a command, or see a list of all commands";
		usage = "/jpp help [command]";
		permission = "jailplusplus.command.help";
		aliases = new String[] { "help" };
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		// Check for next argument
		if (args.length > 1) {
			// Look for that subcommand
			for (AbstractSubCommand sub : jailCommand.getSubCommands()) {
				for (String s : sub.getAliases()) {
					if (s.equalsIgnoreCase(args[1])) {
						// We found it!

						// Permission check
						if (sender.hasPermission(sub.getPermission())) {
							sender.sendMessage(ChatColor.AQUA + "----- Command: " + sub.getAliases()[0] + " -----");
							sender.sendMessage(ChatColor.AQUA + sub.getDescription());
							sender.sendMessage(ChatColor.AQUA + sub.getUsage());
							return true;
						} else {
							sender.sendMessage(Messages.NO_PERM);
							return true;
						}
					}
				}
			}

			// We didn't find it
			sender.sendMessage(Messages.INVALID_COMMAND);
			return false;
		} else {
			// Send all commands
			List<String> commands = new ArrayList<String>();
			commands.add(0, ChatColor.AQUA + "----- JailPlusPlus Commands -----");
			for (AbstractSubCommand sub : jailCommand.getSubCommands()) {
				// Permission check
				if (sender.hasPermission(sub.getPermission())) {
					commands.add(ChatColor.AQUA + sub.getUsage());
				}
			}
			if (commands.isEmpty()) {
				commands.add(Messages.NO_PERM);
			}
			sender.sendMessage(commands.toArray(new String[commands.size()]));

		}

		// We did it right
		return true;
	}

}
