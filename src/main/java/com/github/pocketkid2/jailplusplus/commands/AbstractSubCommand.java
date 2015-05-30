package com.github.pocketkid2.jailplusplus.commands;

import org.bukkit.command.CommandSender;

import com.github.pocketkid2.jailplusplus.JailPlugin;

public abstract class AbstractSubCommand {

	protected String description;
	protected String usage;
	protected String permission;
	protected String[] aliases;
	protected JailBaseCommand jailCommand;
	protected JailPlugin plugin;

	public AbstractSubCommand(JailBaseCommand jc, JailPlugin pl) {
		jailCommand = jc;
		plugin = pl;
	}

	// onCommand wrapper
	public abstract boolean onCommand(CommandSender sender, String[] args);

	public String getDescription() {
		return description;
	}

	public String getUsage() {
		return usage;
	}

	public String getPermission() {
		return permission;
	}

	public String[] getAliases() {
		return aliases;
	}
}
