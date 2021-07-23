package com.solarenchants.GUI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.solarenchants.enchantments.VennilaEnchantment;
import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.MessageUtils;

public class VenillaGeneralShop extends PagedGUI{

	public VenillaGeneralShop(Player player) {
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
		
		for(VennilaEnchantment enchant : SolarEnchants.getInstance().enchantmentHandler.getVennilaEnchantments()) {
			
				ItemStack stack = GenerateItem.getItem("&c" + enchant.getEnchantment().getKey().getKey(), Material.GREEN_STAINED_GLASS_PANE);
				
				PagedItem item = new PagedItem(stack, () -> {
					this.player.closeInventory();
					PagedGUI gui = new VenillaItemShop(this.player, enchant);
					gui.open();
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
		
		ItemStack venilla = GenerateItem.getItem("&bVenilla", Material.BLUE_STAINED_GLASS_PANE);
		
		SpecialItem special = new SpecialItem(back, () -> {
			
			player.closeInventory();
			PagedGUI gui = new VenillaGeneralShop(player);
			gui.open();
			
		}, 46);
		

		SpecialItem venillaItem = new SpecialItem(venilla, () -> {
			
			player.closeInventory();
			PagedGUI gui = new VenillaGeneralShop(player);
			gui.open();
			
		}, 47);
		
		item.add(special);
		item.add(venillaItem);
		
		return item;
	}

	
	
}
