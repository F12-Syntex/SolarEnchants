package com.solarenchants.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownUser extends CooldownEntity{
	
	private UUID uuid;
	private Map<String, Integer> cooldown;
	
	public CooldownUser(UUID uuid) {
		this.uuid = uuid;
		this.cooldown = new HashMap<String, Integer>();
	}
	
	public Integer getTime(String key) {
		if(this.cooldown.containsKey(key)) {
			return this.cooldown.get(key);
		}else {
			int timer = Cooldowns.instance().getCooldown(key);
			this.cooldown.put(key, Cooldowns.instance().getCooldown(timer+""));
			return 0;
		}
	}
	
	public void reset(String key) {
		this.cooldown.put(key, Cooldowns.instance().getCooldown(key));
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public Map<String, Integer> getCooldown() {
		return cooldown;
	}

	public void setCooldown(Map<String, Integer> cooldown) {
		this.cooldown = cooldown;
	}

	@Override
	public void onTick() {
		this.cooldown.keySet().forEach(i -> {
			
			int newValue = this.cooldown.get(i) - 1;
			
			if(newValue >= 0) {
				this.cooldown.put(i, newValue);
			}
	
		});
	}
	
}
