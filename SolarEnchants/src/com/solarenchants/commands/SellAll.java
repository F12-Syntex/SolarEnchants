
package com.solarenchants.commands;

import org.bukkit.entity.Player;

import com.solarenchants.main.SolarEnchants;
import com.solarenchants.utils.MessageUtils;

public class SellAll extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	if(SolarEnchants.getInstance().CommandManager.players.contains(player.getUniqueId())){
    		SolarEnchants.getInstance().CommandManager.players.remove(player.getUniqueId());
    		MessageUtils.inform(player, " &aAutoSell disabled!");
    	}else {
    		SolarEnchants.getInstance().CommandManager.players.add(player.getUniqueId());
    		MessageUtils.inform(player, " &aAutoSell enabled!");
    	}
    }

    @Override

    public String name() {
        return "sellall";
    }

    @Override
    public String info() {
        return "Auto sell.";
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