package io.myztic.core.config;

import io.myztic.core.bukkit.ChatUtils;
import io.myztic.core.config.coreconfig.MainConfigSetup;
import org.apache.commons.lang3.StringUtils;

public class ConfigProvider {

    private static ConfigProvider configProvider = null;

    public final String PLUGIN_PREFIX = getFormattedString(MainConfigSetup.getInst().get().getString("plugin-prefix"));
    public final boolean DEBUG_MODE = MainConfigSetup.getInst().get().getBoolean("debug-mode");
    public final boolean SQL_USE = MainConfigSetup.getInst().get().getBoolean("sql-database-details.use");
    public final String SQL_HOST = getFormattedString(MainConfigSetup.getInst().get().getString("sql-database-details.host"));
    public final String SQL_USER = getFormattedString(MainConfigSetup.getInst().get().getString("sql-database-details.user"));
    public final String SQL_PASSWORD = getFormattedString(MainConfigSetup.getInst().get().getString("sql-database-details.password"));
    public final String SQL_DB = getFormattedString(MainConfigSetup.getInst().get().getString("sql-database-details.database"));
    public final int SQL_PORT = MainConfigSetup.getInst().get().getInt("sql-database-details.port");
    public final String SQL_DRIVER = getFormattedString(MainConfigSetup.getInst().get().getString("sql-database-details.driver"));
    public final String SQL_TLS_VERSION = getFormattedString(MainConfigSetup.getInst().get().getString("sql-database-details.tls-version"));
    public final boolean SQL_USE_SSL = getFormattedBooleanFromString(MainConfigSetup.getInst().get().getString("sql-database-details.use-SSL"));


    private String getFormattedString(String text) {
        if(text != null) {
            return ChatUtils.parseColors(text, true);
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
        if (configProvider == null) {
            configProvider = new ConfigProvider();
        }
        return configProvider;
    }
}
