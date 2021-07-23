package com.solarenchants.enchants.bow;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.utils.MessageUtils;

public class Blaze extends SolarEnchantment{

	public Blaze(String enchant, RARITY rarity, String target) {
		super(enchant, rarity, target);
	}

	@Override
	public String getName() {
		return MessageUtils.translateAlternateColorCodes(this.enchant);
	}

	@Override
	public int getMaxLevel() {
		return 1;
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
		return Tools.isTool(item, Tools.BOW);
	}

	@EventHandler
	public void onEntityAttack(EntityShootBowEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
		
		Player player = (Player) e.getEntity();
		
		if(!this.checkTools(player, Tools.BOW)) return;
        
        e.setCancelled(true);
        e.getEntity().launchProjectile(SmallFireball.class);
		
	
	}
	

	@Override
	public int price(int level) {
		return 65000*level;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Shoots a fireball!";
	}

}
