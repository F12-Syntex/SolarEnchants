package com.solarenchants.GUI;

import java.util.ArrayList;
import java.util.List;

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
import com.solarenchants.utils.Numbers;

import net.milkbowl.vault.economy.Economy;

public class ItemShop extends PagedGUI{

	private SolarEnchantment enchant;
	
	public ItemShop(Player player, SolarEnchantment enchantment) {
		super(player);
		this.enchant = enchantment;
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
		
		for(int level = enchant.getStartLevel(); level <= enchant.getMaxLevel(); level++) {
				
				ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
	            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)book.getItemMeta();
	            meta.addStoredEnchant(enchant, level, true);
	            
	            String name = MessageUtils.translateAlternateColorCodes(enchant.getKey().getKey() + " " + Numbers.integerToRoman(level));
	            
	            int price = enchant.price(level);
	            
	            meta.setDisplayName(name);
	            
	            List<String> breakdown = new ArrayList<String>();
	            
	            String[] text = enchant.description().split(" ");
	            
	            StringBuilder builder = new StringBuilder();
	            
	            breakdown.add(ChatColor.STRIKETHROUGH + "&6======================================");
	            breakdown.add("&3Cost: &a$" + price);
	            breakdown.add("&3Grade: " + RARITY.getColour(enchant.rarity) + enchant.rarity.name());
	            
	            
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
	            
	            final int lvl = level;
	            
				PagedItem item = new PagedItem(book, () -> {
	            	
	            	Economy economy = SolarEnchants.getInstance().econ;
	    			double balance = economy.getBalance(this.player);
	    			
	    			if(balance < price) {
	    				MessageUtils.inform(player, "&cSorry, you need &a$" + (price-balance) + "&c more to make that purchase.");
	    				return;
	    			}
	    			if(this.player.getInventory().firstEmpty() == -1) {
	    				MessageUtils.inform(player, "&cSorry, you don't have enough space in your inventory.");
	    				return;
	    			}
	    			
	    			
					ItemStack newBook = new ItemStack(Material.ENCHANTED_BOOK);
		            EnchantmentStorageMeta Newmeta = (EnchantmentStorageMeta)newBook.getItemMeta();
		            Newmeta.setDisplayName("Enchanted book");
		            Newmeta.addStoredEnchant(enchant, lvl, true);
		            Newmeta.setLore(ComponentBuilder.createLore("&7" + enchant.getKey().getKey() + " " + Numbers.integerToRoman(lvl)));
	    			newBook.setItemMeta(Newmeta);
		            
	    			economy.withdrawPlayer(player, price);
	    			
	    			player.getInventory().addItem(newBook);
	    			
	    			MessageUtils.inform(player, "&apurchase complete!");
	    			
	            });
	            
	            items.add(item);
			}
		
		
		return items;
	}


	@Override
	public List<SpecialItem> SpecialContents() {
		
		List<SpecialItem> item = new ArrayList<SpecialItem>();
		
		ItemStack back = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
		ItemMeta meta = back.getItemMeta();
		
		meta.setDisplayName(MessageUtils.translateAlternateColorCodes("&6Home"));
		back.setItemMeta(meta);
		
		SpecialItem special = new SpecialItem(back, () -> {
			
			player.closeInventory();
			PagedGUI gui = new GeneralShop(player);
			gui.open();
			
		}, 46);
		
		item.add(special);
		
		return item;
	}
}
