package com.solarenchants.cooldown;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitScheduler;

import com.solarenchants.main.SolarEnchants;

public class CooldownTick {
	
	private BukkitScheduler scheduler;

	public CooldownTick() {
		this.scheduler = SolarEnchants.getInstance().getServer().getScheduler();
	}

	public void schedule() {
		
        scheduler.scheduleSyncRepeatingTask(SolarEnchants.getInstance(), new Runnable() {
            @Override
            public void run() {
            	final List<CooldownEntity> cooldowns = SolarEnchants.getInstance().cooldownManager.getCooldowns();
            	final List<CooldownEntity> remove = new ArrayList<CooldownEntity>();
            	
            	for(CooldownEntity i : cooldowns) {
            		i.onTick();
            		
            		
            		if(i instanceof SingleUseCooldownEntity) {
            			SingleUseCooldownEntity dispose = (SingleUseCooldownEntity)i;
            			if(!(dispose.running)) {
            				remove.add(dispose);
            			}
            		}
            	
            	
            	
            	}
            	
            	SolarEnchants.getInstance().cooldownManager.getCooldowns().removeAll(remove);
            	
            }  	
        }, 0L, 20L);

	}
	
	public void stop() {
		this.scheduler.cancelTasks(SolarEnchants.getInstance());
	}
	
}
