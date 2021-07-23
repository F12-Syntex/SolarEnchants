package com.solarenchants.enchantments;

@FunctionalInterface
public interface Price {
	public abstract int price(int level);
}
