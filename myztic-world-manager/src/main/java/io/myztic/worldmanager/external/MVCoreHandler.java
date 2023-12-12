package io.myztic.worldmanager.external;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import io.myztic.core.bukkit.BukkitUtil;
import io.myztic.core.fileio.FileUtil;
import io.myztic.core.logging.LogUtil;
import io.myztic.worldmanager.MyzticWorldManager;
import io.myztic.worldmanager.config.ConfigProvider;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MVCoreHandler {

    private final String DATAPACKS_FOLDER_NAME = "datapacks";
    private final String INVALID_WORLD_NAME_ERROR_MSG = "World name empty!!";
    private final MultiverseCore mvCorePlugin;
    private MVWorldManager worldManager = null;
    private static MVCoreHandler mvCoreHandler = null;

    private MVCoreHandler() {
        mvCorePlugin =  (MultiverseCore) Bukkit.getPluginManager().getPlugin(MyzticWorldManager.getMvCorePluginName());
        if(mvCorePlugin != null)
            worldManager = mvCorePlugin.getMVWorldManager();
    }

    public static MVCoreHandler getInst() {
        if(mvCoreHandler == null)
            mvCoreHandler = new MVCoreHandler();
        return mvCoreHandler;
    }

    private MultiverseCore getMvc () {
        return mvCorePlugin;
    }

    private MVWorldManager getWorldManager() {
        return worldManager;
    }

    public void createMainWorldAsync() {
        BukkitUtil.runOnAsyncThread(MyzticWorldManager.getInst(), () -> {
            if(!StringUtils.isEmpty(getMainWorldName())) {
                boolean isWorldCreatedAndLoaded = createMVWorld(
                        getMainWorldName(),
                        Environment.NORMAL,
                        getMainWorldSeed(),
                        WorldType.NORMAL,
                        true,
                        null);
                if(isWorldCreatedAndLoaded) {
                    LogUtil.logInfo(MyzticWorldManager.getPrefix(), "World created and loaded with name '" + getMainWorldName() + "'");
                    addDatapackToWorld(getWorldManager().getMVWorld(getMainWorldName()), getMainWorldDatapackPathInConfig());
                } else {
                    LogUtil.logError(MyzticWorldManager.getPrefix(), "World not created/loaded!");
                }
            } else
                LogUtil.logError(MyzticWorldManager.getPrefix(), INVALID_WORLD_NAME_ERROR_MSG);
        });
    }

    private boolean createMVWorld(@NotNull String worldName, @NotNull Environment env, String seedString, @NotNull WorldType worldType, boolean generateStructures, String generator) {
        return getWorldManager().addWorld(worldName, env, seedString, worldType, generateStructures, generator);
    }

    private void addDatapackToWorld(@NotNull MultiverseWorld world, String datapackName) {
        if(!StringUtils.isEmpty(getMainWorldDatapackPathInConfig())) {
            File worldFolder = new File(world.getCBWorld().getWorldFolder().getPath());
            LogUtil.logDebugInfo(MyzticWorldManager.getPrefix(), "World folder path: " + worldFolder.getPath(), ConfigProvider.getInst().DEBUG_MODE);
            if(worldFolder.exists()) {
                File worldDatapackFolder = new File(worldFolder, DATAPACKS_FOLDER_NAME);
                File datapackFile = new File(
                        MyzticWorldManager.getInst().getDataFolder() + File.separator + DATAPACKS_FOLDER_NAME,
                        datapackName);
                LogUtil.logDebugInfo(MyzticWorldManager.getPrefix(), "Datapack file path: " + datapackFile.getPath(), ConfigProvider.getInst().DEBUG_MODE);
                LogUtil.logDebugInfo(MyzticWorldManager.getPrefix(), "World datapack folder path: " + worldDatapackFolder.getPath(), ConfigProvider.getInst().DEBUG_MODE);
                boolean isDatapackDirCreatedInWorldFolder;
                if(!worldDatapackFolder.exists()) {
                    LogUtil.logInfo(MyzticWorldManager.getPrefix(), "Creating '" + DATAPACKS_FOLDER_NAME + "' folder inside world folder...");
                    isDatapackDirCreatedInWorldFolder = worldDatapackFolder.mkdir();
                } else
                    isDatapackDirCreatedInWorldFolder = true;
                if(isDatapackDirCreatedInWorldFolder) {
                    copyDatapackFile(datapackFile, worldDatapackFolder, datapackName);
                } else
                    LogUtil.logDebugWarn(MyzticWorldManager.getPrefix(), "Failed to create datapack folder inside world folder", getDebugMode());
            } else
                LogUtil.logDebugWarn(MyzticWorldManager.getPrefix(), world.getName() + " world folder doesn't exist", getDebugMode());
        }
    }

    private void copyDatapackFile(@NotNull File datapackFile, @NotNull File worldDatapackFolder, @NotNull String datapackName) {
        if(datapackFile.exists()) {
            boolean isFileCopied = FileUtil.copy(datapackFile, new File(worldDatapackFolder, datapackName));
            if(isFileCopied)
                LogUtil.logInfo(MyzticWorldManager.getPrefix(), "Copied datapack to world folder");
            else
                LogUtil.logError(MyzticWorldManager.getPrefix(), "Failed to copy datapack to world folder");
        } else
            LogUtil.logError(MyzticWorldManager.getPrefix(), "Datapack with name '" + datapackName + "' doesn't exist");
    }

    private String getMainWorldName() {
        return ConfigProvider.getInst().MAIN_WORLD_NAME;
    }

    private String getMainWorldSeed() {
        return ConfigProvider.getInst().MAIN_WORLD_SEED;
    }

    private String getMainWorldDatapackPathInConfig() {
        return ConfigProvider.getInst().MAIN_WORLD_DATAPACK_PATH;
    }

    private boolean getDebugMode() {
        return ConfigProvider.getInst().DEBUG_MODE;
    }
}
