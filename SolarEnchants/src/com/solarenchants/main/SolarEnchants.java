package com.solarenchants.main;
import java.io.File;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.solarenchants.config.ConfigManager;
import com.solarenchants.cooldown.CooldownManager;
import com.solarenchants.cooldown.CooldownTick;
import com.solarenchants.cooldown.Cooldowns;
import com.solarenchants.data.GenericMessages;
import com.solarenchants.data.Permissions;
import com.solarenchants.enchantments.EnchantmentHandler;
import com.solarenchants.events.EventHandler;
import com.solarenchants.utils.MessageUtils;

import net.milkbowl.vault.economy.Economy;


public class SolarEnchants extends JavaPlugin implements Listener{


    public static SolarEnchants instance;
    public com.solarenchants.commands.CommandManager CommandManager;
    public ConfigManager configManager;
    public EventHandler eventHandler;
    public CooldownManager cooldownManager;
    public CooldownTick cooldownTick;
	public EnchantmentHandler enchantmentHandler;
	public Economy econ = null;
    public File ParentFolder;
	
	@Override
	public void onEnable(){
		
		ParentFolder = getDataFolder();
	    instance = this;
		
	    configManager = new ConfigManager();
	    configManager.setup(this);

	    GenericMessages.reInitialize();
	    Permissions.reInitialize();
	    Cooldowns.reInitialize();
	    
	    eventHandler = new EventHandler();
	    eventHandler.setup();
	    
	    this.cooldownManager = new CooldownManager();

	    this.cooldownTick = new CooldownTick();
	    this.cooldownTick.schedule();
	    
	    this.CommandManager = new com.solarenchants.commands.CommandManager();
	    this.CommandManager.setup(this);
	    
	    this.enchantmentHandler = new EnchantmentHandler();
	    this.enchantmentHandler.initialize();
	    
	    if (!setupEconomy() ) {
            MessageUtils.sendConsoleMessage("&cDisabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
	    
	}
	
	    
	

	private boolean setupEconomy() {
       if (getServer().getPluginManager().getPlugin("Vault") == null) {
           return false;
       }
       RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
       if (rsp == null) {
           return false;
       }
       econ = rsp.getProvider();
       return econ != null;
    }

	
	@Override
	public void onDisable(){
		this.configManager.dispose();
		this.enchantmentHandler.unregisterAll();
		this.eventHandler = null;
		HandlerList.unregisterAll();
	}

	public void reload() {
	    GenericMessages.reInitialize();
	    Permissions.reInitialize();
	    Cooldowns.reInitialize();
	    this.enchantmentHandler.initialize();
	}
		

	public static SolarEnchants getInstance() {
		return instance;
	}
		
	
}
