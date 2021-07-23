package com.solarenchants.enchantments;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.enchantments.Enchantment;

import com.solarenchants.utils.MessageUtils;

public class Solarholder {
	
	private EnchantmentHandler handler;
	public List<SolarEnchantment> enchantments;
	public List<VennilaEnchantment> VennilaEnchantment;
	
	public Solarholder(EnchantmentHandler enchantmentHandler) {
		enchantments = new ArrayList<SolarEnchantment>();
		this.setHandler(enchantmentHandler);
		this.setVennilaEnchantment(new ArrayList<VennilaEnchantment>());
	}
	
	
	public void register(SolarEnchantment enchantment) {
		this.enchantments.add(enchantment);
	}
	
	public void wrap() {
		enchantments.stream().filter(i -> !handler.registered(i)).forEach(handler::register);
		MessageUtils.sendConsoleMessage("&cRegistered " + handler.getSolarEnchantments().size() + " custom enchantments.");	
	}

	public void initializeVenilla() {
		this.VennilaEnchantment.clear();
		
		this.VennilaEnchantment.add(new VennilaEnchantment(1, 100, Enchantment.LOOT_BONUS_BLOCKS, new Price() {
			@Override
			public int price(int level) {
				if(level == 1) return 2500 * level;
				return price((level-1)) + (2500 * level);
			}
		}));
		
		this.VennilaEnchantment.add(new VennilaEnchantment(1, 100, Enchantment.LOOT_BONUS_MOBS, new Price() {
			@Override
			public int price(int level) {
				if(level == 1) return 2500 * level;
				return price((level-1)) + (2500 * level);
			}
		}));
		
		this.VennilaEnchantment.add(new VennilaEnchantment(1, 100, Enchantment.ARROW_DAMAGE, new Price() {
			@Override
			public int price(int level) {
				if(level == 1) return 500 * level;
				return price((level-1)) + (5000 * level);
			}
		}));
		
		this.VennilaEnchantment.add(new VennilaEnchantment(1, 100, Enchantment.PROTECTION_ENVIRONMENTAL, new Price() {
			@Override
			public int price(int level) {
				if(level == 1) return 25000 * level;
				return price((level-1)) + (2500 * level);
			}
		}));
		
		this.VennilaEnchantment.add(new VennilaEnchantment(1, 100, Enchantment.DIG_SPEED, new Price() {
			@Override
			public int price(int level) {
				if(level == 1) return 2500 * level;
				return price((level-1)) + (5000 * level);
			}
		}));
		
		
	}

	public EnchantmentHandler getHandler() {
		return handler;
	}


	public void setHandler(EnchantmentHandler handler) {
		this.handler = handler;
	}


	public List<VennilaEnchantment> getVennilaEnchantment() {
		return VennilaEnchantment;
	}


	public void setVennilaEnchantment(List<VennilaEnchantment> vennilaEnchantment) {
		VennilaEnchantment = vennilaEnchantment;
	}

}
