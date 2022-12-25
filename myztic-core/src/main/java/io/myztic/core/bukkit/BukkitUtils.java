package io.myztic.core.bukkit;

import io.papermc.lib.PaperLib;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BukkitUtils {

    private BukkitUtils() { }

    /**
     * @deprecated Use sendPlayerActionBarPaper(Player, String) instead
     * @param player
     * @param msg
     */
    @Deprecated(since = "1.0-dev", forRemoval = false)
    public static void sendPlayerActionBarSpigot(Player player, String msg) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, ColorTranslator.translateColorCodesToTextComponent(msg));
    }

    public static void sendPlayerActionBarPaper(Player player, String msg) {
        player.sendActionBar(LegacyComponentSerializer.legacyAmpersand().deserialize(msg));
    }

    public static void teleportPlayer(Player player, Location locToTeleport) {
        PaperLib.teleportAsync(player, locToTeleport, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public static void broadcast(String s) {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.sendMessage(s);
        }
    }

    public static void broadcast(String s, String permission) {
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(p.hasPermission(permission)) p.sendMessage(s);
        }
    }
}
