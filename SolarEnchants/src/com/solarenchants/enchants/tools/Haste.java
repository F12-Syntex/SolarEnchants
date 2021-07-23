package com.solarenchants.enchants.tools;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.utils.MessageUtils;

public class Haste extends SolarEnchantment{

	public Haste(String enchant, RARITY rarity, String target) {
		super(enchant, rarity, target);
	}

	@Override
	public String getName() {
		return MessageUtils.translateAlternateColorCodes(this.enchant);
	}

	@Override
	public int getMaxLevel() {
		return 50;
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
	public void onBlockBreak(PlayerInteractEvent e) {

		if(!this.checkTools(e.getPlayer(), Tools.TOOLS)) return;
		
		int level = this.getToolLevel(e.getPlayer());
		
		e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 45, level));
		
	}

	@Override
	public int price(int level) {
		return 10000*level;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Gives the user a haste effect, the power is proportional to the level.";
	}
	
}
