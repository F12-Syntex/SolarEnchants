package com.solarenchants.enchants.bow;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.Tools;
import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.MessageUtils;

public class Bombardment extends SolarEnchantment{

	public Bombardment(String enchant, RARITY rarity, String target) {
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

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent e) {
		
        if (e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
            
        	Arrow a = (Arrow) e.getDamager();
        
            if(!(a.getShooter() instanceof Player)) return;
            
            Player player = (Player) a.getShooter();
        
            if(!this.checkTools(player, Tools.BOW)) return;
            
    		if(!(e.getEntity() instanceof LivingEntity)) return;
            
            LivingEntity target = (LivingEntity)e.getEntity();
        


    		final World world = target.getWorld();
    		Vector vec = new Vector(0, -5, 0);
    		Location spawnLocation = new Location(world, target.getLocation().getX(), 255, target.getLocation().getZ());
    		
			final FallingBlock b = world.spawnFallingBlock(spawnLocation, Material.TNT, (byte) 0x0);
    		b.setVelocity(vec);

    		new BukkitRunnable() {

    			Location l	= b.getLocation();

    			@Override
    			public void run() {
    				l = b.getLocation();
    				if(b.isDead()) {
    					l.getBlock().setType(Material.AIR);
    					for(int i = 0; i <= 1 + getToolLevel(player); i++) {
    						TNTPrimed tnt = world.spawn(l, TNTPrimed.class);
    						tnt.setFuseTicks(0);
    					}
    					this.cancel();
    				}
    				
    				l.getWorld().playSound(l, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 2f);
    			}
    		}.runTaskTimer(SolarEnchants.getInstance(), 0l, 5l);
    		}
            
            
            
        }
	

	@Override
	public int price(int level) {
		return 65000*level;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Summons a primed TNT to blow the area upon entity attack.";
	}

}
