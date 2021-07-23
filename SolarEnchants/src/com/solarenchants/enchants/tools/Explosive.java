package com.solarenchants.enchants.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
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

public class Explosive extends SolarEnchantment{

	public Explosive(String enchant, RARITY rarity, String target) {
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
		return Tools.isTool(item, Tools.PICKAXE);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {

		Player player = e.getPlayer();
		ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
		
		if(!this.checkTools(e.getPlayer(), Tools.PICKAXE)) return;
		
        List<Location> locations = new ArrayList<Location>();

        int level = this.getToolLevel(player);
        
        int locRad = level + 2;
        if (ThreadLocalRandom.current().nextInt(100) < level * 5)
            locRad += 2;
        int r = locRad - 1;
        int start = r / 2;

        Location sL = e.getBlock().getLocation();

        player.getWorld().createExplosion(sL, 0f); // Create a fake explosion

        sL.setX(sL.getX() - start);
        sL.setY(sL.getY() - start);
        sL.setZ(sL.getZ() - start);

        for (int x = 0; x < locRad; x++)
            for (int y = 0; y < locRad; y++)
                for (int z = 0; z < locRad; z++)
                    if ((!(x == 0 && y == 0 && z == 0)) && (!(x == r && y == 0 && z == 0)) && (!(x == 0 && y == r && z == 0)) && (!(x == 0 && y == 0 && z == r)) && (!(x == r && y == r && z == 0))
                            && (!(x == 0 && y == r && z == r)) && (!(x == r && y == 0 && z == r)) && (!(x == r && y == r && z == r)))
                        locations.add(new Location(sL.getWorld(), sL.getX() + x, sL.getY() + y, sL.getZ() + z));

        for (Location loc : locations) {
            String iMat = item.getType().toString();
            Block b = loc.getBlock();
            String bMat = b.getType().toString();

    		EnchantmentHandler handler = SolarEnchants.getInstance().enchantmentHandler;
    		
            if (isUsable(iMat, bMat))
                if (!loc.getBlock().getDrops(item).isEmpty())
                	if(!handler.hasEnchant(e.getPlayer().getInventory().getItemInMainHand(), CustomEnchants.TELEPATHY)) {
                		 if(handler.hasEnchant(e.getPlayer().getInventory().getItemInMainHand(), CustomEnchants.SMELTER)) {
                			 AutoSmelt.call(player, loc.getBlock());
                		 }else {
                             loc.getBlock().breakNaturally(item); 
                		 }
                	}else {
                            for (ItemStack i : loc.getBlock().getDrops(item)) {
                       		 if(handler.hasEnchant(e.getPlayer().getInventory().getItemInMainHand(), CustomEnchants.SMELTER)) {
                       			AutoSmelt.call(player, loc.getBlock());
                       			loc.getBlock().setType(Material.AIR);
                    		 }else {
                                 player.getInventory().addItem(i);
                                 loc.getBlock().setType(Material.AIR);
                    		 }
                            }
                	}
        		}

    }
	

	// Checks if the Material of the block (bMat) is intended to be mined by the
	// item's Material (iMat)
	private boolean isUsable(String iMat, String bMat) {
	    if ((iMat.endsWith("PICKAXE") && (bMat.contains("ORE") || (!bMat.contains("STAIRS") && bMat.contains("STONE")) || bMat.equals("STAINED_CLAY") || bMat.equals("NETHERRACK")))
	            || (iMat.endsWith("SPADE") && (bMat.contains("SAND") || bMat.equals("DIRT") || bMat.equals("SNOW_BLOCK") || bMat.equals("SNOW") || bMat.equals("MYCEL") || bMat.equals("CLAY")
	            || bMat.equals("GRAVEL") || bMat.equals("GRASS")))
	            || (iMat.endsWith("_AXE") && bMat.contains("LOG") || bMat.contains("PLANKS")) || (iMat.endsWith("HOE") && (bMat.equals("CROPS") || bMat.equals("POTATO") || bMat.equals("CARROT"))))
	        return true;
	    return false;
	}


	@Override
	public int price(int level) {
		return 1000000*level;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Blows up the surrounding blocks. enchantment abilities apply.";
	}

}
