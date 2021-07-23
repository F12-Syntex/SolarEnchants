package com.solarenchants.cooldown;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.solarenchants.main.SolarEnchants;
import com.solarenchants.placeholder.time.TimeData;
import com.solarenchants.utils.MappyHelper;
import com.solarenchants.utils.MappyObject;

public class Cooldowns {

	public static Cooldowns instance;
	
	public FileConfiguration config = SolarEnchants.getInstance().configManager.getConfig("cooldown").configuration;
	
	public String message = config.getString("Format.message");
	public String coding = config.getString("Format.colorCoding");
	public int simplified = config.getInt("Format.simplicity");
	
	public int getCooldown(String key) {
		
		List<Map<?, ?>> cooldowns = config.getMapList("Cooldown");
		
		MappyHelper helper = new MappyHelper(cooldowns);
		
		List<MappyObject> commands = helper.decode();
		
		List<MappyObject> command = commands.stream().filter(i -> i.getKey().toString().equalsIgnoreCase(key)).collect(Collectors.toList());
		
		if(command.isEmpty()) {
			return 0;
		}
		
		return Integer.valueOf(command.get(0).getValue().toString());
	}

	public List<TimeData> getTimeData(){
		
		ConfigurationSection section = config.getConfigurationSection("Time");
		
		List<TimeData> data = new ArrayList<TimeData>();
		
		for(String name : section.getKeys(false)) {
		
			ConfigurationSection time = section.getConfigurationSection(name);
			
			String shortName = time.getString(".short_name");
			long seconds = time.getLong(".seconds");
			
			TimeData timeData = new TimeData(name, shortName, seconds);
			
			data.add(timeData);
			
		}
		
		return data;
	}
	
	public static Cooldowns instance() {
		return instance;
	}
	
	public static void reInitialize() {
		instance = new Cooldowns();
	}
	
	
}
