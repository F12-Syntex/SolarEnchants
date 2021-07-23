package com.solarenchants.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import com.solarenchants.main.SolarEnchants;

public class EventHandler {

    public List<SubEvent> events = new ArrayList<SubEvent>();
	
    private Plugin plugin = SolarEnchants.instance;
    
	public void setup() {
		events.clear();
		events.add(new EnchantAdder());
		events.forEach(i -> {
			HandlerList.unregisterAll(i);
			plugin.getServer().getPluginManager().registerEvents(i, plugin);
		});
	}
	
}
