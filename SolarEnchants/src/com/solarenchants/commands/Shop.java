
package com.solarenchants.commands;

import org.bukkit.entity.Player;

import com.solarenchants.GUI.PagedGUI;

public class Shop extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    
    	PagedGUI gui = new com.solarenchants.GUI.GeneralShop(player);
    	gui.open();
    	
    }

    @Override

    public String name() {
        return "shop";
    }

    @Override
    public String info() {
        return "Opens the shop";
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