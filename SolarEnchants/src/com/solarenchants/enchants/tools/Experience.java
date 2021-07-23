package com.solarenchants.enchants.tools;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.utils.MessageUtils;

public class Experience extends SolarEnchantment{

	public Experience(String enchant, RARITY rarity, String target) {
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
	public void onBlockBreak(BlockBreakEvent e) {

		if(!this.checkTools(e.getPlayer(), Tools.TOOLS)) return;
		
		int xp = e.getExpToDrop() * this.getToolLevel(e.getPlayer());
		
		e.setExpToDrop(xp);
		
	}
	
	@EventHandler
	public void onEntitiyKill(EntityDeathEvent e) {

	    Entity killer = e.getEntity().getKiller();
	  
	    if (killer instanceof Player){

	    	Player player = (Player)killer;
	    	
			if(!this.checkTools(player, Tools.TOOLS)) return;
			
			int xp = e.getDroppedExp() * this.getToolLevel(player);
			
			e.setDroppedExp(xp);
	    
	    }
	}

	@Override
	public int price(int level) {
		return 10000*(level);
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Multiplies the xp received by the level of this enchant.";
	}

}
