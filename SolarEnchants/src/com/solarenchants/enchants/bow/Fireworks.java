package com.solarenchants.enchants.bow;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.MessageUtils;
import com.solarenchants.utils.Misc;

public class Fireworks extends SolarEnchantment{

	public Fireworks(String enchant, RARITY rarity, String target) {
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
		return Tools.isTool(item, Tools.BOW);
	}

	@EventHandler
	public void onEntityAttack(EntityShootBowEvent e) {
		
		if(!(e.getEntity() instanceof Player)) return;
		
		Player player = (Player) e.getEntity();
		
		if(!this.checkTools(player, Tools.BOW)) return;
        
		new BukkitRunnable() {

			int	fireworkLivingTime = 1 + getToolLevel(player);

			@Override
			public void run() {
				if(fireworkLivingTime > 0) {
					Location loc = e.getProjectile().getLocation();
					if(e.getProjectile() != null && !e.getProjectile().isDead()) {
						Misc.shootFirework(loc, new Random());

						fireworkLivingTime--;
						return;
					}
				}
					this.cancel();
				
			}

		}.runTaskTimer(SolarEnchants.getInstance(), 0, 5);
		
	
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
        
            target.getWorld().createExplosion(target.getLocation(), 0);
            
        }
	
	}

	@Override
	public int price(int level) {
		return 65000*level;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Shoots a fireball!";
	}

}
