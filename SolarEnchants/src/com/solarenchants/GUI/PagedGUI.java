package com.solarenchants.GUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.solarenchants.data.ConfigData;
import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.MessageUtils;

public abstract class PagedGUI extends ConfigData implements Listener{

	protected Player player;
	protected Inventory inv;
	
	public int page;
	
	private Map<Integer, Runnable> execution = new HashMap<Integer, Runnable>();
	
	public PagedGUI(Player player) {
		this.page = 1;
		this.player = player;
		SolarEnchants.instance.getServer().getPluginManager().registerEvents(this, SolarEnchants.instance);
		this.inv = Bukkit.createInventory(player, size(), name());
	}
	
	@EventHandler()
	public void onOpen(InventoryOpenEvent e) {
		if(e.getPlayer().getUniqueId() != this.player.getUniqueId()) return;
		if(!e.getPlayer().hasPermission(permission())) {
			MessageUtils.sendMessage(player, messages.INAVLID_PERMISSION);
			e.setCancelled(true);
			return;
		}
		onOpenInventory(e);
	}
	
	@EventHandler()
	public void onClose(InventoryCloseEvent e) {
		if(e.getPlayer().getUniqueId() != this.player.getUniqueId()) return;
		this.onCloseInventory(e);
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler()
	public void onClick(InventoryClickEvent e) {
		
		if(e.getWhoClicked().getUniqueId() != this.player.getUniqueId()) return;
		
		e.setCancelled(!canTakeItems());
		if(e.getCurrentItem() == null) {
			return;
		}
		this.onClickInventory(e);
		
		if(e.getSlot() == 45) {
			if(this.page > 1) {
				this.page--;
				this.refresh();
			}
		}
		
		if(e.getSlot() == 53) {
			int maxPage = this.Contents().size()/35;
			if(this.Contents().size() % 35 > 0) {
				maxPage++;
			}
			if(this.page < maxPage) {
				this.page++;
				this.refresh();
			}
		}
		
		int index = e.getSlot() + ((this.page-1) * 36);
		
		if(this.execution.containsKey(index) && e.getSlot() < 36) {
			this.execution.get(index).run();
		}
		
		for(SpecialItem i : this.SpecialContents()) {
			if(e.getSlot() == i.getSlot()) {
				i.getExecution().run();
			}
		}
		
	}
	
	public abstract String name();
	public abstract String permission();
	
	public abstract int size();
	
	public abstract Sound sound();
	public abstract float soundLevel();
	public abstract boolean canTakeItems();
	
	public abstract void onClickInventory(InventoryClickEvent e);
	public abstract void onOpenInventory(InventoryOpenEvent e);
	public abstract void onCloseInventory(InventoryCloseEvent e);
	public abstract List<PagedItem> Contents();
	public abstract List<SpecialItem> SpecialContents();
	
	public void open() {
		player.getWorld().playSound(player.getLocation(), sound(), soundLevel(), soundLevel());
		this.refresh();
		player.openInventory(inv);
	}


	public void refresh() {
		
		Bukkit.getServer().getScheduler().runTask(SolarEnchants.getInstance(), new Runnable() {
		    public void run() {

				inv.clear();
				ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
				fillEmpty(blackPane);		
			
				final List<PagedItem> contents = Contents();
				final List<SpecialItem> special = SpecialContents();
				
				int maxPage = contents.size()/35;
				
				if(contents.size() % 35 > 0) {
					maxPage++;
				}
				
				for(int i = 0; i < 36; i++) {
					int index = i + ((page-1) * 36);
					if(index < contents.size()) {
						PagedItem item = contents.get(index);
						inv.setItem(i, contents.get(index).getItem());
						execution.put(index, item.getExecution());
					}
				}
			
				for(int i = 36; i < 45; i++) {
					ItemStack emptyPane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
					inv.setItem(i, emptyPane);
				}
				for(int i = 45; i < 54; i++) {

					boolean flag = false;
					for(SpecialItem o : special) {
						if(o.getSlot() == i) {
							inv.setItem(i, o.getItem());
							flag = true;
						}
					}

					if(!flag) {
						ItemStack emptyPane = new ItemStack(Material.AIR, 1);
						inv.setItem(i, emptyPane);	
					}
					
				}
		
				
				if(page > 1) {
					ItemStack back = GenerateItem.getItem("&aBack", Material.GREEN_STAINED_GLASS_PANE, "&3Takes you back to page " + (page-1));
					inv.setItem(45, back);
				}else {
					ItemStack back = GenerateItem.getItem("&aBack", Material.RED_STAINED_GLASS_PANE, "&cThis is the first page!");
					inv.setItem(45, back);	
				}
				
				if(page < maxPage) {
					ItemStack next = GenerateItem.getItem("&aNext", Material.GREEN_STAINED_GLASS_PANE, "&3Takes you to page " + (page+1) + "!");
					inv.setItem(53, next);		
				}else {
					ItemStack next = GenerateItem.getItem("&aNext", Material.RED_STAINED_GLASS_PANE, "&cThis is the last page!");
					inv.setItem(53, next);	
				}
		    }
		});
			
	}
	
	
	public void addItem(int index, ItemStack item) {
		inv.setItem(index, item);
	}
	
	public void fillEmpty(ItemStack stack) {
		for(int i = 0; i < this.size(); i++) {
			if(this.inv.getItem(i) == null) {
				this.inv.setItem(i, stack);
			}
		}
	}
	
	
}
