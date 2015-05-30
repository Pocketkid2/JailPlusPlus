package com.github.pocketkid2.jailplusplus.commands;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class AddSubCommand extends AbstractSubCommand {

	public AddSubCommand(JailBaseCommand jc, JailPlugin pl) {
		super(jc, pl);
		description = "Define a jail where you're standing (or update an existing one)";
		usage = "/jpp add <name>";
		permission = "jailplusplus.command.add";
		aliases = new String[] { "add", "set", "create", "update" };
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		// Check for player
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
			return true;
		}

		Player player = (Player) sender;

		// Check for first argument
		if (args.length < 2) {
			sender.sendMessage(Messages.NOT_ENOUGH_ARGUMENTS);
			return false;
		}

		String name = args[1];

		Map<String, Location> map = plugin.getJails();
		map.put(name, player.getLocation());
		plugin.setJails(map);
		player.sendMessage(Messages.JAIL_UPDATED);
		return true;
	}
}
