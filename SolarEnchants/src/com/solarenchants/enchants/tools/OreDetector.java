package com.solarenchants.enchants.tools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.utils.MessageUtils;

public class OreDetector extends SolarEnchantment{

	public OreDetector(String enchant, RARITY rarity, String target) {
		super(enchant, rarity, target);
	}

	@Override
	public String getName() {
		return MessageUtils.translateAlternateColorCodes(this.enchant);
	}

	@Override
	public int getMaxLevel() {
		return 6;
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

		if(!this.checkTools(e.getPlayer(), Tools.TOOLS)) return;

		int level = this.getToolLevel(e.getPlayer());
		
		List<Block> blocks = this.getBlocksAroundCenter(e.getBlock().getLocation(), level+2);
		
		for(Block i : blocks) {
			if(i.getType() == Material.DIAMOND_ORE) {
				i.getWorld().playSound(i.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10f, 10f);
				return;
			}
			if(i.getType() == Material.EMERALD_ORE) {
				i.getWorld().playSound(i.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10f, 10f);
				return;
			}
		}
		
	}

	@Override
	public int price(int level) {
		return 65000*level;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Plays a sound effect when diamonds/emeralds are found, checks 1 more layer of blocks every level.";
	}
	
	private ArrayList<Block> getBlocksAroundCenter(Location loc, int radius) {
	    ArrayList<Block> blocks = new ArrayList<Block>();
	  
	    for (int x = (loc.getBlockX()-radius); x <= (loc.getBlockX()+radius); x++) {
	        for (int y = (loc.getBlockY()-radius); y <= (loc.getBlockY()+radius); y++) {
	            for (int z = (loc.getBlockZ()-radius); z <= (loc.getBlockZ()+radius); z++) {
	                Location l = new Location(loc.getWorld(), x, y, z);
	                if (l.distance(loc) <= radius) {
	                    blocks.add(l.getBlock());
	                }
	            }
	        }
	    }
	  
	    return blocks;
	}
	

}
