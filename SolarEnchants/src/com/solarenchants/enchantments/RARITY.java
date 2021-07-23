package com.solarenchants.enchantments;

public enum RARITY{

	COMMON, RARE, DEMONIC, LEGENDARY, MYTHICAL;
	
	public static String getColour(RARITY rarity) {
		switch (rarity) {
		case COMMON:
			return "&a";
		case RARE:
			return "&e";
		case DEMONIC:
			return "&c";
		case MYTHICAL:
			return "&5";
		case LEGENDARY:
			return "&6";
		}
		
		return "&2";
	}
	
}
