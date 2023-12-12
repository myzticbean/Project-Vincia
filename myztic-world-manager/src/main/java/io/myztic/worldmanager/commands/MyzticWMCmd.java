package io.myztic.worldmanager.commands;

import io.myztic.core.MyzticCore;
import io.myztic.core.logging.LogUtil;
import io.myztic.worldmanager.MyzticWorldManager;
import io.myztic.worldmanager.commands.subcommands.CreateMainWorldSubCmd;
import io.myztic.worldmanager.commands.subcommands.ReloadSubCmd;
import me.kodysimpson.simpapi.command.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class MyzticWMCmd {
    private final String MWM_MAIN_CMD = "mwm";
    private final String MWM_CMD_DESC = "";
    private final String MWM_CMD_USAGE = "/mwm";

    public void createWMCommand() {
        try {
            CommandManager.createCoreCommand(
                    (JavaPlugin) MyzticWorldManager.getInst(),
                    MWM_MAIN_CMD,
                    MWM_CMD_DESC,
                    MWM_CMD_USAGE,
                    (cmdSender, subCmdList) -> {

                    },
                    Collections.emptyList(),
                    ReloadSubCmd.class,
                    CreateMainWorldSubCmd.class
            );
        } catch(NoSuchFieldException | IllegalAccessException e) {
            LogUtil.logError(MyzticCore.getPrefix(), "Exception occurred:", e);
        }
    }
}
