package com.solarenchants.enchants.tools;

import java.util.Collection;

import org.bukkit.GameMode;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.solarenchants.enchantments.CustomEnchants;
import com.solarenchants.enchantments.EnchantmentHandler;
import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.MessageUtils;

public class Telepathy extends SolarEnchantment{

	public Telepathy(String enchant, RARITY rarity, String target) {
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
		
		if(e.getBlock().getState() instanceof Container) return;
		if(!this.checkTools(e.getPlayer(), Tools.TOOLS)) return;
		if(e.getPlayer().getInventory().firstEmpty() == -1) return;
		if(e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

		
		EnchantmentHandler handler = SolarEnchants.getInstance().enchantmentHandler;
		
		if(handler.hasEnchant(e.getPlayer().getInventory().getItemInMainHand(), CustomEnchants.SMELTER)) {
			return;
		}
		
		Collection<ItemStack> items = e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand());
		e.setDropItems(false);
		items.forEach(o -> e.getPlayer().getInventory().addItem(o));		

	}

	@Override
	public int price(int level) {
		return 1500;
	}
	
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Mined blocks directly enter the users inventory.";
	}

}
