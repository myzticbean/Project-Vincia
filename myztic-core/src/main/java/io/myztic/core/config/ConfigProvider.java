package io.myztic.core.config;

import io.myztic.core.bukkit.ChatUtils;
import org.apache.commons.lang3.StringUtils;

public class ConfigProvider {

    private static ConfigProvider configProvider = null;

    public final String PLUGIN_PREFIX = getFormattedString(ConfigSetup.get().getString("plugin-prefix"));
    public final boolean DEBUG_MODE = ConfigSetup.get().getBoolean("debug-mode");

    private String getFormattedString(String text) {
        if(text != null) {
            return ChatUtils.parseColors(text, true);
        } else {
            return StringUtils.EMPTY;
        }
    }

    public static ConfigProvider getInst() {
        if (configProvider == null) {
            configProvider = new ConfigProvider();
        }
        return configProvider;
    }
}
