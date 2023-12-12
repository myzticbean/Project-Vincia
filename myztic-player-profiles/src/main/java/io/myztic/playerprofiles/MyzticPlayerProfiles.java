package io.myztic.playerprofiles;

import org.bukkit.plugin.java.JavaPlugin;

public final class MyzticPlayerProfiles extends JavaPlugin {

    private static final String PLUGIN_PREFIX = "[MyzticPlayerProfiles]";

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getPrefix() {
        return PLUGIN_PREFIX;
    }
}
