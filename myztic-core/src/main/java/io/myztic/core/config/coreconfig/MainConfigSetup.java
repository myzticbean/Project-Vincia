package io.myztic.core.config.coreconfig;

import io.myztic.core.MyzticCore;
import io.myztic.core.config.ConfigSetup;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public final class MainConfigSetup {

    private static final String MAIN_CONFIG_NAME = "config.yml";
    private File mainConfigFile = null;
    private FileConfiguration configFileConfiguration = null;
    private static MainConfigSetup mainConfigSetup = null;

    public static MainConfigSetup getInst() {
        if(mainConfigSetup == null)
            mainConfigSetup = new MainConfigSetup();
        return mainConfigSetup;
    }

    public void setupConfig() {
        mainConfigFile = ConfigSetup.setupConfig(MyzticCore.getInst(), MyzticCore.getPrefix(), MAIN_CONFIG_NAME);
    }

    public void loadConfig() {
        configFileConfiguration = ConfigSetup.loadConfig(mainConfigFile);
    }

    public FileConfiguration get() {
        return configFileConfiguration;
    }


}
