package com.solarenchants.enchants.tools;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.utils.MessageUtils;

public class Barbarian extends SolarEnchantment{

	public Barbarian(String enchant, RARITY rarity, String target) {
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
		return Tools.isTool(item, Tools.SWORD);
	}

	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent e) {
		
		if(!(e.getDamager() instanceof Player)) return;
		if(!(e.getEntity() instanceof LivingEntity)) return;
		
		Player player = (Player)e.getDamager();
		
		if(!this.checkTools(player, Tools.SWORD)) return;
		
		int level = this.getToolLevel(player);
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, level));
		
		
	}

	@Override
	public int price(int level) {
		if(level == this.getStartLevel()) return 125000;
		return price((level-1)) * 3;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Gain a sudden surge of strenth after hitting a target.";
	}

}
