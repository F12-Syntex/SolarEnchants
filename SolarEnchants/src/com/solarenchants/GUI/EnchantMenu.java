package com.solarenchants.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.solarenchants.enchantments.EnchantmentHandler;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.enchantments.VennilaEnchantment;
import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.ComponentBuilder;
import com.solarenchants.utils.MessageUtils;
import com.solarenchants.utils.Numbers;
import com.solarenchants.utils.PlayerUtils;

public class EnchantMenu extends GUI{

	public EnchantMenu(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&3Enchanter");
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return permissions.DEFAULT;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public Sound sound() {
		// TODO Auto-generated method stub
		return Sound.BLOCK_LEVER_CLICK;
	}

	@Override
	public float soundLevel() {
		// TODO Auto-generated method stub
		return 1f;
	}

	@Override
	public boolean canTakeItems() {
		// TODO Auto-generated method stub
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClickInventory(InventoryClickEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getClickedInventory() == null) return;
		if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
		
		
		
		if(e.getSlot() == 0) e.setCancelled(true);
		if(e.getSlot() == 1) e.setCancelled(true);
		if(e.getSlot() == 3) e.setCancelled(true);
		if(e.getSlot() == 4) e.setCancelled(true);
		if(e.getSlot() == 5) e.setCancelled(true);
		if(e.getSlot() == 6) e.setCancelled(true);
		
		
		if(e.getSlot() == 8) {
			e.setCancelled(true);
			
			ItemStack target = this.inv.getItem(2);
			ItemStack enchantment = this.inv.getItem(7);
			
			if(target == null) return;
			if(enchantment == null) return;
			if(enchantment.getType() != Material.ENCHANTED_BOOK) return;
			if(!(e.getWhoClicked() instanceof Player)) return;
			
			
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta)enchantment.getItemMeta();
			
			Map<Enchantment, Integer> enchantments = meta.getStoredEnchants();
			
			EnchantmentHandler handler = SolarEnchants.getInstance().enchantmentHandler;
			
			boolean flag = true;
			
			for(Enchantment i : enchantments.keySet()) {
				if(!i.canEnchantItem(target)) {
					MessageUtils.inform((Player)e.getWhoClicked(), "&cYou cannot enchant that item!");
					return;
				}
				for(SolarEnchantment o : handler.getSolarEnchantments()) {
					if(i.getKey().getKey().equals(o.getKey().getKey())) {
						flag = true;
					}
				}
				for(VennilaEnchantment o : handler.getVennilaEnchantments()) {
					if(i.getKey().getKey().equals(o.getEnchantment().getKey().getKey())) {
						flag = true;
					}
				}
			}
			
			if(!flag) {
				MessageUtils.inform((Player)e.getWhoClicked(), "&cYou cannot use that book here!");
				return;
			}
			
			
			
			ItemMeta targetMeta = target.getItemMeta();
			
			List<String> lore = new ArrayList<String>();
			
			if(targetMeta.hasLore()) {
				lore = targetMeta.getLore();
			}
			
			for(Enchantment i : enchantments.keySet()) {
				int level = enchantments.get(i);
				target.addUnsafeEnchantment(i, level);
				targetMeta.addEnchant(i, level, true);
			
				String name = "&7" + i.getName() + " " + Numbers.integerToRoman(level);
				
				boolean isVennila = false;
				
				for(VennilaEnchantment o : handler.getVennilaEnchantments()) {
					if(i.getKey().getKey().equals(o.getEnchantment().getKey().getKey())) {
						isVennila = true;
					}
				}
				
				
				if(!isVennila) {
					if(i.getKey().getKey().toLowerCase().contains("unbreakable")) {
						targetMeta.setUnbreakable(true);
					}else {
					
						if(lore.isEmpty()) {
							lore.add(name);
						}else {
							boolean set = false;
							for(int x = 0; x < lore.size(); x++) {
								String prefix = i.getName() + " ";
								if(lore.get(x).startsWith(prefix)) {
									set = true;
									String numberText = lore.get(x).substring(prefix.length()).trim();
									int lvl = Numbers.romanToDecimal(numberText);
									System.out.println(lvl);
									if(lvl < level) {
										lore.set(x, name);
									}
								}
							}	
							if(!set) {
								lore.add(name);
							}
						}
						
					}					
				}
			
			}
			
			targetMeta.setLore(ComponentBuilder.createLore(lore));
			target.setItemMeta(targetMeta);
			
			this.inv.setItem(2, new ItemStack(Material.AIR));
			this.inv.setItem(7, target);
			
			
		}
	}

	@Override
	public void onOpenInventory(InventoryOpenEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		
		if(!(e.getPlayer() instanceof Player)) return;
		
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		if(this.inv.getItem(2) != null && this.inv.getItem(2).getType() != Material.AIR) items.add(this.inv.getItem(2));
		if(this.inv.getItem(7) != null && this.inv.getItem(7).getType() != Material.AIR) items.add(this.inv.getItem(7));
		
		if(PlayerUtils.canAddToInventory((Player) e.getPlayer(), items)) {
			items.forEach(e.getPlayer().getInventory()::addItem);
		}else {
			items.forEach(i -> e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), i));
		}
		
	}

	@Override
	public void Contents(Inventory inv) {
		
		ItemStack pane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		ItemStack accept = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
		
		inv.setItem(0, pane);
		inv.setItem(1, pane);
		inv.setItem(3, pane);
		inv.setItem(4, pane);
		inv.setItem(5, pane);
		inv.setItem(6, pane);
		inv.setItem(8, accept);
		
		
		
	}

}
