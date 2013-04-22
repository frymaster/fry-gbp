package org._127001.frymaster.gbp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.mcstats.Metrics;

// TODO: Implement commands to allow in-game altering of permissions instead of editing config file and using /reload
// TODO: per-world permissions
// TODO: Allow looking up group membership using SQL queries
// TODO: Vault integration
public final class Gbp extends JavaPlugin {

    private PermissionCoordinator pc;

    @Override
    public void onEnable() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().info("Failed to connect to mcstats");
            // Failed to submit the stats :-(
        }
        pc = new PermissionCoordinator(this);

        pc.recalculateGroups();
        getServer().getPluginManager().registerEvents(new EventProcessor(pc), this);

        //If we've been reloaded, players may already be online
        pc.addAllPlayers();
    }

    @Override
    public void onDisable() {
        pc.removeAllPlayers();
        pc = null;
    }
}
