package com.solarenchants.enchants.armour;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.solarenchants.enchantments.Armour;
import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.utils.Luck;
import com.solarenchants.utils.MessageUtils;

public class Ninja extends SolarEnchantment{

	public Ninja(String enchant, RARITY rarity, String target) {
		super(enchant, rarity, target);
	}

	@Override
	public String getName() {
		return MessageUtils.translateAlternateColorCodes(this.enchant);
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean conflictsWith(Enchantment other) {
		return false;
	}

	@Override
	public boolean canEnchantItem(ItemStack item) {
		return Armour.isArmour(item, Armour.ARMOUR);
	}

	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent e) {
		
		if(!(e.getDamager() instanceof Player)) return;
		if(!(e.getEntity() instanceof LivingEntity)) return;
		
		Player player = (Player)e.getDamager();

		if(!this.checkArmour(player, Armour.ARMOUR)) return;
		
		int level = this.getToolLevel(player);
		
		double chance = (double)level/100.0;
		
		if(!Luck.chance(chance)) return;
		
		e.setDamage(0);

	}
	
	@Override
	public int price(int level) {
		return 50000*level;
	}
	
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Grants you the abbility to dodge attacks, each upgrade increases the chance by 1%";
	}

}
