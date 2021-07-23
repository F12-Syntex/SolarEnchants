package com.solarenchants.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.solarenchants.enchantments.RARITY;
import com.solarenchants.enchantments.SolarEnchantment;
import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.ComponentBuilder;
import com.solarenchants.utils.MessageUtils;

import net.milkbowl.vault.economy.Economy;

public class GeneralShop extends PagedGUI{

	public GeneralShop(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return MessageUtils.translateAlternateColorCodes("&cShop");
	}

	@Override
	public String permission() {
		// TODO Auto-generated method stub
		return permissions.DEFAULT;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 54;
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
		return false;
	}

	@Override
	public void onClickInventory(InventoryClickEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpenInventory(InventoryOpenEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCloseInventory(InventoryCloseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<PagedItem> Contents() {
		
		List<PagedItem> items = new ArrayList<PagedItem>();
		
		for(SolarEnchantment enchant : SolarEnchants.getInstance().enchantmentHandler.getSolarEnchantments()) {
			
				ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
	            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)book.getItemMeta();
	            meta.addStoredEnchant(enchant, 1, true);
	            
	            String name = MessageUtils.translateAlternateColorCodes(enchant.getKey().getKey());
	            
	            meta.setDisplayName(name);
	            
	            List<String> breakdown = new ArrayList<String>();
	            
	            String[] text = enchant.description().split(" ");
	            
	            StringBuilder builder = new StringBuilder();
	            
	            breakdown.add(ChatColor.STRIKETHROUGH + "&6======================================");
	            breakdown.add("&3Cost: &a$" + enchant.price(enchant.getStartLevel()) + "+");
	            breakdown.add("&3Grade: " + RARITY.getColour(enchant.rarity) + enchant.rarity.name());
	            breakdown.add("&3Target: &6" + enchant.target);
	            
	            
	            builder.append("&3Description: &e");
	            
	            for(int i = 0; i < text.length; i++) {
	            	builder.append("&e" + text[i] + " ");
	            	if(i % 8 == 0 && i != 0) {
	            		breakdown.add(builder.toString());
	            		builder = new StringBuilder();
	            	}
	            }
	            
	            if(!builder.toString().isEmpty()) {
	            	breakdown.add(builder.toString());
	            }

	            breakdown.add(ChatColor.STRIKETHROUGH + "&6======================================");
	            meta.setLore(ComponentBuilder.createLore(breakdown));
	            
	            book.setItemMeta(meta);	
	            
	            
				PagedItem item = new PagedItem(book, () -> {
					this.player.closeInventory();
					PagedGUI gui = new ItemShop(this.player, enchant);
					gui.open();
	            });
	            
	            items.add(item);
			}
			
		
		
		return items;
	}

	@Override
	public List<SpecialItem> SpecialContents() {
		
		final int farmCost = 500;
		
		List<SpecialItem> item = new ArrayList<SpecialItem>();
		
		ItemStack back = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
		ItemMeta meta = back.getItemMeta();
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes("&6Home"));
		back.setItemMeta(meta);
		
		ItemStack venilla = GenerateItem.getItem("&bVenilla", Material.BLUE_STAINED_GLASS_PANE);
		ItemStack farm = GenerateItem.getItem("&6Farm item", Material.ORANGE_STAINED_GLASS_PANE, "&5Cost: &a" + farmCost + "$");
		
		SpecialItem special = new SpecialItem(back, () -> {
			
			player.closeInventory();
			PagedGUI gui = new GeneralShop(player);
			gui.open();
			
		}, 46);
		

		SpecialItem venillaItem = new SpecialItem(venilla, () -> {
			
			player.closeInventory();
			PagedGUI gui = new VenillaGeneralShop(player);
			gui.open();
			
		}, 47);
		
		SpecialItem farmItem = new SpecialItem(farm, () -> {
			
        	Economy economy = SolarEnchants.getInstance().econ;
			double balance = economy.getBalance(this.player);
			
			if(balance < farmCost) {
				MessageUtils.inform(player, "&cSorry, you need &a$" + (farmCost-balance) + "&c more to make that purchase.");
				return;
			}
			
			if(this.player.getInventory().firstEmpty() == -1) {
				MessageUtils.inform(player, "&cSorry, you don't have enough space in your inventory.");
				return;
			}
			
			economy.withdrawPlayer(this.player, farmCost);
			MessageUtils.inform(player, "&apurchase complete!");
			
			Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "efa givefarmitem " + this.player.getName() + " 1");
			
		}, 48);
		
		item.add(special);
		item.add(venillaItem);
		item.add(farmItem);
		
		return item;
	}

	
	
}
