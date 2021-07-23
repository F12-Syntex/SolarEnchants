package com.solarenchants.enchantments;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.solarenchants.main.SolarEnchants;

public abstract class SolarEnchantment extends Enchantment implements Listener{

	public String enchant;
	public RARITY rarity;
	public String target;
	
	public SolarEnchantment(String enchant, RARITY rarity, String target) {
		super(NamespacedKey.minecraft(enchant));
		this.enchant = RARITY.getColour(rarity) + enchant;
		this.rarity = rarity;
		this.target = target;
		
		SolarEnchants.getInstance().getServer().getPluginManager().registerEvents(this, SolarEnchants.getInstance());
	}
	
	public abstract int price(int level);
	public abstract String description();
	
	public boolean checkTools(Player player, Tools check) {	
			if(Tools.isTool(player.getInventory().getItemInMainHand(), check)) {
			if(player.getInventory().getItemInMainHand().hasItemMeta()) {
				for(Enchantment i : player.getInventory().getItemInMainHand().getItemMeta().getEnchants().keySet()) {
					if(i.getKey().getKey().equals(this.getKey().getKey())) {
						return true;
					}
				}
		}
		}
		return false;
	}
	
	public int getToolLevel(Player player) {
		if(player.getInventory().getItemInMainHand().hasItemMeta()) {
			for(Enchantment i : player.getInventory().getItemInMainHand().getItemMeta().getEnchants().keySet()) {
				if(i.getKey().getKey().equals(this.getKey().getKey())) {
					return player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(i);
				}
	}
	}
		return 0;
	}
	
	public int getArmourLevel(Player player, Armour armour) {
		
		int level = 0;
		
		for(ItemStack o : player.getInventory().getArmorContents()) {
			if(o == null) continue;
			if(o.getType() == Material.AIR) continue;

			if(Armour.isArmour(o, armour)) {
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					for(Enchantment i : player.getInventory().getItemInMainHand().getItemMeta().getEnchants().keySet()) {
						if(i.getKey().getKey().equals(this.getKey().getKey())) {
							level = player.getInventory().getItemInMainHand().getItemMeta().getEnchantLevel(i);
						}
					}
			}
			}
		}

		return level;
	}
	
	public boolean checkArmour(Player player, Armour check) {
		for(ItemStack o : player.getInventory().getArmorContents()) {
			if(o == null) continue;
			if(o.getType() == Material.AIR) continue;

			if(Armour.isArmour(o, check)) {
				if(player.getInventory().getItemInMainHand().hasItemMeta()) {
					for(Enchantment i : o.getItemMeta().getEnchants().keySet()) {
						if(i.getKey().getKey().equals(this.getKey().getKey())) {
							return true;
						}
					}
			}
			}
		}

	return false;
}
	
}
