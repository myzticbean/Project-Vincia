package io.myztic.core;

import io.myztic.core.bukkit.LoggerUtils;
import io.myztic.core.config.coreconfig.MainConfigSetup;
import io.myztic.core.database.sql.MySQL;
import io.myztic.core.scheduled_tasks.TaskTimer30Sec;
import io.myztic.core.scheduled_tasks.TaskTimer5Min;
import io.myztic.core.scheduled_tasks.TaskTimer5Sec;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyzticCore extends JavaPlugin {

    private static Plugin plugin;
    private static final String PLUGIN_PREFIX = "[MyzticCore]";

    public static Plugin getInst() { return plugin; }
    @Override
    public void onEnable() {
        plugin = this;
        MainConfigSetup.getInst().setupConfig();
        MainConfigSetup.getInst().loadConfig();
        registerTaskTimers();
        MySQL.getInst().connect();
    }

    @Override
    public void onDisable() {
        MySQL.getInst().disconnect();
    }

    public static String getPrefix() {
        return PLUGIN_PREFIX;
    }

    private void registerTaskTimers() {
        LoggerUtils.logInfo(PLUGIN_PREFIX, "Registering timed tasks");
        Bukkit.getServer().getScheduler().runTaskTimer(this, new TaskTimer5Sec(), 0, 5*60*20L);
        Bukkit.getServer().getScheduler().runTaskTimer(this, new TaskTimer30Sec(), 0, 30*60*20L);
        Bukkit.getServer().getScheduler().runTaskTimer(this, new TaskTimer5Min(), 0, 5*60*60*20L);
    }
}
