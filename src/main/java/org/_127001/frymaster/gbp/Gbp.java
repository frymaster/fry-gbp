package org._127001.frymaster.gbp;

import java.io.IOException;
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

        pc.startup();
        
        getServer().getPluginManager().registerEvents(new EventProcessor(pc), this);
        getCommand("gbp").setExecutor(new CommandProcessor(pc));
    }

    @Override
    public void onDisable() {
        pc.shutdown();
        pc = null;
    }
}
