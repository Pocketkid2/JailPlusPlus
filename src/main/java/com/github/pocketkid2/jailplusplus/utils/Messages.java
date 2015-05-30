package com.github.pocketkid2.jailplusplus.utils;

import org.bukkit.ChatColor;

// We use this like an enum, to represent all the repeatable messages we want to send
public interface Messages {
	String CANT_JAIL_SELF = ChatColor.RED + "You can't jail yourself!";
	String INVALID_COMMAND = ChatColor.RED + "That command doesn't exist!";
	String INVALID_JAILNAME = ChatColor.RED + "That jail doesn't exist!";
	String INVALID_PLAYERNAME = ChatColor.RED + "That player is invalid!";
	String JAIL_REMOVED = ChatColor.AQUA + "Jail was removed!";
	String JAIL_UPDATED = ChatColor.AQUA + "Jail was updated!";
	String MUST_BE_PLAYER = ChatColor.RED + "You must be a player!";
	String NO_JAILS = ChatColor.RED + "There are no jails set!";
	String NO_PERM = ChatColor.RED + "You don't have permission for that!";
	String NO_PLAYERS = ChatColor.RED + "There are no players in jail!";
	String NOT_ENOUGH_ARGUMENTS = ChatColor.RED + "Not enough arguments!";
	String PLAYER_NOT_IN_JAIL = ChatColor.RED + "That player isn't in jail!";
	String PLAYER_WAS_JAILED = ChatColor.AQUA + "Player was sent to jail!";
	String PLAYER_WAS_UNJAILED = ChatColor.AQUA + "Player was let out of jail!";
	String YOU_NOT_IN_JAIL = ChatColor.RED + "You are not in jail!";
	String YOU_WERE_JAILED = ChatColor.AQUA + "You have been sent to jail!";
	String YOU_WERE_UNJAILED = ChatColor.AQUA + "You were let out of jail!";
	String NOT_ENOUGH_MONEY = ChatColor.RED + "You don't have enough money!";
	String PLAYER_WAS_BAILED = ChatColor.AQUA + "Player was bailed out!";
	String YOU_WERE_BAILED = ChatColor.AQUA + "You were bailed out!";
	String CANT_SWAP_SELF = ChatColor.RED + "You can't swap yourself!";
	String YOU_WERE_SWAPPED = ChatColor.AQUA + "You were swapped!";
	String COOLDOWN_ON = ChatColor.RED + "You must wait before using that again!";
	String ESCAPE_SUCCESS = ChatColor.AQUA + "You escaped successfully!";
	String ESCAPE_FAILURE = ChatColor.AQUA + "You were not able to escape!";
	String CANT_SWAP_PLAYER = ChatColor.RED + "You can't take that player's place!";
	String CANT_JAIL_PLAYER = ChatColor.RED + "That player can't be jailed!";
}
