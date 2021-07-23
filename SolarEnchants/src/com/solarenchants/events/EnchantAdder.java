package com.solarenchants.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.ComponentBuilder;
import com.solarenchants.utils.MessageUtils;
import com.solarenchants.utils.Numbers;

public class EnchantAdder extends SubEvent{

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "enchanter";
	}

	@Override
	public String bypass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub	
		return "Adds enchant books to your items.";
	}

	
	@SuppressWarnings("deprecation")
	public void onInventoryModify(InventoryClickEvent e) {
		
		ItemStack target = e.getClickedInventory().getItem(e.getSlot());
		ItemStack enchantment = e.getCursor();
		
		if(target == null) return;
		if(enchantment == null) return;
		if(enchantment.getType() != Material.ENCHANTED_BOOK) return;
		if(!(e.getWhoClicked() instanceof Player)) return;
		
		System.out.println("Target: " + target.getType().name());
		System.out.println("enchantment: " + enchantment.getType().name());
		
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta)enchantment.getItemMeta();
		
		Map<Enchantment, Integer> enchantments = meta.getStoredEnchants();
		
		for(Enchantment i : enchantments.keySet()) {
			if(!i.canEnchantItem(target)) {
				MessageUtils.inform((Player)e.getWhoClicked(), "&cYou cannot enchant that item!");
				return;
			}
		}
		
		e.setCancelled(true);
		e.setCurrentItem(new ItemStack(Material.AIR));
		
		ItemMeta targetMeta = target.getItemMeta();
		
		List<String> lore = new ArrayList<String>();
		
		for(Enchantment i : enchantments.keySet()) {
			
			int level = enchantments.get(i);
			
			System.out.println("Adding: " + i.getKey().getKey() + " : " + level);
			
			target.addEnchantment(i, level);
			targetMeta.addEnchant(i, level, true);
			
			lore.add("&7" + i.getKey().getKey() + " " + Numbers.integerToRoman(level));
		}
		
		targetMeta.setLore(ComponentBuilder.createLore(lore));
		target.setItemMeta(targetMeta);
		
		e.setCurrentItem(new ItemStack(Material.AIR));
		e.setCursor(new ItemStack(Material.AIR));
		
		MessageUtils.inform((Player)e.getWhoClicked(), "&aItem enchanted!");
		e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
		
		
	}

	@EventHandler
	public void modify(InventoryClickEvent e) {
		if(SolarEnchants.getInstance().CommandManager.players.contains(e.getWhoClicked().getUniqueId())) {
			if(((Player)e.getWhoClicked()).getInventory().firstEmpty() == -1) {
				((Player)e.getWhoClicked()).chat("/sellall");
			}
		}
	}
	
}
