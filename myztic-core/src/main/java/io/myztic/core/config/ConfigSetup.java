package io.myztic.core.config;

import io.myztic.core.MyzticCore;
import io.myztic.core.bukkit.LoggerUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigSetup {

    private static File myzticCoreConfigFile = null;
    private static FileConfiguration configFileConfiguration = null;

    private ConfigSetup() { }

    public static File setupConfig(Plugin pluginInstance, String pluginPrefix, String configFileName) {
        File configFile = new File(pluginInstance.getDataFolder(), configFileName);
        if(!configFile.exists()) {
            try {
                boolean isConfigGenerated = configFile.createNewFile();
                if(isConfigGenerated) {
                    LoggerUtils.logInfo(pluginPrefix, "Generated a new " + configFileName);
                }
            } catch(IOException e) {
                LoggerUtils.logError(pluginPrefix, "Error generating " + configFileName, e);
            }
        }
        if(pluginInstance instanceof MyzticCore) {
            myzticCoreConfigFile = configFile;
        }
        return configFile;
    }

    public static FileConfiguration loadConfig(File configFile) {
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static FileConfiguration get() {
        return configFileConfiguration;
    }

    public static FileConfiguration reloadConfig(File configFile) {

        return YamlConfiguration.loadConfiguration(configFile);
    }

}
