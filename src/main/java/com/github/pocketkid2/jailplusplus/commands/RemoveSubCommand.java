package com.github.pocketkid2.jailplusplus.commands;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class RemoveSubCommand extends AbstractSubCommand {

	public RemoveSubCommand(JailBaseCommand jc, JailPlugin pl) {
		super(jc, pl);
		description = "Remove a jail by it's name";
		usage = "/jpp remove <name>";
		permission = "jailplusplus.command.remove";
		aliases = new String[] { "remove", "delete", "undefine", "destroy" };
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		// Check for first argument
		if (args.length < 2) {
			sender.sendMessage(Messages.NOT_ENOUGH_ARGUMENTS);
			return false;
		}

		String name = args[1];

		if (!plugin.getJails().containsKey(name)) {
			sender.sendMessage(Messages.INVALID_JAILNAME);
			return true;
		}

		Map<String, Location> map = plugin.getJails();
		map.remove(name);
		plugin.setJails(map);
		sender.sendMessage(Messages.JAIL_REMOVED);
		return true;
	}

}
