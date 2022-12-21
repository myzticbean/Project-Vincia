package io.myztic.bukkit;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ChatUtils {

    private ChatUtils() { }

    public static String parseColors(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text).content();
    }
}
