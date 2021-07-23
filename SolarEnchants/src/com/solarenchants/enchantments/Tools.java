package com.solarenchants.enchantments;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Tools {

	TOOLS, PICKAXE, AXE, HOE, SWORD, SHOVEL, BOW;

	public static boolean isTool(ItemStack item, Tools type) {
		
		Material material = item.getType();
		
		if(material.name().toLowerCase().contains(type.name().toLowerCase())) return true;
		
		if(type == Tools.TOOLS) {
			
			if(material.name().toLowerCase().contains("pickaxe") ||
			   material.name().toLowerCase().contains("sword") ||
			   material.name().toLowerCase().contains("shovel") ||
			   material.name().toLowerCase().contains("hoe") ||
			   material.name().toLowerCase().contains("axe") ||
			   material.name().toLowerCase().contains("bow") ) {
				return true;
			}
			
		}
		
		return false;
	}
	
}
