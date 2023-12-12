package io.myztic.core.config;

import io.myztic.core.exceptions.UtilityClassException;
import io.myztic.core.logging.LogUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfigSetup {

    private ConfigSetup() { throw new UtilityClassException(); }

    public static File setupConfig(@NotNull Plugin pluginInstance, String pluginPrefix, @NotNull String configFileName) {
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

    public static boolean setupResourceFolderWithName(@NotNull String folderName, @NotNull Plugin pluginInstance) {
        File resourceFolder = new File(pluginInstance.getDataFolder(), folderName);
        if(resourceFolder.exists())
            return true;
        else
            return resourceFolder.mkdir();
    }

}
