package io.myztic.core.fileio;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.myztic.core.MyzticCore;
import io.myztic.core.config.coreconfig.ConfigProvider;
import io.myztic.core.exceptions.UtilityClassException;
import io.myztic.core.logging.LogUtil;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    private FileUtil() {
        throw new UtilityClassException();
    }

    /**
     *
     * @param pluginInstance the plugin instance
     * @param fileName name of file
     * @param listToBeSaved the list to be saved into JSON file
     * @param <T> type of object
     */
    public static <T> void saveDataToJsonFile(Plugin pluginInstance, String fileName, List<T> listToBeSaved) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(pluginInstance.getDataFolder().getAbsolutePath() + File.pathSeparator + fileName);
        boolean isDirCreated = file.getParentFile().mkdir();
        if(isDirCreated && file.exists()) {
            try {
                if(file.createNewFile()) {
                    Writer writer = new FileWriter(file, false);
                    gson.toJson(listToBeSaved, writer);
                    writer.flush();
                    writer.close();
                    LogUtil.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "Saved hidden shops to file");
                } else {
                    LogUtil.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "Could not create new file");
                }
            } catch (IOException e) {
                LogUtil.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Error occurred: " + e.getMessage(), e);
            }
        } else {
            LogUtil.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "Path or file does not exist");
        }
    }

    /**
     *
     * @param pluginInstance the plugin instance
     * @param fileName name of JSON file
     * @return list of data object
     * @param <T>
     */
    @Nullable
    public static <T> List<T> retrieveDataFromJsonFile(Plugin pluginInstance, String fileName) {
        Gson gson = new Gson();
        File file = new File(pluginInstance.getDataFolder().getAbsolutePath() + File.pathSeparator + fileName);
        if(file.exists()) {
            try {
                List<T> dataList = null;
                Reader reader = new FileReader(file);
                /*
                    Tutorial followed: https://www.baeldung.com/gson-deserialization-guide
                 */
                Type typeToken = new TypeToken<T[]>(){}.getType();
                T[] h = gson.fromJson(reader, typeToken);
                if(h != null) {
                    dataList = new ArrayList<>(Arrays.asList(h));
                }
                else {
                    dataList = new ArrayList<>();
                }
                LogUtil.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "Loaded data from file");
                return dataList;
            } catch (FileNotFoundException e) {
                LogUtil.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Error occurred: " + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * This method copies one file to another location
     * Reimplementation of Bukkit's FileUtils.copy() method
     *
     * @param inFile the source filename
     * @param outFile the target filename
     * @return true on success
     */
    public static boolean copy(@NotNull File inFile, @NotNull File outFile) {
        if (!inFile.exists()) {
            return false;
        }
        try (FileChannel in = new FileInputStream(inFile).getChannel();
             FileChannel out = new FileOutputStream(outFile).getChannel()) {
            long pos = 0;
            long size = in.size();
            while (pos < size) {
                pos += in.transferTo(pos, 10 * 1024 * 1024, out);
            }
        } catch (IOException ioe) {
            LogUtil.logError(MyzticCore.getPrefix(), "Error occurred while copying file", ioe);
            return false;
        }
        return true;
    }

}
