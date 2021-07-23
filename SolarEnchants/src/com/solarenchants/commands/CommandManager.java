package com.solarenchants.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.solarenchants.cooldown.CooldownUser;
import com.solarenchants.cooldown.Cooldowns;
import com.solarenchants.data.ConfigData;
import com.solarenchants.main.SolarEnchants;
import com.solarenchants.tags.TagFactory;
import com.solarenchants.utils.MessageUtils;

public class CommandManager extends ConfigData implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
    public ArrayList<UUID> players = new ArrayList<UUID>();

    private SolarEnchants plugin;

    //Sub Commands
    public String main = "solar";
    
    public void setup(SolarEnchants plugin) {
    	this.setPlugin(plugin);
    	plugin.getCommand(main).setExecutor(this);
        commands.add(new Help());
        commands.add(new Reload());
        commands.add(new Shop());
        commands.add(new SellAll());
        commands.add(new Enchanter());
    }


    public ArrayList<SubCommand> getCommands(){
    	return commands;
    }

    
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {

            sender.sendMessage(messages.INAVLID_ENTITY);

            return true;

        }

        Player player = (Player) sender;
        
    	try {

        if (command.getName().equalsIgnoreCase(main)) {

            if (args.length == 0) {
            	
            	CooldownUser user = SolarEnchants.getInstance().cooldownManager.getUser(player.getUniqueId());
            	
            	SubCommand cmd = new Help();
            	
            	if(!player.hasPermission(cmd.permission())) {
		    		MessageUtils.sendMessage(player, messages.INAVLID_PERMISSION);
		    		return true;
		        }
            	
            	
            	String key = cmd.name();

            	int timer = user.getTime(key);
            	
            	if(timer <= 0 || player.hasPermission(permissions.BYPASS)) {
            	
            		cmd.onCommand(player, args);
            	  
                	user.reset(key);
                
            	}else {
                	
                	TagFactory tagHelper = TagFactory.instance(Cooldowns.instance().message);
                
                	tagHelper.setCooldown(timer);
                	
                	MessageUtils.sendMessage(player, tagHelper.parse());
                }
            	
                return true;

            }

            SubCommand target = this.get(args[0]);

            if (target == null) {

                player.sendMessage(messages.INVALID_SYNTEX);

                return true;

            }
            
		    if(!player.hasPermission(target.permission())) {
		    		MessageUtils.sendMessage(player, messages.INAVLID_PERMISSION);
		    		return true;
		    }

            ArrayList<String> arrayList = new ArrayList<String>();

            arrayList.addAll(Arrays.asList(args));

            arrayList.remove(0);
            
            try{
            	
            	CooldownUser user = SolarEnchants.getInstance().cooldownManager.getUser(player.getUniqueId());
            	
            	String key = args[0].trim();

            	int timer = user.getTime(key);
            	
            	if(timer <= 0 || player.hasPermission(permissions.BYPASS)) {
            		
            	    target.onCommand(player, args);
            	    
            	    user.reset(key);
                
            	}else {
                	
                	TagFactory tagHelper = TagFactory.instance(Cooldowns.instance().message);
                
                	tagHelper.setCooldown(timer);
                	
                	MessageUtils.sendMessage(player, tagHelper.parse());
                }
            	
            
            
            }catch (Exception e){
                player.sendMessage(messages.ERROR);
                e.printStackTrace();
            }

        }


    }catch(Throwable e) {
        player.sendMessage(messages.ERROR);
        e.printStackTrace();
    }

        return true;
    
    }



    private SubCommand get(String name) {

        Iterator<SubCommand> subcommands = commands.iterator();

        while (subcommands.hasNext()) {

            SubCommand sc = (SubCommand) subcommands.next();


            if (sc.name().equalsIgnoreCase(name)) {

                return sc;

            }


            String[] aliases;

            int length = (aliases = sc.aliases()).length;



            for (int var5 = 0; var5 < length; ++var5) {

                String alias = aliases[var5];

                if (name.equalsIgnoreCase(alias)) {

                    return sc;

                }

            }

        }

        return null;

    }


	public SolarEnchants getPlugin() {
		return plugin;
	}


	public void setPlugin(SolarEnchants plugin) {
		this.plugin = plugin;
	}

}