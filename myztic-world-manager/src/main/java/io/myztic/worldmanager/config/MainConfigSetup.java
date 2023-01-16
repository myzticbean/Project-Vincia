package io.myztic.worldmanager.config;

import io.myztic.core.config.ConfigSetup;
import io.myztic.core.logging.LogUtil;
import io.myztic.worldmanager.MyzticWorldManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class MainConfigSetup {
    private static final String MAIN_CONFIG_NAME = "config.yml";
    private static final String DATAPACK_FOLDER_NAME = "datapacks";
    private File mainConfigFile = null;
    private FileConfiguration configFileConfig = null;
    private static MainConfigSetup mainConfigSetup = null;

    public static MainConfigSetup getInst() {
        if(mainConfigSetup == null)
            mainConfigSetup = new MainConfigSetup();
        return mainConfigSetup;
    }

    public void setupConfig() {
        mainConfigFile = ConfigSetup.setupConfig(MyzticWorldManager.getInst(), MyzticWorldManager.getPrefix(), MAIN_CONFIG_NAME);
        if(ConfigSetup.setupResourceFolderWithName(DATAPACK_FOLDER_NAME, MyzticWorldManager.getInst()))
            LogUtil.logInfo(MyzticWorldManager.getPrefix(), "Folder exists with name: " + DATAPACK_FOLDER_NAME);
        else
            LogUtil.logInfo(MyzticWorldManager.getPrefix(), "Failed to create folder: " + DATAPACK_FOLDER_NAME);
    }

    public void loadConfig() {
        configFileConfig = ConfigSetup.loadConfig(mainConfigFile);
    }

    public FileConfiguration get() {
        return configFileConfig;
    }

}
