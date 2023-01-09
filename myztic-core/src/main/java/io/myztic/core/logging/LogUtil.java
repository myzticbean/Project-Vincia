package io.myztic.core.logging;

import io.myztic.core.bukkit.ChatUtil;
import io.myztic.core.exceptions.UtilityClassException;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

/**
 * As of now using Bukkit logging system
 * In the future, plan is to migrate it to a custom logging system using log4j
 */
public class LogUtil {

    private LogUtil() { throw new UtilityClassException(); }

    public static void logDebugInfo(String prefix, String text, boolean debugMode) {
        if(debugMode) {
            text = ChatUtil.parseColors(processPrefix(prefix, text), true);
            Bukkit.getLogger().info(text);
        }
    }

    public static void logDebugWarn(String prefix, String text, boolean debugMode) {
        if(debugMode) {
            text = ChatUtil.parseColors(processPrefix(prefix, text), true);
            Bukkit.getLogger().warning(text);
        }
    }

    public static void logDebugWarn(String prefix, String text, boolean debugMode, Exception e) {
        if(debugMode) {
            text = ChatUtil.parseColors(processPrefix(prefix, text), true);
            Bukkit.getLogger().warning(text);
            e.printStackTrace();
        }
    }

    public static void logDebugError(String prefix, String text, boolean debugMode, Exception e) {
        text = ChatUtil.parseColors(processPrefix(prefix, text), true);
        Bukkit.getLogger().warning(text);
        if(debugMode) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String prefix, String text) {
        text = ChatUtil.parseColors(processPrefix(prefix, text), true);
        Bukkit.getLogger().info(text);
    }

    public static void logInfoSuccess(String prefix, String text) {
        text = ChatUtil.parseColors(prefix + " &a" + text, false);
        Bukkit.getLogger().info(text);
    }

    public static void logError(String prefix, String text) {
        text = ChatUtil.parseColors(processPrefix(prefix, text), true);
        Bukkit.getLogger().severe(text);
    }

    public static void logError(String prefix, String text, Exception e) {
        text = ChatUtil.parseColors(processPrefix(prefix, text), true);
        Bukkit.getLogger().severe(text);
        e.printStackTrace();
    }

    public static void logWarning(String prefix, String text) {
        text = ChatUtil.parseColors(processPrefix(prefix, text), true);
        Bukkit.getLogger().warning(text);
    }

    private static String processPrefix(String prefix, String text) {
        StringBuilder logText = new StringBuilder();
        if(!StringUtils.isEmpty(prefix)) {
            logText.append(prefix).append(StringUtils.SPACE).append(text);
        } else {
            logText.append(text);
        }
        return logText.toString();
    }
}
