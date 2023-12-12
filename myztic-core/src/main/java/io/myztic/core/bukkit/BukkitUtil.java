package io.myztic.core.bukkit;

import io.myztic.core.MyzticCore;
import io.myztic.core.exceptions.UtilityClassException;
import io.papermc.lib.PaperLib;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BukkitUtil {

    private BukkitUtil() { throw new UtilityClassException(); }

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

    public static void broadcastAsync(String s) {
        runOnAsyncThread(MyzticCore.getInst(), () -> {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                p.sendMessage(s);
            }
        });
    }

    public static void broadcastAsync(String s, String permission) {
        runOnAsyncThread(MyzticCore.getInst(), () -> {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.hasPermission(permission)) p.sendMessage(s);
            }
        });
    }

    /**
     * Ensure this method is calling from specific thread
     *
     * @param runOnAsync on async thread or main server thread.
     */
    public static void ensureThread(boolean runOnAsync) throws IllegalStateException {
        boolean isMainThread = Bukkit.isPrimaryThread();
        if (runOnAsync) {
            if (isMainThread) {
                throw new IllegalStateException("#[Illegal Access] This method require runs on async thread.");
            }
        } else {
            if (!isMainThread) {
                throw new IllegalStateException("#[Illegal Access] This method require runs on server main thread.");
            }
        }
    }

    public static void runOnMainThread(Plugin plugin, @NotNull Runnable runnable) {
        if (Bukkit.isPrimaryThread()) {
            runnable.run();
        } else {
            Bukkit.getScheduler().runTask(plugin, runnable);
        }
    }

    public static void runOnAsyncThread(Plugin plugin, @NotNull Runnable runnable) {
        if(Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
        }
        runnable.run();
    }
}
