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

public class Curse extends SolarEnchantment{

	public Curse(String enchant, RARITY rarity, String target) {
		super(enchant, rarity, target);
	}

	@Override
	public String getName() {
		return MessageUtils.translateAlternateColorCodes(this.enchant);
	}

	@Override
	public int getMaxLevel() {
		return 15;
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
		return Tools.isTool(item, Tools.TOOLS);
	}

	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent e) {
		
		if(!(e.getDamager() instanceof Player)) return;
		if(!(e.getEntity() instanceof LivingEntity)) return;
		
		Player player = (Player)e.getDamager();
		
		if(!this.checkTools(player, Tools.TOOLS)) return;
		
		LivingEntity target = (LivingEntity)e.getEntity();
		
		target.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 50, this.getToolLevel(player)));
		
	}

	@Override
	public int price(int level) {
		return 35000*level;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Applies the wither effect to your target for 2 seconds, the power of the effect is proportional to the level.";
	}

}
