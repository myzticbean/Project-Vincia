package io.myztic.core;

import io.myztic.core.bukkit.LoggerUtils;
import io.myztic.core.config.ConfigProvider;
import io.myztic.core.scheduled_tasks.TaskTimer30Sec;
import io.myztic.core.scheduled_tasks.TaskTimer5Min;
import io.myztic.core.scheduled_tasks.TaskTimer5Sec;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyzticCore extends JavaPlugin {

    private static Plugin plugin;
    private static final String PLUGIN_PREFIX = ConfigProvider.getInst().PLUGIN_PREFIX;

    public static Plugin getInst() { return plugin; }
    @Override
    public void onEnable() {
        LoggerUtils.logInfo(PLUGIN_PREFIX, "Starting MyzticCore");
        registerTaskTimers();
    }

    @Override
    public void onDisable() {
        LoggerUtils.logInfo(PLUGIN_PREFIX, "Shutting down MyzticCore");
    }

    public static String getPrefix() {
        return PLUGIN_PREFIX;
    }

    private void registerTaskTimers() {
        LoggerUtils.logInfo(PLUGIN_PREFIX, "Register timed tasks");
        Bukkit.getServer().getScheduler().runTaskTimer(this, new TaskTimer5Sec(), 0, 5*60*20L);
        Bukkit.getServer().getScheduler().runTaskTimer(this, new TaskTimer30Sec(), 0, 30*60*20L);
        Bukkit.getServer().getScheduler().runTaskTimer(this, new TaskTimer5Min(), 0, 5*60*60*20L);
    }
}
