package mryazik.github.io.workData;

import mryazik.github.io.Classes.FilesWork;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

public class workJsonFile {
    private static final File jsonFile = new File(FilesWork.createSystemDir() + "/all_data.json");
    public static jsonData jsonObject = getJsonInfo();

    public static jsonData getJsonInfo() {
        try {
            if (!jsonFile.exists()) {
                try {
                    Files.createFile(jsonFile.toPath());
                    return getJsonInfo();
                } catch (Exception e) {
                    jsonData.logger.log(Level.SEVERE, "Не удалось создать новый json файл", e);
                }
            } else { // Если файл существует
                try {
                    // Получаем содержимое файла
                    String inFile = Files.readString(jsonFile.toPath());

                    if (inFile.isEmpty()) {
                        jsonData newObj = new jsonData();
                        String example = jsonData.outJsonToString(newObj);

                        Files.writeString(jsonFile.toPath(), example);
                        getJsonInfo();
                    } else {
                        ObjectMapper mapper = new ObjectMapper();
                        jsonData obj = mapper.readValue(jsonFile, jsonData.class);

                        return obj;
                    }
                } catch (Exception e) {
                    jsonData.logger.log(Level.SEVERE, "Не удалось получить содержимое файла", e);
                }
            }
        } catch (Exception e) {
            jsonData.logger.log(Level.SEVERE, "Не удалось получить файл all_data.json", e);
        }
        return null;
    }

    public static jsonData changeJson(jsonData jsonObject) {
        try {
            Files.writeString(jsonFile.toPath(), jsonData.outJsonToString(jsonObject));
        } catch (Exception e) {
            jsonData.logger.log(Level.SEVERE, "Не удалось переписать json", e);
        }

        return null;
    }

    public static void main(String[] args) {
       jsonData.outJsonToString(getJsonInfo());
    }
}
