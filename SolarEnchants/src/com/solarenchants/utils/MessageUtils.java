package com.solarenchants.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.solarenchants.data.GenericMessages;
import com.solarenchants.main.SolarEnchants;

public class MessageUtils {

	
	public static String translateAlternateColorCodes(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
    
	public static void sendMessage(Player player, String s) {
		player.sendMessage(MessageUtils.translateAlternateColorCodes(s));
	}
	
	public static void inform(Player player, String s) {
		player.sendMessage(GenericMessages.instance().PREFIX + " " + MessageUtils.translateAlternateColorCodes(s));
	}

	public static void sendConsoleMessage(String msg){
		  Bukkit.getConsoleSender().sendMessage(GenericMessages.instance().PREFIX + ": " + MessageUtils.translateAlternateColorCodes(msg));
	}
	public static void sendConsoleMessage(String[] msg){
		for(int i = 0; i < msg.length; i++)
		System.out.println(MessageUtils.translateAlternateColorCodes(msg[i]));
	}
	public static void sendHelp(Player player) {
		 SolarEnchants.getInstance().CommandManager.getCommands().forEach(i -> {
			 MessageUtils.sendMessage(player, GenericMessages.instance().PREFIX + " " + "&6• &c" + i.name() + "&7 : &b" + i.info());
		 });
	}
	public static String prettyBoolean(boolean i) {
		if(i) {
			return "Enabled";
		}else {
			return "Disabled";
		}
	}
}
