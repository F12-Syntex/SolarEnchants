package com.solarenchants.enchantments;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Armour {

	ARMOUR, HELMET, CHESTPLATE, LEGGINGS, BOOTS;
	
	public static boolean isArmour(ItemStack item, Armour type) {
		
		Material material = item.getType();
		
		if(material.name().toLowerCase().contains(type.name().toLowerCase())) return true;

		if(type == Armour.ARMOUR) {
			
			if(material.name().toUpperCase().contains("HELMET") ||
			   material.name().toUpperCase().contains("CHESTPLATE") ||
			   material.name().toUpperCase().contains("LEGGINGS") ||
			   material.name().toUpperCase().contains("BOOTS")) {
				return true;
			}
			
		}
		
		return false;
	}
	
}
