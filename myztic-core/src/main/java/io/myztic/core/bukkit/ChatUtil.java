package io.myztic.core.bukkit;

import io.myztic.core.exceptions.UtilityClassException;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ChatUtil {

    private ChatUtil() { throw new UtilityClassException(); }

    public static String parseColors(String text, boolean legacy) {
        if(legacy) {
            return LegacyComponentSerializer.legacyAmpersand().deserialize(text).content();
        } else {
            return ColorTranslator.translateColorCodes(text);
        }
    }
}
