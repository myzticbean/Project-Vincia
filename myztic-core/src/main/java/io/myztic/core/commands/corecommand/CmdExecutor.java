package io.myztic.core.commands.corecommand;

import io.myztic.core.MyzticCore;
import io.myztic.core.config.coreconfig.ConfigProvider;
import io.myztic.core.config.coreconfig.MainConfigSetup;
import io.myztic.core.logging.LogUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdExecutor {
    public void handlePluginReload(CommandSender cmdSender) {
        if(!(cmdSender instanceof Player)) {
            // Console sender
            MainConfigSetup.getInst().loadConfig();
            ConfigProvider.resetConfigProvider();
            LogUtil.logInfo(MyzticCore.getPrefix(), "Config reloaded");
        } else {
            // Player sender
        }
    }
}
