package io.myztic.core.fileio;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.myztic.core.bukkit.LoggerUtils;
import io.myztic.core.config.ConfigProvider;
import io.myztic.core.exceptions.UtilityClassException;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    private FileUtils() {
        throw new UtilityClassException();
    }

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
                    LoggerUtils.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "Saved hidden shops to file");
                } else {
                    LoggerUtils.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "Could not create new file");
                }
            } catch (IOException e) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Error occurred: " + e.getMessage(), e);
            }
        } else {
            LoggerUtils.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "Path or file does not exist");
        }
    }

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
                LoggerUtils.logInfo(ConfigProvider.getInst().PLUGIN_PREFIX, "Loaded data from file");
                return dataList;
            } catch (FileNotFoundException e) {
                LoggerUtils.logError(ConfigProvider.getInst().PLUGIN_PREFIX, "Error occurred: " + e.getMessage(), e);
            }
        }
        return null;
    }

}
