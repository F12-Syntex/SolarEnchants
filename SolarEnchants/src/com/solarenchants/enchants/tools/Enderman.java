package com.solarenchants.enchants.tools;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.utils.Luck;
import com.solarenchants.utils.MessageUtils;

public class Enderman extends SolarEnchantment{

	public Enderman(String enchant, RARITY rarity, String target) {
		super(enchant, rarity, target);
	}

	@Override
	public String getName() {
		return MessageUtils.translateAlternateColorCodes(this.enchant);
	}

	@Override
	public int getMaxLevel() {
		return 10;
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
		
		double chance = (double)level/100.0;
		
		if(!Luck.chance(chance)) return;
		
		LivingEntity target = (LivingEntity)e.getEntity();
		
		Location playerLocation = player.getLocation();
        Location damagerLocation = target.getLocation();

        org.bukkit.util.Vector swap = damagerLocation.toVector().subtract(playerLocation.toVector()).normalize();
     
        Location behind = damagerLocation.clone().add(swap);
        
        if(!(behind.getBlock().isEmpty() && behind.add(0, 1, 0).getBlock().isEmpty())) return;
        
        player.teleport(behind);
        
        Vector vec = behind.toVector().subtract(target.getLocation().toVector());
        
        player.getEyeLocation().setDirection(vec);


	}

	@Override
	public int price(int level) {
		if(level == this.getStartLevel()) return 10000;
		return price((level-1)) * 2;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "A 1% chance to teleport behind your target, every level upgrade increases this chance by 1%";
	}

}
