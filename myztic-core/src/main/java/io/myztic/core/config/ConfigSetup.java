package io.myztic.core.config;

import io.myztic.core.logging.LogUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigSetup {

    private ConfigSetup() { }

    public static File setupConfig(Plugin pluginInstance, String pluginPrefix, String configFileName) {
        File configFile = new File(pluginInstance.getDataFolder(), configFileName);
        if(!configFile.exists()) {
            LogUtil.logInfo(pluginPrefix, "Generated a new " + configFileName);
            pluginInstance.saveResource(configFileName, false);
            loadConfig(configFile).options().copyDefaults(true);
        }
        return configFile;
    }

    public static FileConfiguration loadConfig(File configFile) {
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static FileConfiguration reloadConfig(File configFile) {
        return YamlConfiguration.loadConfiguration(configFile);
    }

}
