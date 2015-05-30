package com.github.pocketkid2.jailplusplus.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

	/**
	 * Private API implementation: key = UUID of the player value = long value
	 * representing the time (in milliseconds) until this cooldown is over
	 */
	private Map<UUID, Long> cooldowns;

	public CooldownManager() {
		cooldowns = new HashMap<UUID, Long>();
	}

	/**
	 * Public API function: Get a boolean representing whether the cooldown is
	 * over for this player
	 */
	public boolean isCooldownOn(UUID uuid) {
		if (cooldowns.containsKey(uuid)) {
			Long over = cooldowns.get(uuid);
			if (System.currentTimeMillis() < over.longValue()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Public API function: Start the cooldown for this player
	 */
	public void startCooldown(UUID uuid, long seconds) {
		cooldowns.put(uuid, Long.valueOf(System.currentTimeMillis() + (seconds * 1000)));
	}

	/**
	 * Public API function: Get the time in minutes until the cooldown is over
	 * for that player
	 */
	public int timeTillOver(UUID uuid) {
		if (cooldowns.containsKey(uuid)) {
			if (isCooldownOn(uuid)) {
				Long left = cooldowns.get(uuid) - System.currentTimeMillis();
				left /= 60000;
				return left.intValue();
			}
		}
		return 0;
	}
}
