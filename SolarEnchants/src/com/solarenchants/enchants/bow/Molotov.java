package com.solarenchants.enchants.bow;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.utils.MessageUtils;

public class Molotov extends SolarEnchantment{

	public Molotov(String enchant, RARITY rarity, String target) {
		super(enchant, rarity, target);
	}

	@Override
	public String getName() {
		return MessageUtils.translateAlternateColorCodes(this.enchant);
	}

	@Override
	public int getMaxLevel() {
		return 3;
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
		return Tools.isTool(item, Tools.BOW);
	}

	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent e) {
		
        if (e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
            
        	Arrow a = (Arrow) e.getDamager();
        
            if(!(a.getShooter() instanceof Player)) return;
            
            Player player = (Player) a.getShooter();
        
            if(!this.checkTools(player, Tools.BOW)) return;
            
    		if(!(e.getEntity() instanceof LivingEntity)) return;
            
            LivingEntity target = (LivingEntity)e.getEntity();
        
    		World world = target.getWorld();
    		world.playEffect(target.getLocation(), Effect.POTION_BREAK, 10);
    		double boundaries = 0.1*this.getToolLevel(player);
    		for(double x = boundaries; x >= -boundaries; x-=0.1)
    			for(double z = boundaries; z >= -boundaries; z-=0.1) {
    				@SuppressWarnings("deprecation")
    				FallingBlock b = world.spawnFallingBlock(target.getLocation(), Material.FIRE, (byte) 0x0);
    				b.setVelocity(new Vector(x, 0.1, z));
    				b.setDropItem(false);
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
		return "Bursts a molotov upon entity attack!";
	}

}
