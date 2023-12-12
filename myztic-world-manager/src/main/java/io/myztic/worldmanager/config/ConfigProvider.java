package io.myztic.worldmanager.config;

import io.myztic.core.bukkit.ChatUtil;
import org.apache.commons.lang3.StringUtils;

public class ConfigProvider {
    private static ConfigProvider configProvider = null;
    private final Double CONFIG_VERSION = MainConfigSetup.getInst().get().getDouble("config-version");
    public final String PLUGIN_PREFIX = getFormattedString(MainConfigSetup.getInst().get().getString("plugin-prefix"));
    public final boolean DEBUG_MODE = MainConfigSetup.getInst().get().getBoolean("debug-mode");
    public final String MAIN_WORLD_NAME = MainConfigSetup.getInst().get().getString("main-world.world-name");
    public final String MAIN_WORLD_SEED = MainConfigSetup.getInst().get().getString("main-world.seed");
    public final String MAIN_WORLD_DATAPACK_PATH = MainConfigSetup.getInst().get().getString("main-world.datapack-path");

    private String getFormattedString(String text) {
        if(text != null) {
            return ChatUtil.parseColors(text, true);
        } else {
            return StringUtils.EMPTY;
        }
    }

    public boolean getFormattedBooleanFromString(String text) {
        if(text != null) {
            return Boolean.parseBoolean(text);
        }
        return false;
    }

    public static ConfigProvider getInst() {
        if(configProvider == null)
            configProvider = new ConfigProvider();
        return configProvider;
    }

    public static void resetConfigProvider() {
        configProvider = new ConfigProvider();
    }
}
