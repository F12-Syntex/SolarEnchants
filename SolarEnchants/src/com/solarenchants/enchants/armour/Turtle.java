package com.solarenchants.enchants.armour;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.solarenchants.enchantments.Armour;
import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.utils.MessageUtils;

public class Turtle extends SolarEnchantment{

	public Turtle(String enchant, RARITY rarity, String target) {
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
		return Armour.isArmour(item, Armour.ARMOUR);
	}

	@EventHandler
	public void onMoveEvent(PlayerMoveEvent e) {

		if(!this.checkArmour(e.getPlayer(), Armour.ARMOUR)) return;
		
		int level = this.getToolLevel(e.getPlayer());
		
		e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, level));
		
	}

	@Override
	public int price(int level) {
		return 50000*level;
	}
	
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Provides the user with the resistance effect, power is proportion the the level.";
	}

}
