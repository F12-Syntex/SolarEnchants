package com.solarenchants.enchantments;

import java.util.Comparator;

public class RarityComparator implements Comparator<SolarEnchantment>{
	
    private int getPos(SolarEnchantment rarity) {
        switch (rarity.rarity) {
		case COMMON:
			return 0;
		case RARE:
			return 1;
		case DEMONIC:
			return 2;
		case LEGENDARY:
			return 3;
		case MYTHICAL:
			return 4;
        default:
            return Integer.MAX_VALUE;
        }
    }
	
    @Override
    public int compare(SolarEnchantment o1, SolarEnchantment o2) {
        return Integer.compare(getPos(o1), getPos(o2));
    }

}
