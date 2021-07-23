package com.solarenchants.enchantments;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.solarenchants.enchants.armour.Bunny;
import com.solarenchants.enchants.armour.Escapist;
import com.solarenchants.enchants.armour.Nightseeker;
import com.solarenchants.enchants.armour.Ninja;
import com.solarenchants.enchants.armour.Turtle;
import com.solarenchants.enchants.bow.Blaze;
import com.solarenchants.enchants.bow.Bombardment;
import com.solarenchants.enchants.bow.Fireworks;
import com.solarenchants.enchants.bow.Molotov;
import com.solarenchants.enchants.general.AutoSell;
import com.solarenchants.enchants.general.Unbreakable;
import com.solarenchants.enchants.tools.AutoSmelt;
import com.solarenchants.enchants.tools.Barbarian;
import com.solarenchants.enchants.tools.Curse;
import com.solarenchants.enchants.tools.Enderman;
import com.solarenchants.enchants.tools.Experience;
import com.solarenchants.enchants.tools.Explosive;
import com.solarenchants.enchants.tools.Freezing;
import com.solarenchants.enchants.tools.Haste;
import com.solarenchants.enchants.tools.OreDetector;
import com.solarenchants.enchants.tools.Poison;
import com.solarenchants.enchants.tools.Telepathy;
import com.solarenchants.utils.MessageUtils;

public class EnchantmentHandler {
	
	Solarholder enchantments;
	
	public EnchantmentHandler() {
		this.enchantments = new Solarholder(this);
	}
	
	public void initialize() {
		this.enchantments.enchantments.clear();
		this.enchantments.register(new Telepathy("telepathy", RARITY.COMMON, "Tools."));
		this.enchantments.register(new Haste("haste", RARITY.COMMON, "Tools."));
		this.enchantments.register(new AutoSmelt("smelter", RARITY.RARE, "Pickaxe."));
		this.enchantments.register(new Experience("experience", RARITY.DEMONIC, "Tools."));
		this.enchantments.register(new Poison("poison", RARITY.COMMON, "Sword."));
		this.enchantments.register(new Freezing("freezing", RARITY.COMMON, "Sword."));
		this.enchantments.register(new Unbreakable("unbreakable", RARITY.RARE, "Tools."));
		this.enchantments.register(new Nightseeker("nightseeker", RARITY.COMMON, "Helmet."));
		this.enchantments.register(new Escapist("escapist", RARITY.COMMON, "Boots."));
		this.enchantments.register(new Curse("curse", RARITY.DEMONIC, "Sword."));
		this.enchantments.register(new Turtle("turtle", RARITY.LEGENDARY, "Armour."));
		this.enchantments.register(new AutoSell("autosell", RARITY.RARE, "Tools."));
		this.enchantments.register(new OreDetector("oredetector", RARITY.MYTHICAL, "Pickaxe."));
		this.enchantments.register(new Barbarian("barbarian", RARITY.DEMONIC, "Sword."));
		this.enchantments.register(new Enderman("enderman", RARITY.MYTHICAL, "Sword."));
		this.enchantments.register(new Ninja("ninja", RARITY.RARE, "Armour."));
		this.enchantments.register(new Explosive("explosive", RARITY.MYTHICAL, "Pickaxe."));
		this.enchantments.register(new Molotov("molotov", RARITY.RARE, "Bow."));
		this.enchantments.register(new Blaze("blaze", RARITY.RARE, "Bow."));
		this.enchantments.register(new Bombardment("bombardment", RARITY.RARE, "Bow."));
		this.enchantments.register(new Fireworks("fireworks", RARITY.RARE, "Bow."));
		this.enchantments.register(new Bunny("bunny", RARITY.COMMON, "Boots."));
		this.enchantments.wrap();

		this.enchantments.initializeVenilla();
		
		Collections.sort(this.enchantments.enchantments, new RarityComparator());
		
	}

	public void register(Enchantment enchantment) {
		boolean registered = true;
		try {
			Field field = Enchantment.class.getDeclaredField("acceptingNew");
			field.setAccessible(true);
			field.set(null, true);
			Enchantment.registerEnchantment(enchantment);
		}catch (Exception e) {
			registered = false;
		}
		
		MessageUtils.sendConsoleMessage(enchantment.getKey().getKey()+ " -> " + MessageUtils.prettyBoolean(registered));	
	}
	
	public void unregisterAll() {
		this.getSolarEnchantments().forEach(i -> {
			HandlerList.unregisterAll(i);
		});
	}
	
	public boolean registered(Enchantment enchantment) {
		return Arrays.asList(Enchantment.values()).stream().collect(Collectors.toList()).contains(enchantment);
	}
	
	public List<SolarEnchantment> getSolarEnchantments() {
		return this.enchantments.enchantments;
	}
	
	public List<VennilaEnchantment> getVennilaEnchantments() {
		return this.enchantments.VennilaEnchantment;
	}
	
	
	public boolean hasEnchant(ItemStack stack, CustomEnchants enchants) {
		if(!stack.hasItemMeta()) return false;
		for(Enchantment o : stack.getEnchantments().keySet()) {
			if(o.getKey().getKey().equalsIgnoreCase(enchants.name().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
}
