package io.myztic.core.bukkit;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

public class LoggerUtils {

    private LoggerUtils() { }

    public static void logDebugInfo(String prefix, String text, boolean debugMode) {
        if(debugMode) {
            Bukkit.getLogger().warning(ChatUtils.parseColors(processPrefix(prefix, text), true));
        }
    }

    public static void logDebugInfo(String prefix, String text, boolean debugMode, Exception e) {
        if(debugMode) {
            Bukkit.getLogger().warning(ChatUtils.parseColors(processPrefix(prefix, text), true));
            e.printStackTrace();
        }
    }

    public static void logDebugError(String prefix, String text, boolean debugMode, Exception e) {
        Bukkit.getLogger().warning(ChatUtils.parseColors(processPrefix(prefix, text), true));
        if(debugMode) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String prefix, String text) {
        Bukkit.getLogger().info(ChatUtils.parseColors(processPrefix(prefix, text), true));
    }

    public static void logInfoSuccess(String prefix, String text) {
        Bukkit.getLogger().info(ChatUtils.parseColors(prefix + " &a" + text, false));
    }

    public static void logError(String prefix, String text) {
        Bukkit.getLogger().severe(ChatUtils.parseColors(processPrefix(prefix, text), true));
    }

    public static void logError(String prefix, String text, Exception e) {
        Bukkit.getLogger().severe(ChatUtils.parseColors(processPrefix(prefix, text), true));
        e.printStackTrace();
    }

    public static void logWarning(String prefix, String text) {
        Bukkit.getLogger().warning(ChatUtils.parseColors(processPrefix(prefix, text), true));
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
