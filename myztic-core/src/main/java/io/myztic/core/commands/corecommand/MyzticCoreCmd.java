package io.myztic.core.commands.corecommand;

import io.myztic.core.MyzticCore;
import io.myztic.core.commands.corecommand.subcommands.ReloadSubCmd;
import io.myztic.core.logging.LogUtil;
import me.kodysimpson.simpapi.command.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class MyzticCoreCmd {

    private final String MCORE_MAIN_CMD = "mcore";
    private final String MCORE_CMD_DESC = "";
    private final String MCORE_CMD_USAGE = "/mcore";

    public void createCoreCommand() {
        try {
            CommandManager.createCoreCommand(
                    (JavaPlugin) MyzticCore.getInst(),
                    MCORE_MAIN_CMD,
                    MCORE_CMD_DESC,
                    MCORE_CMD_USAGE,
                    (cmdSender, subCmdList) -> {

                    },
                    Collections.emptyList(),
                    ReloadSubCmd.class);
        } catch(NoSuchFieldException | IllegalAccessException e) {
            LogUtil.logError(MyzticCore.getPrefix(), "Exception occurred:", e);
        }
    }
}
