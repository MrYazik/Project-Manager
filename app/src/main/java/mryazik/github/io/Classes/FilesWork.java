package mryazik.github.io.Classes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import mryazik.github.io.util.*;

public class FilesWork {
    static Logger logger = Logger.getLogger(FilesWork.class.getName());

    public static void createSystemDir()
    {
        try {
            File folder = new File(getSystemAppPath.get().toUri());

            if (!folder.exists()) { // Если папки нет
                Files.createDirectory(Paths.get(folder.getPath()));
            } else {
                logger.log(Level.INFO, "Файл программы уже существует");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Не удалось создать системную папку", e);
        }
    }

    public static void main(String args[])
    {
        createSystemDir();
    }
}
