package com.solarenchants.enchants.tools;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
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
import com.solarenchants.utils.PlayerUtils;

public class AutoSmelt extends SolarEnchantment{

	public AutoSmelt(String enchant, RARITY rarity, String target) {
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
		return Tools.isTool(item, Tools.PICKAXE);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {

		if(!this.checkTools(e.getPlayer(), Tools.PICKAXE)) return;

		e.setDropItems(false);
		
		AutoSmelt.call(e.getPlayer(), e.getBlock());
		
	}

	public static void call(Player player, Block block) {
		EnchantmentHandler handler = SolarEnchants.getInstance().enchantmentHandler;
		
		Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
		
		if(handler.hasEnchant(player.getInventory().getItemInMainHand(), CustomEnchants.TELEPATHY)) {
			if(PlayerUtils.canAddToInventory(player, drops)) {
				drops.forEach(o -> {
					if(o.getType() == Material.IRON_ORE) {
						player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, o.getAmount()));
						return;
					}
					if(o.getType() == Material.GOLD_ORE) {
						player.getInventory().addItem(new ItemStack(Material.GOLD_INGOT, o.getAmount()));
						return;
					}
					player.getInventory().addItem(o);
				});		
				
			}else {
				drops.forEach(o -> block.getWorld().dropItem(block.getLocation(), o));	
			}
		}else {
			drops.forEach(o -> {
				if(o.getType() == Material.IRON_ORE) {
					block.getWorld().dropItem(block.getLocation(), o);
					return;
				}
				if(o.getType() == Material.GOLD_ORE) {
					block.getWorld().dropItem(block.getLocation(), o);
					return;
				}
				player.getInventory().addItem(o);
			});	
		}

	}
	
	public static ItemStack effect(ItemStack item) {

		if(item.getType() == Material.IRON_ORE) {
			return new ItemStack(Material.IRON_INGOT);
		}
		if(item.getType() == Material.GOLD_ORE) {
			return new ItemStack(Material.GOLD_INGOT);
		}
		
		return item;

	}
	
	@Override
	public int price(int level) {
		return 3500*(level*2);
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Automatically turns ores into there respective ingots.";
	}

}
