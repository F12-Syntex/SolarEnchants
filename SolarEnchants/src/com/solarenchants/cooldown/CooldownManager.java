package com.solarenchants.cooldown;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CooldownManager {

	private List<CooldownEntity> cooldowns;
	
	public CooldownManager() {
		this.cooldowns = new ArrayList<CooldownEntity>();
	}
	
	public CooldownUser getUser(UUID uuid) {
		Stream<CooldownUser> stream = cooldowns.stream().filter(i -> i instanceof CooldownUser).map(i -> (CooldownUser)i);
		List<CooldownUser> target = stream.filter(i -> i.getUuid().compareTo(uuid) == 0).collect(Collectors.toList());
		if(target.isEmpty()) {
			CooldownUser newUser = new CooldownUser(uuid);
			this.cooldowns.add(newUser);
			return newUser;
		}
		
		return target.get(0);
	}
	
	public void registerSinglyCooldown(CooldownEntity i) {
		this.cooldowns.add(i);
	}

	public List<CooldownEntity> getCooldowns() {
		return cooldowns;
	}

	public void setCooldowns(List<CooldownEntity> cooldowns) {
		this.cooldowns = cooldowns;
	}

	
	
	
}
