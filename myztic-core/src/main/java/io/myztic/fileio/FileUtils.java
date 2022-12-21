package io.myztic.fileio;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.myztic.MyzticCore;
import io.myztic.bukkit.LoggerUtils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    private FileUtils() { }

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
                    LoggerUtils.logInfo(MyzticCore.getPrefix(), "Saved hidden shops to file");
                } else {
                    LoggerUtils.logInfo(MyzticCore.getPrefix(), "Could not create new file");
                }
            } catch (IOException e) {
                LoggerUtils.logError(MyzticCore.getPrefix(), "Error occurred: " + e.getMessage(), e);
            }
        } else {
            LoggerUtils.logInfo(MyzticCore.getPrefix(), "Path or file does not exist");
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
                LoggerUtils.logInfo(MyzticCore.getPrefix(), "Loaded data from file");
                return dataList;
            } catch (FileNotFoundException e) {
                LoggerUtils.logError(MyzticCore.getPrefix(), "Error occurred: " + e.getMessage(), e);
            }
        }
        return null;
    }

}