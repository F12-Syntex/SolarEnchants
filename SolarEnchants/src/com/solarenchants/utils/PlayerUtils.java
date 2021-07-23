package com.solarenchants.utils;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {
	
	public static boolean isInventoryFull(Player player) {
		return player.getInventory().contains(Material.AIR);
	}
	
	public static boolean canAddToInventory(Player player, Collection<ItemStack> items) {
		
		int free = 0;
		
		for(ItemStack i : player.getInventory().getContents()) {
			if(i == null || i.getType() == Material.AIR) {
				free++;
			}
		}
		
		return (items.size() <= free);
		
	}

}
