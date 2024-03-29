package io.myztic.core;

import io.myztic.core.commands.corecommand.MyzticCoreCmd;
import io.myztic.core.config.coreconfig.MainConfigSetup;
import io.myztic.core.database.sql.MySQL;
import io.myztic.core.logging.LogUtil;
import io.myztic.core.scheduled_tasks.TaskTimer30Sec;
import io.myztic.core.scheduled_tasks.TaskTimer5Min;
import io.myztic.core.scheduled_tasks.TaskTimer5Sec;
import io.myztic.core.scheduled_tasks.TimedTaskHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyzticCore extends JavaPlugin {

    private static Plugin plugin;
    private static final String PLUGIN_PREFIX = "[MyzticCore]";

    @Override
    public void onEnable() {
        setPluginInst();
        MainConfigSetup.getInst().setupConfig();
        MainConfigSetup.getInst().loadConfig();
        registerTaskTimers();
        registerCommands();
        MySQL.getInst().connect();

        // @TODO: for testing, to be removed later
//        TimedTaskHandler.addTaskTo5SecTaskQueue();
    }

    private void setPluginInst() {
        plugin = this;
    }

    public static Plugin getInst() { return plugin; }

    @Override
    public void onDisable() {
        MySQL.getInst().disconnect();
        Bukkit.getScheduler().cancelTasks(this);
    }

    public static String getPrefix() {
        return PLUGIN_PREFIX;
    }

    private void registerTaskTimers() {
        LogUtil.logInfo(PLUGIN_PREFIX, "Registering timed tasks");
        Bukkit.getServer().getScheduler().runTaskTimer(this, new TaskTimer5Sec(), 0, 5*20L);
        Bukkit.getServer().getScheduler().runTaskTimer(this, new TaskTimer30Sec(), 0, 30*20L);
        Bukkit.getServer().getScheduler().runTaskTimer(this, new TaskTimer5Min(), 0, 5*60*20L);
    }

    private void registerCommands() {
        new MyzticCoreCmd().createCoreCommand();
    }
}
