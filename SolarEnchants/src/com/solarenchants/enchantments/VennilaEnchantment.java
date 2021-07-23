package com.solarenchants.enchantments;

import org.bukkit.enchantments.Enchantment;

public class VennilaEnchantment {

	private int startLevel;
	private int maxLevel;
	private Enchantment enchantment;
	private Price price;
	
	public VennilaEnchantment(int startLevel, int maxLevel, Enchantment enchantment, Price price) {
		this.startLevel = startLevel;
		this.maxLevel = maxLevel;
		this.enchantment = enchantment;
		this.price = price;
	}

	public int getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public void setEnchantment(Enchantment enchantment) {
		this.enchantment = enchantment;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}
	
}
