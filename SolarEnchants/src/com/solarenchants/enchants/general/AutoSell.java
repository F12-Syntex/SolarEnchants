package com.solarenchants.enchants.general;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.utils.MessageUtils;

public class AutoSell extends SolarEnchantment{

	public AutoSell(String enchant, RARITY rarity, String target) {
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
		return Tools.isTool(item, Tools.TOOLS);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {

		if(!this.checkTools(e.getPlayer(), Tools.PICKAXE)) return;

		if(e.getPlayer().getInventory().firstEmpty() == -1) {
			e.getPlayer().chat("/sellall");
		}
		
	}

	@Override
	public int price(int level) {
		return 50000;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Types the command /sellall when your inventory is full.";
	}

}
