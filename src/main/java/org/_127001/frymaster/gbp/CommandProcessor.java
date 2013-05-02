/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org._127001.frymaster.gbp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author frymaster
 */
class CommandProcessor implements CommandExecutor {

    private PermissionCoordinator pc;

    CommandProcessor(PermissionCoordinator pc) {
        this.pc = pc;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            String cmd = args[0].toLowerCase();
            if ("reload".equals(cmd)) {
                return reloadConfig(sender, args);
            } else if ("help".equals(cmd)) {
                return help(sender,args);
            }


        }
        return false;
    }

    private boolean reloadConfig(CommandSender sender, String[] args) {
        if (args.length == 1) {
            pc.getPlugin().getLogger().info("Shutting down and reloading permissions system");
            sender.sendMessage("Reloading permissions...");
            pc.shutdown();
            pc.startup();
            sender.sendMessage("Done");
            return true;
        }
        return false;
    }
    
    private boolean help(CommandSender sender, String[] args) {
        String[] t = {"/gpb help - this help screen","/gbp reload - reload all config files"};
        sender.sendMessage(t);
        return true;
    }
}
