package com.github.pocketkid2.jailplusplus.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class JailObject implements ConfigurationSerializable {

	public String getJailName() {
		return jailName;
	}

	public void setJailName(String jailName) {
		this.jailName = jailName;
	}

	public String getJailer() {
		return jailer;
	}

	public void setJailer(String jailer) {
		this.jailer = jailer;
	}

	public double getBailAmount() {
		return bailAmount;
	}

	public void setBailAmount(double bailAmount) {
		this.bailAmount = bailAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Location getPreviousLocation() {
		return previousLocation;
	}

	public void setPreviousLocation(Location previousLocation) {
		this.previousLocation = previousLocation;
	}

	public JailObject(String jailName, String jailer, double bailAmount, String reason, Location previousLocation) {
		super();
		this.jailName = jailName;
		this.jailer = jailer;
		this.bailAmount = bailAmount;
		this.reason = reason;
		this.previousLocation = previousLocation;
	}

	private String jailName;

	private String jailer;

	private double bailAmount;

	private String reason;

	private Location previousLocation;

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("jailName", jailName);
		map.put("jailer", jailer);
		map.put("bailAmount", bailAmount);
		map.put("reason", reason);
		map.put("previousLocation", previousLocation);

		return map;
	}

	public static JailObject deserialize(Map<String, Object> map) {
		return new JailObject((String) map.get("jailName"), (String) map.get("jailer"), (Double) map.get("bailAmount"), (String) map.get("reason"), (Location) map.get("previousLocation"));
	}

}
