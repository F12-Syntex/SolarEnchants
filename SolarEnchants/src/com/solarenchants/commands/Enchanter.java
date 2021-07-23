
package com.solarenchants.commands;

import org.bukkit.entity.Player;

import com.solarenchants.GUI.EnchantMenu;
import com.solarenchants.GUI.GUI;

public class Enchanter extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	GUI gui = new EnchantMenu(player);
    	gui.open();
    }

    @Override

    public String name() {
        return "enchanter";
    }

    @Override
    public String info() {
        return "The enchanter.";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return permissions.DEFAULT;	
	}
	

}