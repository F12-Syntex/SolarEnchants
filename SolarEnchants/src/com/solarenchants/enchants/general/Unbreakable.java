package com.solarenchants.enchants.general;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.solarenchants.enchantments.Armour;
import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.utils.MessageUtils;

public class Unbreakable extends SolarEnchantment{

	public Unbreakable(String enchant, RARITY rarity, String target) {
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
		return Tools.isTool(item, Tools.TOOLS) || Armour.isArmour(item, Armour.ARMOUR);
	}
	
	@EventHandler
	public void onEntityAttack(PlayerInteractEvent e) {
		if(!(this.checkTools(e.getPlayer(), Tools.TOOLS) && this.checkArmour(e.getPlayer(), Armour.ARMOUR))) return;
		
		ItemStack tool = e.getPlayer().getInventory().getItemInMainHand();
		ItemMeta meta = tool.getItemMeta();
		meta.setUnbreakable(true);
	}

	@Override
	public int price(int level) {
		return 5000*level;
	}
	
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Item is unbreakable.";
	}

}
