package com.solarenchants.utils;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class Misc {
	
	
	   private static Color fireworkColor(int i) {
	        switch (i) {
	        default:
	        case 1:
	            return Color.SILVER;
	        case 2:
	            return Color.AQUA;
	        case 3:
	            return Color.BLACK;
	        case 4:
	            return Color.BLUE;
	        case 5:
	            return Color.FUCHSIA;
	        case 6:
	            return Color.GRAY;
	        case 7:
	            return Color.GREEN;
	        case 8:
	            return Color.LIME;
	        case 9:
	            return Color.MAROON;
	        case 10:
	            return Color.NAVY;
	        case 11:
	            return Color.OLIVE;
	        case 12:
	            return Color.ORANGE;
	        case 13:
	            return Color.PURPLE;
	        case 14:
	            return Color.RED;
	        case 15:
	            return Color.YELLOW;
	        case 16:
	            return Color.TEAL;

	        }
	    }

    public static Firework shootFirework(Location loc, Random rand) {
        int type = rand.nextInt(5) + 1;
        Firework firework = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        org.bukkit.FireworkEffect.Type ft = null;
        switch (type) {
        case 1:
            ft = org.bukkit.FireworkEffect.Type.BALL;
            break;
        case 2:
            ft = org.bukkit.FireworkEffect.Type.BALL_LARGE;
            break;
        case 3:
            ft = org.bukkit.FireworkEffect.Type.BURST;
            break;
        case 4:
            ft = org.bukkit.FireworkEffect.Type.CREEPER;
            break;
        case 5:
            ft = org.bukkit.FireworkEffect.Type.STAR;
            break;
        }
        FireworkEffect effect = FireworkEffect.builder().flicker(rand.nextBoolean()).withColor(fireworkColor(rand.nextInt(16) + 1)).withFade(fireworkColor(rand.nextInt(16) + 1))
                .trail(rand.nextBoolean()).with(ft).trail(rand.nextBoolean()).build();
        meta.addEffect(effect);
        firework.setFireworkMeta(meta);
        return firework;
    }

}
