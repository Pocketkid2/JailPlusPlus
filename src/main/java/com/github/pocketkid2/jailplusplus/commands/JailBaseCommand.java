package com.github.pocketkid2.jailplusplus.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class JailBaseCommand implements CommandExecutor {

	private JailPlugin plugin;

	private List<AbstractSubCommand> subCommands;

	public List<AbstractSubCommand> getSubCommands() {
		return subCommands;
	}

	// Register all subcommands here
	public JailBaseCommand(JailPlugin pl) {
		plugin = pl;
		subCommands = new ArrayList<AbstractSubCommand>();
		subCommands.add(new HelpSubCommand(this, plugin));
		subCommands.add(new ListSubCommand(this, plugin));
		subCommands.add(new InfoSubCommand(this, plugin));
		subCommands.add(new JailSubCommand(this, plugin));
		subCommands.add(new AddSubCommand(this, plugin));
		subCommands.add(new RemoveSubCommand(this, plugin));
		subCommands.add(new BailSubCommand(this, plugin));
		subCommands.add(new SwapSubCommand(this, plugin));
		subCommands.add(new EscapeSubCommand(this, plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Check for main permission
		if (!(sender.hasPermission("jailplusplus.command"))) {
			sender.sendMessage(Messages.NO_PERM);
			return true;
		}

		// If we have at least one argument
		if (args.length > 0) {
			// Check through all subcommands and see if we have one registered
			// under this name
			for (AbstractSubCommand sub : subCommands) {
				// Check through all aliases
				for (String s : sub.getAliases()) {
					// Check if the alias is our argument
					if (s.equalsIgnoreCase(args[0])) {
						// We found our command! Check for permission!
						if (!(sender.hasPermission(sub.getPermission()))) {
							sender.sendMessage(Messages.NO_PERM);
							return true;
						}

						// Now execute! But if it fails, show usage
						if (!sub.onCommand(sender, args)) {
							// We want to show help
							sender.sendMessage(sub.getUsage());
						}

						// And exit either way
						return true;
					}
				}
			}
		}

		// If we get down here, show usage
		return false;
	}
}
