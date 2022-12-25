package io.myztic.streaksystem;

import io.myztic.core.bukkit.LoggerUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyzticStreakSystem extends JavaPlugin {

    private static Plugin plugin;
    private static final String PLUGIN_PREFIX = "[MyzticStreakSystem]";

    public static Plugin getInst() { return plugin; }

    @Override
    public void onEnable() {
        LoggerUtils.logInfo(PLUGIN_PREFIX, "Starting MyzticStreakSystem");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getPrefix() {
        return PLUGIN_PREFIX;
    }
}
