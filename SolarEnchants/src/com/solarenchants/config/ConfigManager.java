package com.solarenchants.config;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.MessageUtils;

public class ConfigManager {

    public ArrayList<Config> config = new ArrayList<Config>();
    
    public void setup(Plugin plugin) {
    	
    	this.config.add(new Messages());
    	this.config.add(new Permissions());
    	this.config.add(new Cooldown());
          	
    	for(int i = 0; i < config.size(); i++) {
    		config.get(i).setup(plugin);
    		
    		if(config.get(i).folder() == "" || config.get(i).folder() == null) {
    		if(!config.get(i).configuration.contains("version") || (config.get(i).configuration.getInt("version") != config.get(i).version) && (!config.get(i).exception())) {
    			new File(plugin.getDataFolder(), config.get(i).name() + ".yml").delete();
    			plugin.saveResource(config.get(i).name() + ".yml", false);
       			config.get(i).configuration.setDefaults(YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), config.get(i).name() + ".yml")));
       			MessageUtils.sendConsoleMessage(config.get(i).name + " has an invalid version reseting...");
    		}
    		}else {
        		if(!config.get(i).configuration.contains("version") || (config.get(i).configuration.getInt("version") != config.get(i).version) && (!config.get(i).exception())) {
        			new File(plugin.getDataFolder()+"\\"+config.get(i).folder()+"\\", config.get(i).name() + ".yml").delete();
        			plugin.saveResource(config.get(i).folder()+"\\"+config.get(i).name() + ".yml", false);
        			config.get(i).configuration = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), config.get(i).name() + ".yml"));
        			MessageUtils.sendConsoleMessage(config.get(i).name + " has an invalid version reseting...");
        		}
    		}
    	}
    	
    	
    }
    
    public void reset() {
    	config.forEach(i -> i.configFile.delete());
    }
    
    public Config getConfig(String name) {
    	return config.stream().filter(i -> i.name().equalsIgnoreCase(name)).findFirst().get();
    }
    
    public void save() {
    	config.forEach(i -> i.save());
    }
    

    public void reload() {
    	SolarEnchants.getInstance().reload();
    }
    
    public void dispose() {
    	config.stream().filter(i -> i.saveData()).forEach(i -> i.save());
    	config.clear();
    }
    
}
