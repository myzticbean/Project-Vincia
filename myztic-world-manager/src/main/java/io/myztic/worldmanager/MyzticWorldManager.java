package io.myztic.worldmanager;

import io.myztic.core.logging.LogUtil;
import io.myztic.core.scheduled_tasks.TimedTaskHandler;
import io.myztic.worldmanager.commands.MyzticWMCmd;
import io.myztic.worldmanager.config.MainConfigSetup;
import io.myztic.worldmanager.external.MVCoreHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyzticWorldManager extends JavaPlugin {

    private static Plugin plugin;
    private static final String PLUGIN_PREFIX = "[MyzticWorldManager]";
    private static final String MV_CORE_PLUGIN_NAME = "Multiverse-Core";

    public static Plugin getInst() { return plugin; }

    @Override
    public void onEnable() {
        setPluginInst();
        initConfig();
        checkDependencies();
        registerCommands();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }

    public static String getPrefix() {
        return PLUGIN_PREFIX;
    }

    private void setPluginInst() {
        plugin = this;
    }

    private void initConfig() {
        MainConfigSetup.getInst().setupConfig();
        MainConfigSetup.getInst().loadConfig();
    }

    private void checkDependencies() {
        TimedTaskHandler.addTaskTo30SecTaskQueue(() -> {
            LogUtil.logInfo(getPrefix(), "Checking " + MV_CORE_PLUGIN_NAME + " dependency...");
            if(!Bukkit.getPluginManager().isPluginEnabled(MV_CORE_PLUGIN_NAME)) {
                LogUtil.logError(getPrefix(), MV_CORE_PLUGIN_NAME + " is not installed. Disabling plugin...");
                Bukkit.getPluginManager().disablePlugin(this);
            } else {
                if(MVCoreHandler.getInst() != null)
                    LogUtil.logInfo(getPrefix(), "Hooked with " + MV_CORE_PLUGIN_NAME);
            }
        });
    }

    public static String getMvCorePluginName() {
        return MV_CORE_PLUGIN_NAME;
    }

    public void registerCommands() {
        new MyzticWMCmd().createWMCommand();
    }
}
