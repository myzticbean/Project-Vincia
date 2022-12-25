package io.myztic.worldmanager;

import io.myztic.core.bukkit.LoggerUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyzticWorldManager extends JavaPlugin {

    private static Plugin plugin;
    private static final String PLUGIN_PREFIX = "[MyzticWorldManager]";

    public static Plugin getInst() { return plugin; }

    @Override
    public void onEnable() {
        LoggerUtils.logInfo(PLUGIN_PREFIX, "Starting MyzticWorldManager");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getPrefix() {
        return PLUGIN_PREFIX;
    }
}
