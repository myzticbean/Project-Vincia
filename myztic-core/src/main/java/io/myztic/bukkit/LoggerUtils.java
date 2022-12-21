package io.myztic.bukkit;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

public class LoggerUtils {

    private LoggerUtils() { }

    public static void logDebugInfo(String prefix, String text, boolean debugMode) {
        if(debugMode) {
            Bukkit.getLogger().warning(ChatUtils.parseColors(processPrefix(prefix, text)));
        }
    }

    public static void logDebugInfo(String prefix, String text, boolean debugMode, Exception e) {
        if(debugMode) {
            Bukkit.getLogger().warning(ChatUtils.parseColors(processPrefix(prefix, text)));
            e.printStackTrace();
        }
    }

    public static void logInfo(String prefix, String text) {
        Bukkit.getLogger().info(ChatUtils.parseColors(processPrefix(prefix, text)));
    }

    public static void logError(String prefix, String text) {
        Bukkit.getLogger().severe(ChatUtils.parseColors(processPrefix(prefix, text)));
    }

    public static void logError(String prefix, String text, Exception e) {
        Bukkit.getLogger().severe(ChatUtils.parseColors(processPrefix(prefix, text)));
        e.printStackTrace();
    }

    public static void logWarning(String prefix, String text) {
        Bukkit.getLogger().warning(ChatUtils.parseColors(processPrefix(prefix, text)));
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
