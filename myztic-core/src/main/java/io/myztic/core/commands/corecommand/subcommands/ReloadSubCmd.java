package io.myztic.core.commands.corecommand.subcommands;

import io.myztic.core.commands.corecommand.CmdExecutor;
import me.kodysimpson.simpapi.command.SubCommand;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ReloadSubCmd extends SubCommand {

    private final CmdExecutor cmdExecutor;
    public ReloadSubCmd() {
        cmdExecutor = new CmdExecutor();
    }
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getDescription() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getSyntax() {
        return "/mcore reload";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        cmdExecutor.handlePluginReload(sender);
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
