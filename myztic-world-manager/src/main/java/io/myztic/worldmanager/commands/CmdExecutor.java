package io.myztic.worldmanager.commands;

import io.myztic.core.exceptions.UtilityClassException;
import io.myztic.core.logging.LogUtil;
import io.myztic.worldmanager.MyzticWorldManager;
import io.myztic.worldmanager.config.ConfigProvider;
import io.myztic.worldmanager.config.MainConfigSetup;
import io.myztic.worldmanager.external.MVCoreHandler;
import io.myztic.worldmanager.permissions.AdminPerm;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdExecutor {

    private CmdExecutor() { throw new UtilityClassException(); }

    public static void handlePluginReload(CommandSender cmdSender) {
        if(!(cmdSender instanceof Player)) {
            // Console sender
            MainConfigSetup.getInst().loadConfig();
            ConfigProvider.resetConfigProvider();
            LogUtil.logInfo(MyzticWorldManager.getPrefix(), "Config reloaded");
        } else {
            // Player sender

        }
    }

    public static void createMainWorld(CommandSender cmdSender) {
        if(!(cmdSender instanceof Player)) {
            // Console sender
            MVCoreHandler.getInst().createMainWorldAsync();
        } else {
            // Player sender
            Player p = (Player) cmdSender;
            if(p.hasPermission(AdminPerm.ALL_ACCESS.value())) {
                MVCoreHandler.getInst().createMainWorldAsync();
            } else {
                LogUtil.logError(MyzticWorldManager.getPrefix(), "No permission");
            }
        }
    }
}
