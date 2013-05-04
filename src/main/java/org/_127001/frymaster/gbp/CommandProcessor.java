/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org._127001.frymaster.gbp;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author frymaster
 */
class CommandProcessor implements CommandExecutor {

    private PermissionCoordinator pc;
    private static final int MAX_TEXT_LEN = 53;

    CommandProcessor(PermissionCoordinator pc) {
        this.pc = pc;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            String cmd = args[0].toLowerCase();
            if ("reload".equals(cmd)) {
                return reloadConfig(sender, args);
            } else if ("list".equals(cmd)) {
                return listGroups(sender, args);
            } else if ("help".equals(cmd)) {
                return help(sender, args);
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
        String[] t = {"/gpb help - this help screen", "/gbp reload - reload all config files","/gbp list - list all groups"};
        sender.sendMessage(t);
        return true;
    }

    private boolean listGroups(CommandSender sender, String[] args) {
        Collection<FryGroup> groups = pc.getGroups();
        List<String> messages = new LinkedList();
        messages.add("Groups: (" + ChatColor.RED + "built-in, " + ChatColor.GREEN + "default, " + ChatColor.LIGHT_PURPLE + "both" + ChatColor.RESET + ")");
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (FryGroup group : groups) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            String name = group.getName();
            if (sb.length() + name.length() + 2 > MAX_TEXT_LEN) {
                messages.add(sb.toString());
                sb = new StringBuilder();
            }
            if (group.isDefaultGroup() && group.isMetaGroup()) {
                sb.append(ChatColor.LIGHT_PURPLE);
                sb.append(name);
                sb.append(ChatColor.RESET);
            } else if (group.isDefaultGroup()) {
                sb.append(ChatColor.GREEN);
                sb.append(name);
                sb.append(ChatColor.RESET);
            } else if (group.isMetaGroup()) {
                sb.append(ChatColor.RED);
                sb.append(name);
                sb.append(ChatColor.RESET);
            } else {
                sb.append(name);
            }
        }
        messages.add(sb.toString());
        sender.sendMessage(messages.toArray(new String[messages.size()]));
        return true;
    }
}
