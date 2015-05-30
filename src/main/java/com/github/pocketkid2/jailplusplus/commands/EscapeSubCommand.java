package com.github.pocketkid2.jailplusplus.commands;

import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.pocketkid2.jailplusplus.JailPlugin;
import com.github.pocketkid2.jailplusplus.utils.Messages;

public class EscapeSubCommand extends AbstractSubCommand {

	public EscapeSubCommand(JailBaseCommand jc, JailPlugin pl) {
		super(jc, pl);
		description = "Use this every once in a while to try to escape! Note this doesn't always work!";
		usage = "/jpp escape";
		permission = "jailplusplus.command.escape";
		aliases = new String[] { "escape", "jailbreak" };
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		// Check for player
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.MUST_BE_PLAYER);
			return true;
		}
		Player player = (Player) sender;

		// Check if player is in jail
		if (!plugin.isPlayerInJail(player)) {
			player.sendMessage(Messages.YOU_NOT_IN_JAIL);
			return true;
		}

		// Check if cooldown is on
		if (plugin.cm.isCooldownOn(player.getUniqueId())) {
			player.sendMessage(Messages.COOLDOWN_ON);
			return true;
		}

		Random r = new Random(System.currentTimeMillis());
		if (r.nextInt(100) < plugin.getConfig().getInt("escape-values.percentage-of-success")) {
			// We escaped!
			player.teleport(plugin.getPlayerObject(player).getPreviousLocation());
			plugin.removePlayerObject(player);
			player.sendMessage(Messages.ESCAPE_SUCCESS);
		} else {
			// We didn't
			player.sendMessage(Messages.ESCAPE_FAILURE);
		}

		// Start a new cooldown
		plugin.cm.startCooldown(player.getUniqueId(), plugin.getConfig().getInt("escape-values.cooldown-time") * 60);
		return true;
	}

}
